package com.aliyun.kotlin.sdk.service.oss2.retry

import com.aliyun.kotlin.sdk.service.oss2.exceptions.CredentialsFetchException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.InconsistentException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.RequestException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.ResponseException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import kotlin.test.*

internal class ErrorRetryableTest {
    @Test
    fun testClientErrorRetryable() {
        val retryable: ErrorRetryable = ClientErrorRetryable()

        assertTrue(retryable.isErrorRetryable(RequestException("")))
        assertTrue(retryable.isErrorRetryable(ResponseException("")))
        assertTrue(retryable.isErrorRetryable(InconsistentException("", "", null)))
        assertTrue(retryable.isErrorRetryable(CredentialsFetchException(Exception())))

        assertFalse(retryable.isErrorRetryable(Exception()))
        assertFalse(retryable.isErrorRetryable(null))
    }

    @Test
    fun testHTTPStatusCodeRetryable() {
        val retryable: ErrorRetryable = HTTPStatusCodeRetryable()

        assertTrue(retryable.isErrorRetryable(ServiceException(401)))
        assertTrue(retryable.isErrorRetryable(ServiceException(408)))
        assertTrue(retryable.isErrorRetryable(ServiceException(429)))
        assertTrue(retryable.isErrorRetryable(ServiceException(500)))
        assertTrue(retryable.isErrorRetryable(ServiceException(504)))
        assertTrue(retryable.isErrorRetryable(ServiceException(599)))

        assertFalse(retryable.isErrorRetryable(ServiceException(400)))
        assertFalse(retryable.isErrorRetryable(ServiceException(403)))
        assertFalse(retryable.isErrorRetryable(ServiceException()))
    }

    @Test
    fun testServiceErrorCodeRetryable() {
        val retryable: ErrorRetryable = ServiceErrorCodeRetryable()

        assertTrue(
            retryable.isErrorRetryable(
                ServiceException(
                    statusCode = 400,
                    errorFields = mapOf<String, String>("Code" to "BadRequest")
                )
            )
        )

        assertTrue(
            retryable.isErrorRetryable(
                ServiceException(
                    statusCode = 401,
                    errorFields = mapOf<String, String>("Code" to "RequestTimeTooSkewed")
                )
            )
        )

        assertFalse(
            retryable.isErrorRetryable(
                ServiceException(
                    statusCode = 401,
                    errorFields = mapOf<String, String>("Code" to "UnSupportCode")
                )
            )
        )

        assertFalse(
            retryable.isErrorRetryable(
                ServiceException(
                    statusCode = 401,
                    errorFields = mapOf<String, String>()
                )
            )
        )

        assertFalse(retryable.isErrorRetryable(Exception()))

        assertFalse(retryable.isErrorRetryable(null))
    }
}
