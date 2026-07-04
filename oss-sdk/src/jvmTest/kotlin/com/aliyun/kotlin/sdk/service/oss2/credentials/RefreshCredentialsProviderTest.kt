@file:OptIn(ExperimentalTime::class)

package com.aliyun.kotlin.sdk.service.oss2.credentials

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.DateTimeComponents.Companion.Format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.char
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

class RefreshCredentialsProviderTest {

    // 2024-04-18T11:33:40Z
    private val ISO8601_FORMAT: DateTimeFormat<DateTimeComponents> = Format {
        year()
        char('-')
        monthNumber()
        char('-')
        day()
        char('T')
        hour()
        char(':')
        minute()
        char(':')
        second()
        chars("Z")
    }

    @Test
    fun testWithoutExpiration() {
        val provider = RefreshCredentialsProvider(
            StaticCredentialsProvider("ak", "sk")
        )

        runBlocking {
            val cred = provider.getCredentials()
            assertEquals("ak", cred.accessKeyId)
            assertEquals("sk", cred.accessKeySecret)
            assertEquals(null, cred.securityToken)
            assertEquals(null, cred.expiration)

            val cred1 = provider.getCredentials()
            assertEquals("ak", cred1.accessKeyId)
            assertEquals("sk", cred1.accessKeySecret)
            assertEquals(null, cred1.securityToken)
            assertEquals(null, cred1.expiration)
        }
    }

    @Test
    fun testWithExpiration() {
        runBlocking {
            var provider = RefreshCredentialsProvider(
                object : CredentialsProvider {
                    override suspend fun getCredentials(): Credentials {
                        return Credentials(
                            "ak",
                            "sk",
                            "token",
                            Clock.System.now().plus(600.seconds).format(ISO8601_FORMAT)
                        )
                    }
                }
            )

            var cred = provider.getCredentials()
            assertEquals("ak", cred.accessKeyId)
            assertEquals("sk", cred.accessKeySecret)
            assertEquals("token", cred.securityToken)
            assertNotNull(cred.expiration)
            assertTrue(cred.expiration.isNotEmpty())

            provider = RefreshCredentialsProvider(
                object : CredentialsProvider {
                    override suspend fun getCredentials(): Credentials {
                        return Credentials(
                            "ak-1",
                            "sk-1",
                            "token-1",
                            Clock.System.now().plus(1.seconds).format(ISO8601_FORMAT)
                        )
                    }
                }
            )

            cred = provider.getCredentials()
            assertEquals("ak-1", cred.accessKeyId)
            assertEquals("sk-1", cred.accessKeySecret)
            assertEquals("token-1", cred.securityToken)
            assertNotNull(cred.expiration)
            assertTrue(cred.expiration.isNotEmpty())
        }
    }

    @Test
    fun testExpirationGraterThanRefreshInterval() {
        runBlocking {
            var count = 0
            val provider = RefreshCredentialsProvider(
                object : CredentialsProvider {
                    override suspend fun getCredentials(): Credentials {
                        val cred = Credentials(
                            "ak-$count",
                            "sk-$count",
                            "token-$count",
                            Clock.System.now().plus(6000.seconds).format(ISO8601_FORMAT)
                        )
                        count++
                        return cred
                    }
                }
            )

            var testcnt = 0

            for (index in 0..29) {
                val cred = provider.getCredentials()
                assertEquals("ak-0", cred.accessKeyId)
                assertEquals("sk-0", cred.accessKeySecret)
                assertEquals("token-0", cred.securityToken)
                assertNotNull(cred.expiration)
                testcnt++
            }
            assertEquals(30, testcnt)
        }
    }

    @Test
    fun testExpirationLessThanRefreshInterval() {
        // default refreshInterval is 5 * 60
        runBlocking {
            var count = 0
            val provider = RefreshCredentialsProvider(
                object : CredentialsProvider {
                    override suspend fun getCredentials(): Credentials {
                        val cred = Credentials(
                            "ak-$count",
                            "sk-$count",
                            "token-$count",
                            Clock.System.now().plus(100.seconds).format(ISO8601_FORMAT)
                        )
                        count++
                        return cred
                    }
                }
            )

            var cred = provider.getCredentials()
            assertEquals("ak-0", cred.accessKeyId)
            assertEquals("sk-0", cred.accessKeySecret)
            assertEquals("token-0", cred.securityToken)

            var testcnt = 0
            for (index in 0..29) {
                cred = provider.getCredentials()
                assertEquals("ak-$testcnt", cred.accessKeyId)
                assertEquals("sk-$testcnt", cred.accessKeySecret)
                assertEquals("token-$testcnt", cred.securityToken)
                assertNotNull(cred.expiration)
                delay(200.milliseconds)
                testcnt++
            }
            assertEquals(30, testcnt)
        }
    }

    @Test
    fun testUpdateDefaultRefreshInterval() {
        runBlocking {
            var count = 0
            val expiration1 = Clock.System.now().plus(240.seconds).format(ISO8601_FORMAT)
            val expiration2 = Clock.System.now().plus(3600.seconds).format(ISO8601_FORMAT)

            val provider = RefreshCredentialsProvider(
                object : CredentialsProvider {
                    override suspend fun getCredentials(): Credentials {
                        val cred: Credentials
                        if (count == 0) {
                            cred = Credentials(
                                "ak-$count",
                                "sk-$count",
                                "token-$count",
                                expiration1
                            )
                        } else {
                            cred = Credentials(
                                "ak-$count",
                                "sk-$count",
                                "token-$count",
                                expiration2
                            )
                        }
                        count++
                        return cred
                    }
                }
            )

            // 1st
            var cred = provider.getCredentials()
            assertEquals("ak-0", cred.accessKeyId)
            assertEquals("sk-0", cred.accessKeySecret)
            assertEquals("token-0", cred.securityToken)
            assertEquals(expiration1, cred.expiration)

            // 2st in cache
            cred = provider.getCredentials()
            assertEquals("ak-0", cred.accessKeyId)
            assertEquals("sk-0", cred.accessKeySecret)
            assertEquals("token-0", cred.securityToken)
            assertEquals(expiration1, cred.expiration)

            delay(1.seconds)

            var testcnt = 0
            for (index in 0..29) {
                cred = provider.getCredentials()
                assertEquals("ak-1", cred.accessKeyId)
                assertEquals("sk-1", cred.accessKeySecret)
                assertEquals("token-1", cred.securityToken)
                assertEquals(expiration2, cred.expiration)
                delay(200.milliseconds)
                testcnt++
            }
            assertEquals(30, testcnt)
        }
    }

    @Test
    fun testConcurrencyGetCredentials() {
        runBlocking {
            var count = 0
            val expiration1 = Clock.System.now().plus((5 * 60 + 8).seconds).format(ISO8601_FORMAT)
            val expiration2 = Clock.System.now().plus(3600.seconds).format(ISO8601_FORMAT)

            val provider = RefreshCredentialsProvider(
                object : CredentialsProvider {
                    override suspend fun getCredentials(): Credentials {
                        val cred: Credentials
                        if (count == 0) {
                            cred = Credentials(
                                "ak-$count",
                                "sk-$count",
                                "token-$count",
                                expiration1
                            )
                        } else {
                            cred = Credentials(
                                "ak-$count",
                                "sk-$count",
                                "token-$count",
                                expiration2
                            )
                        }
                        count++
                        return cred
                    }
                }
            )

            // run task
            val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

            // run 6s
            var finishTime = Clock.System.now().plus(6.seconds)
            var task1 = scope.async {
                while (finishTime > Clock.System.now()) {
                    val cred = provider.getCredentials()
                    assertTrue {
                        cred.accessKeyId == "ak-0" || cred.accessKeyId == "ak-1"
                    }
                }
            }
            var task2 = scope.async {
                while (finishTime > Clock.System.now()) {
                    val cred = provider.getCredentials()
                    assertTrue {
                        cred.accessKeyId == "ak-0" || cred.accessKeyId == "ak-1"
                    }
                }
            }

            task1.await()
            task2.await()

            // wait 5s
            delay(5.seconds)
            provider.getCredentials()
            delay(100.milliseconds)

            // run 10s
            finishTime = Clock.System.now().plus(10.seconds)
            task1 = scope.async {
                while (finishTime > Clock.System.now()) {
                    val cred = provider.getCredentials()
                    assertTrue {
                        cred.accessKeyId == "ak-1" || cred.accessKeyId == "ak-2"
                    }
                }
            }
            task2 = scope.async {
                while (finishTime > Clock.System.now()) {
                    val cred = provider.getCredentials()
                    assertTrue {
                        cred.accessKeyId == "ak-1" || cred.accessKeyId == "ak-2"
                    }
                }
            }

            task1.await()
            task2.await()
        }
    }
}
