package com.aliyun.kotlin.sdk.service.oss2.exceptions

import kotlin.test.*

class ServiceExceptionTest {
    @Test
    fun testEmptyServiceException() {
        val error = ServiceException()

        assertNotNull(error)

        assertEquals(0, error.statusCode)
        assertTrue(error.errorFields.isEmpty())
        assertTrue(error.headers.isEmpty())
        assertEquals("BadErrorResponse", error.errorCode)
        assertEquals("", error.errorMessage)
        assertEquals("", error.requestId)
        assertEquals("", error.ec)
        assertEquals("", error.timestamp)
        assertEquals("", error.requestTarget)
        assertNull(error.snapshot)

        val errorMsg = "$error"

        assertContains(errorMsg, "Http Status Code: 0")
        assertContains(errorMsg, "Error Code: BadErrorResponse")
        assertContains(errorMsg, "Request Id: .")
        assertContains(errorMsg, "Message: .")
        assertContains(errorMsg, "EC: .")
        assertContains(errorMsg, "Timestamp: .")
        assertContains(errorMsg, "Request Endpoint: .")
    }

    @Test
    fun testServiceExceptionWithAll() {
        val errorFields = mutableMapOf<String, String>()
        errorFields.put("Code", "MalformedXML")
        errorFields.put(
            "Message",
            "The XML you provided was not well-formed or did not validate against our published schema."
        )
        errorFields.put("RequestId", "57ABD896CCB80C366955****")
        errorFields.put("EC", "0031-00000001")

        val headers = mutableMapOf<String, String>()
        headers.put("x-oss-request-id", "636B68BA80DA8539399F****")
        headers.put("x-oss-ec", "0003-00000001")

        val statusCode = 403
        val requestTarget = "http://oss-cn-hangzhou.aliyuncs.com/1.txt"
        val timestamp = "Thu, 15 May 2025 11:18:32 GMT"
        val payload = "hello".toByteArray()

        val error = ServiceException(
            statusCode = statusCode,
            errorFields = errorFields,
            headers = headers,
            requestTarget = requestTarget,
            timestamp = timestamp,
            snapshot = payload
        )

        assertNotNull(error)

        assertEquals(403, error.statusCode)
        assertTrue(error.errorFields.isNotEmpty())
        assertTrue(error.headers.isNotEmpty())
        assertEquals("MalformedXML", error.errorCode)
        assertEquals(
            "The XML you provided was not well-formed or did not validate against our published schema.",
            error.errorMessage
        )
        assertEquals("57ABD896CCB80C366955****", error.requestId)
        assertEquals("0031-00000001", error.ec)
        assertEquals(timestamp, error.timestamp)
        assertEquals(requestTarget, error.requestTarget)
        assertEquals(payload, error.snapshot)

        val errorMsg = "$error"

        assertContains(errorMsg, "Http Status Code: 403")
        assertContains(errorMsg, "Error Code: MalformedXML")
        assertContains(errorMsg, "Request Id: 57ABD896CCB80C366955****")
        assertContains(errorMsg, "Message: The XML you provided was not well-formed or ")
        assertContains(errorMsg, "EC: 0031-00000001")
        assertContains(errorMsg, "Timestamp: Thu, 15 May 2025 11:18:32 GMT.")
        assertContains(errorMsg, "Request Endpoint: http://oss-cn-hangzhou.aliyuncs.com/1.txt.")
    }

    @Test
    fun testServiceExceptionWithoutErrorFields() {
        val headers = mutableMapOf<String, String>()
        headers.put("x-oss-request-id", "636B68BA80DA8539399F****")
        headers.put("x-oss-ec", "0003-00000001")

        val statusCode = 403
        val requestTarget = "http://oss-cn-hangzhou.aliyuncs.com/1.txt"
        val timestamp = "Thu, 15 May 2025 11:18:32 GMT"
        val payload = "hello".toByteArray()

        val error = ServiceException(
            statusCode = statusCode,
            headers = headers,
            requestTarget = requestTarget,
            timestamp = timestamp,
            snapshot = payload
        )

        assertNotNull(error)

        assertEquals(403, error.statusCode)
        assertTrue(error.errorFields.isEmpty())
        assertTrue(error.headers.isNotEmpty())
        assertEquals("BadErrorResponse", error.errorCode)
        assertEquals(
            "",
            error.errorMessage
        )
        assertEquals("636B68BA80DA8539399F****", error.requestId)
        assertEquals("0003-00000001", error.ec)
        assertEquals(timestamp, error.timestamp)
        assertEquals(requestTarget, error.requestTarget)
        assertEquals(payload, error.snapshot)

        val errorMsg = "$error"

        assertContains(errorMsg, "Http Status Code: 403")
        assertContains(errorMsg, "Error Code: BadErrorResponse")
        assertContains(errorMsg, "Request Id: 636B68BA80DA8539399F****")
        assertContains(errorMsg, "Message: .")
        assertContains(errorMsg, "EC: 0003-00000001")
        assertContains(errorMsg, "Timestamp: Thu, 15 May 2025 11:18:32 GMT.")
        assertContains(errorMsg, "Request Endpoint: http://oss-cn-hangzhou.aliyuncs.com/1.txt.")
    }

    @Test
    fun testAsCause() {
        assertNull(ServiceException.asCause(null))
        assertNull(ServiceException.asCause(RuntimeException()))

        val se: ServiceException = ServiceException()
        val root = RuntimeException("test", se)
        assertEquals(se, ServiceException.asCause(root))
    }
}
