package com.aliyun.kotlin.sdk.service.oss2.credentials

import kotlinx.coroutines.runBlocking
import kotlin.test.*

class StaticCredentialsProviderTest {
    @Test
    fun testConstructor() {
        runBlocking {
            var provider = StaticCredentialsProvider("ak", "sk")
            assertNotNull(provider)

            var cred = provider.getCredentials()
            assertEquals("ak", cred.accessKeyId)
            assertEquals("sk", cred.accessKeySecret)
            assertNull(cred.securityToken)
            assertTrue(cred.hasKeys())

            provider = StaticCredentialsProvider("ak", "sk", "token")
            cred = provider.getCredentials()
            assertNotNull(cred)
            assertEquals("ak", cred.accessKeyId)
            assertEquals("sk", cred.accessKeySecret)
            assertEquals("token", cred.securityToken)
            assertTrue(cred.hasKeys())
        }
    }
}
