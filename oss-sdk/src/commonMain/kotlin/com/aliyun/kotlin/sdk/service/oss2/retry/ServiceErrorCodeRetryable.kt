package com.aliyun.kotlin.sdk.service.oss2.retry

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException

/**
 * Determines whether a service error is retryable based on its error code.
 */
internal class ServiceErrorCodeRetryable : ErrorRetryable {
    private val errorCodes = listOf("RequestTimeTooSkewed", "BadRequest")

    private val errorMessages = listOf("Invalid signing date in Authorization header.")

    /**
     * Checks whether the given error has a retryable error code.
     *
     * @param error The error to be checked
     * @return true if the error code is retryable, false otherwise
     */
    override fun isErrorRetryable(error: Throwable?): Boolean {
        if (error is ServiceException) {
            return errorCodes.contains(error.errorCode) || errorMessages.contains(error.errorMessage)
        }
        return false
    }
}
