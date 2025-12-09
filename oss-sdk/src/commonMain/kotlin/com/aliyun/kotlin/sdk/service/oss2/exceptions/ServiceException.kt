package com.aliyun.kotlin.sdk.service.oss2.exceptions

/**
 * Service Exception public class that encapsulates detailed error information returned by the OSS service
 */
public class ServiceException(
    public val statusCode: Int = 0,
    public val headers: Map<String, String> = emptyMap(),
    public val errorFields: Map<String, String> = emptyMap(),
    public val requestTarget: String = "",
    public val timestamp: String = "",
    public val snapshot: ByteArray? = null,
) : RuntimeException(buildMessage(statusCode, headers, errorFields, timestamp, requestTarget)) {

    public val errorCode: String
        get() = toErrorCode(this.errorFields)

    public val errorMessage: String
        get() = toErrorMessage(this.errorFields)

    public val ec: String
        get() = toEc(this.errorFields, this.headers)

    public val requestId: String
        get() = toRequestId(this.errorFields, this.headers)

    public companion object {

        /**
         * Obtain the ServiceException cause if the throwable's causal chain have an immediate or wrapped exception
         * to the ServiceException
         *
         * @param chain The root of a Throwable causal chain.
         * @return the ServiceException instance, if chain is an instance of ServiceException;
         * null, if none found
         */
        public fun asCause(chain: Throwable?): ServiceException? {
            if (chain == null) {
                return null
            }
            var current: Throwable? = chain
            while (current != null) {
                if (current is ServiceException) {
                    return current
                }
                current = current.cause
            }
            return null
        }

        /**
         * Error message format template used to generate structured server-side error descriptions
         */
        private fun buildMessage(
            statusCode: Int?,
            headers: Map<String, String>,
            errorFields: Map<String, String>,
            timestamp: String,
            requestTarget: String,
        ): String {
            return "Error returned by Service.\n" +
                "Http Status Code: $statusCode.\n" +
                "Error Code: ${toErrorCode(errorFields)}.\n" +
                "Request Id: ${toRequestId(errorFields, headers)}.\n" +
                "Message: ${toErrorMessage(errorFields)}.\n" +
                "EC: ${toEc(errorFields, headers)}.\n" +
                "Timestamp: $timestamp.\n" +
                "Request Endpoint: $requestTarget."
        }

        private fun toErrorCode(errorFields: Map<String, String>): String {
            return errorFields.getOrDefault("Code", "BadErrorResponse")
        }

        private fun toErrorMessage(errorFields: Map<String, String>): String {
            return errorFields.getOrDefault("Message", "")
        }

        private fun toRequestId(
            errorFields: Map<String, String>?,
            headers: Map<String, String>?
        ): String {
            var value: String? = null
            if (errorFields != null) {
                value = errorFields["RequestId"]
            }

            if (value == null && headers != null) {
                value = headers["x-oss-request-id"]
            }

            return value ?: ""
        }

        private fun toEc(
            errorFields: Map<String, String>?,
            headers: Map<String, String>?
        ): String {
            var value: String? = null
            if (errorFields != null) {
                value = errorFields["EC"]
            }

            if (value == null && headers != null) {
                value = headers["x-oss-ec"]
            }
            return value ?: ""
        }
    }
}
