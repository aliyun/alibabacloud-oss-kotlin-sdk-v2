package com.aliyun.kotlin.sdk.service.oss2.retry

import com.aliyun.kotlin.sdk.service.oss2.exceptions.CredentialsFetchException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.InconsistentException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.RequestException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.ResponseException

/**
 * Helper class to determine if a client-side error is retryable.
 */
internal class ClientErrorRetryable : ErrorRetryable {

    /**
     * Checks whether the given error is of a retryable type.
     *
     * @param error The error to be checked
     * @return true if the error is retryable, false otherwise
     */
    override fun isErrorRetryable(error: Throwable?): Boolean {
        if (error is RequestException) {
            return true
        }

        if (error is ResponseException) {
            return true
        }

        if (error is InconsistentException) {
            return true
        }

        if (error is CredentialsFetchException) {
            return true
        }
        return false
    }
}
