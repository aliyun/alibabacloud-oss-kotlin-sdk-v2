package com.aliyun.kotlin.sdk.service.oss2.extension.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GetUserDefinedLogFieldsConfigTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = GetUserDefinedLogFieldsConfigRequest {}
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
        val request = GetUserDefinedLogFieldsConfigRequest {
            bucket = "bucket"
        }

        assertEquals("bucket", request.bucket)

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
    fun buildRequestFromBuilder() {
        val builder = GetUserDefinedLogFieldsConfigRequest.Builder()
        builder.bucket = "bucket"

        val request = GetUserDefinedLogFieldsConfigRequest(builder)
        assertEquals("bucket", request.bucket)

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
    fun buildResultWithEmptyValues() {
        val result = GetUserDefinedLogFieldsConfigResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.userDefinedLogFieldsConfiguration)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val configuration = UserDefinedLogFieldsConfiguration {
            headerSet = HeaderSet {
                headers = listOf(
                    "header1",
                    "header2",
                    "header3"
                )
            }
            paramSet = ParamSet {
                parameters = listOf(
                    "param1",
                    "param2"
                )
            }
        }
        val result = GetUserDefinedLogFieldsConfigResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = configuration
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(configuration, result.userDefinedLogFieldsConfiguration)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val configuration = UserDefinedLogFieldsConfiguration {
            headerSet = HeaderSet {
                headers = listOf(
                    "header1",
                    "header2",
                    "header3"
                )
            }
            paramSet = ParamSet {
                parameters = listOf(
                    "param1",
                    "param2"
                )
            }
        }
        val builder = GetUserDefinedLogFieldsConfigResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = configuration

        val result = GetUserDefinedLogFieldsConfigResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(configuration, result.userDefinedLogFieldsConfiguration)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
