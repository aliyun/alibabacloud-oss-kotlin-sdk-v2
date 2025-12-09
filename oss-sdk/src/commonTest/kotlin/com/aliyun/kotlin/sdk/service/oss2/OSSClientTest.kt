@file:OptIn(ExperimentalTime::class)

package com.aliyun.kotlin.sdk.service.oss2

import com.aliyun.kotlin.sdk.service.oss2.credentials.Credentials
import com.aliyun.kotlin.sdk.service.oss2.credentials.StaticCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.exceptions.OperationException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketAclRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

class OSSClientTest {

    companion object {
        const val BUCKET_NAME_PREFIX: String = "kotlin-sdk-test-bucket-"
        const val OBJECT_NAME_PREFIX: String = "kotlin-sdk-test-object-"

        fun randomBucketName(): String {
            val ticks = Clock.System.now().epochSeconds
            val value = Random.nextInt(500).toLong()
            return "$BUCKET_NAME_PREFIX$ticks-$value"
        }
    }

    @Test
    fun testBucketAclOperationsFail() {
        val config = ClientConfiguration.loadDefault().apply {
            region = "cn-shenzhen"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
        }

        DefaultOSSClient(config).use { client ->

            runBlocking {
                val bucketName = randomBucketName()

                try {
                    client.getBucketAcl(
                        GetBucketAclRequest {
                            bucket = bucketName
                        }
                    )
                    // assertEquals("private", result.accessControlPolicy!!.accessControlList!!.grant)
                } catch (e: OperationException) {
                    val se = ServiceException.asCause(e)
                    assertNotNull(se)
                    assertEquals(404, se.statusCode)
                    assertEquals("NoSuchBucket", se.errorCode)
                    assertEquals(24, se.headers["x-oss-request-id"]?.length)
                    assertEquals(24, se.headers["X-Oss-Request-Id"]?.length)
                    assertContains(
                        se.toString(),
                        "Request Endpoint: GET https://$bucketName.oss-${config.region}.aliyuncs.com/?acl"
                    )
                }
            }
        }
    }

    @Test
    fun testScope() {
        val provider = StaticCredentialsProvider("ak", "sk")
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

        val d: Deferred<Credentials> = scope.async {
            println("getCredentials begin")
            delay(1.seconds)
            provider.getCredentials()
        }

        runBlocking {
            println("await 1 begin")
            val cred1 = d.await()
            println("await 1 end")
            println("cred1 ak:${cred1.accessKeyId}, sk:${cred1.accessKeySecret}")

            println("await 2 end")
            val cred2 = d.await()
            println("await 2 end")
            println("cred2 ak:${cred2.accessKeyId}, sk:${cred2.accessKeySecret}")
        }
    }
}
