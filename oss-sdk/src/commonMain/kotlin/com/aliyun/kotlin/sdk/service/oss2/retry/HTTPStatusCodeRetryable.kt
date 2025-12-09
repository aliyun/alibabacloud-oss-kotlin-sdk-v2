package com.aliyun.kotlin.sdk.service.oss2.retry

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException

/**
 * Determines whether a service error is retryable based on its HTTP status code.
 */
internal class HTTPStatusCodeRetryable : ErrorRetryable {

    private val statusCodes = listOf(401, 408, 429)

    /**
     * Checks whether the given error is retryable based on its HTTP status code.
     *
     * @param error The error to be checked
     * @return true if the error should be retried, false otherwise
     */
    override fun isErrorRetryable(error: Throwable?): Boolean {
        if (error is ServiceException) {
            if (error.statusCode >= 500) {
                return true
            }
            for (code in statusCodes) {
                if (code == error.statusCode) {
                    return true
                }
            }
        }
        return false
    }
}
