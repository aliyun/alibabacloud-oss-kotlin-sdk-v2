package com.aliyun.kotlin.sdk.service.oss2.extension.models

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class BucketCorsTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = PutBucketCorsRequest {}
        assertNull(request.bucket)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.isEmpty()
        }
    }

    @Test
    fun buildRequestWithFullValuesFromDsl() {
    }

    @Test
    fun buildRequestFromBuilder() {
    }

    @Test
    fun buildResultWithEmptyValues() {
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
    }

    @Test
    fun buildResultFromBuilder() {
    }
}
