package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the presign operation.
 */
public class PresignResult(builder: Builder) {
    /**
     * Gets the pre-signed URL.
     */
    public val url: String = builder.url ?: ""

    /**
     * Gets the HTTP method, which corresponds to the operation.
     * For example, the HTTP method of the GetObject operation is GET.
     */
    public val method: String = builder.method ?: ""

    /**
     * Gets the time when the pre-signed URL expires.
     * The epoch number of seconds from the epoch instant 1970-01-01T00:00:00Z
     * If it is null, it means it does not expire.
     */
    public val expirationInEpoch: Long? = builder.expirationInEpoch

    /**
     * Gets the request headers specified in the request.
     */
    public val signedHeaders: Map<String, String> = builder.signedHeaders ?: mapOf()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PresignResult =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * Specifies the pre-signed URL.
         */
        public var url: String? = null

        /**
         * Specifies the HTTP method, which corresponds to the operation.
         */
        public var method: String? = null

        /**
         * Specifies the time when the pre-signed URL expires.
         * The epoch number of seconds from the epoch instant 1970-01-01T00:00:00Z
         */
        public var expirationInEpoch: Long? = null

        /**
         * Specifies the request headers specified in the request.
         */
        public var signedHeaders: Map<String, String>? = null

        public fun build(): PresignResult {
            return PresignResult(this)
        }
    }
}
