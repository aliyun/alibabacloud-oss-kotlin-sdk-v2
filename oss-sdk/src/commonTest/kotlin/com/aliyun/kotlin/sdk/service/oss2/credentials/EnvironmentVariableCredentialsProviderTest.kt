package com.aliyun.kotlin.sdk.service.oss2.credentials

import kotlinx.coroutines.runBlocking
import kotlin.test.*

class EnvironmentVariableCredentialsProviderTest {

    @Test
    fun testConstructor() {
        runBlocking {
            val provider = EnvironmentVariableCredentialsProvider()
            assertNotNull(provider)

            val cred = provider.getCredentials()
            assertNotNull(cred.accessKeyId)
            assertNotNull(cred.accessKeySecret)
        }
    }
}
