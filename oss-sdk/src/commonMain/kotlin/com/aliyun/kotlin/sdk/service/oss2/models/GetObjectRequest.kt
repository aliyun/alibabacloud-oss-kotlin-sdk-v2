package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.progress.ProgressListener

/**
 * The request for the GetObject operation.
 */
public class GetObjectRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The full path of the object.
     */
    public val key: String? = builder.key

    /**
     * The progress listener for monitoring data transfer during OSS operations.
     */
    public val progressListener: ProgressListener? = builder.progressListener

    /**
     * The range of data of the object to be returned.
     * - If the value of Range is valid, OSS returns the response that includes the total size of the object and the range of data returned. For example, Content-Range: bytes 0~9/44 indicates that the total size of the object is 44 bytes, and the range of data returned is the first 10 bytes.
     * - However, if the value of Range is invalid, the entire object is returned, and the response returned by OSS excludes Content-Range. Default value: null
     */
    public val range: String?
        get() = headers["Range"]

    /**
     * If the time specified in this header is earlier than the object modified time or is invalid, OSS returns the object and 200 OK. If the time specified in this header is later than or the same as the object modified time, OSS returns 304 Not Modified. The time must be in GMT. Example: `Fri, 13 Nov 2015 14:47:53 GMT`.Default value: null
     */
    public val ifModifiedSince: String?
        get() = headers["If-Modified-Since"]

    /**
     * If the time specified in this header is the same as or later than the object modified time, OSS returns the object and 200 OK. If the time specified in this header is earlier than the object modified time, OSS returns 412 Precondition Failed.
     * The time must be in GMT. Example: `Fri, 13 Nov 2015 14:47:53 GMT`.You can specify both the **If-Modified-Since** and **If-Unmodified-Since** headers in a request. Default value: null
     */
    public val ifUnmodifiedSince: String?
        get() = headers["If-Unmodified-Since"]

    /**
     * If the ETag specified in the request matches the ETag value of the object, OSS transmits the object and returns 200 OK. If the ETag specified in the request does not match the ETag value of the object, OSS returns 412 Precondition Failed. The ETag value of an object is used to check whether the content of the object has changed. You can check data integrity by using the ETag value. Default value: null
     */
    public val ifMatch: String?
        get() = headers["If-Match"]

    /**
     * If the ETag specified in the request does not match the ETag value of the object, OSS transmits the object and returns 200 OK. If the ETag specified in the request matches the ETag value of the object, OSS returns 304 Not Modified. You can specify both the **If-Match** and **If-None-Match** headers in a request. Default value: null
     */
    public val ifNoneMatch: String?
        get() = headers["If-None-Match"]

    /**
     * The encoding type at the client side. If you want an object to be returned in the GZIP format, you must include the Accept-Encoding:gzip header in your request. OSS determines whether to return the object compressed in the GZip format based on the Content-Type header and whether the size of the object is larger than or equal to 1 KB.
     * If an object is compressed in the GZip format, the response OSS returns does not include the ETag value of the object.
     * - OSS supports the following Content-Type values to compress the object in the GZip format: text/cache-manifest, text/xml, text/plain, text/css, application/javascript, application/x-javascript, application/rss+xml, application/json, and text/json. Default value: null
     */
    public val acceptEncoding: String?
        get() = headers["Accept-Encoding"]

    /**
     * The content-type header in the response that OSS returns.
     */
    public val responseContentType: String?
        get() = parameters["response-content-type"]

    /**
     * The content-language header in the response that OSS returns.
     */
    public val responseContentLanguage: String?
        get() = parameters["response-content-language"]

    /**
     * The expires header in the response that OSS returns.
     */
    public val responseExpires: String?
        get() = parameters["response-expires"]

    /**
     * The cache-control header in the response that OSS returns.
     */
    public val responseCacheControl: String?
        get() = parameters["response-cache-control"]

    /**
     * The content-disposition header in the response that OSS returns.
     */
    public val responseContentDisposition: String?
        get() = parameters["response-content-disposition"]

    /**
     * The content-encoding header in the response that OSS returns.
     */
    public val responseContentEncoding: String?
        get() = parameters["response-content-encoding"]

    /**
     * The version ID of the object that you want to query.
     */
    public val versionId: String?
        get() = parameters["versionId"]

    public inline fun copy(block: Builder.() -> Unit = {}): GetObjectRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetObjectRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The full path of the object.
         */
        public var key: String? = null

        /**
         * The progress listener for monitoring data transfer during OSS operations.
         */
        public var progressListener: ProgressListener? = null

        /**
         * The range of data of the object to be returned.
         * - If the value of Range is valid, OSS returns the response that includes the total size of the object and the range of data returned. For example, Content-Range: bytes 0~9/44 indicates that the total size of the object is 44 bytes, and the range of data returned is the first 10 bytes.
         * - However, if the value of Range is invalid, the entire object is returned, and the response returned by OSS excludes Content-Range. Default value: null
         */
        public var range: String?
            set(value) {
                value?.let { this.headers["Range"] = it }
            }
            get() = headers["Range"]

        /**
         * If the time specified in this header is earlier than the object modified time or is invalid, OSS returns the object and 200 OK. If the time specified in this header is later than or the same as the object modified time, OSS returns 304 Not Modified. The time must be in GMT. Example: `Fri, 13 Nov 2015 14:47:53 GMT`.Default value: null
         */
        public var ifModifiedSince: String?
            set(value) {
                value?.let { this.headers["If-Modified-Since"] = it }
            }
            get() = headers["If-Modified-Since"]

        /**
         * If the time specified in this header is the same as or later than the object modified time, OSS returns the object and 200 OK. If the time specified in this header is earlier than the object modified time, OSS returns 412 Precondition Failed.
         * The time must be in GMT. Example: `Fri, 13 Nov 2015 14:47:53 GMT`.You can specify both the **If-Modified-Since** and **If-Unmodified-Since** headers in a request. Default value: null
         */
        public var ifUnmodifiedSince: String?
            set(value) {
                value?.let { this.headers["If-Unmodified-Since"] = it }
            }
            get() = headers["If-Unmodified-Since"]

        /**
         * If the ETag specified in the request matches the ETag value of the object, OSS transmits the object and returns 200 OK. If the ETag specified in the request does not match the ETag value of the object, OSS returns 412 Precondition Failed. The ETag value of an object is used to check whether the content of the object has changed. You can check data integrity by using the ETag value. Default value: null
         */
        public var ifMatch: String?
            set(value) {
                value?.let { this.headers["If-Match"] = it }
            }
            get() = headers["If-Match"]

        /**
         * If the ETag specified in the request does not match the ETag value of the object, OSS transmits the object and returns 200 OK. If the ETag specified in the request matches the ETag value of the object, OSS returns 304 Not Modified. You can specify both the **If-Match** and **If-None-Match** headers in a request. Default value: null
         */
        public var ifNoneMatch: String?
            set(value) {
                value?.let { this.headers["If-None-Match"] = it }
            }
            get() = headers["If-None-Match"]

        /**
         * The encoding type at the client side. If you want an object to be returned in the GZIP format, you must include the Accept-Encoding:gzip header in your request. OSS determines whether to return the object compressed in the GZip format based on the Content-Type header and whether the size of the object is larger than or equal to 1 KB.
         * If an object is compressed in the GZip format, the response OSS returns does not include the ETag value of the object.
         * - OSS supports the following Content-Type values to compress the object in the GZip format: text/cache-manifest, text/xml, text/plain, text/css, application/javascript, application/x-javascript, application/rss+xml, application/json, and text/json. Default value: null
         */
        public var acceptEncoding: String?
            set(value) {
                value?.let { this.headers["Accept-Encoding"] = it }
            }
            get() = headers["Accept-Encoding"]

        /**
         * The content-type header in the response that OSS returns.
         */
        public var responseContentType: String?
            set(value) {
                value?.let { this.parameters["response-content-type"] = it }
            }
            get() = parameters["response-content-type"]

        /**
         * The content-language header in the response that OSS returns.
         */
        public var responseContentLanguage: String?
            set(value) {
                value?.let { this.parameters["response-content-language"] = it }
            }
            get() = parameters["response-content-language"]

        /**
         * The expires header in the response that OSS returns.
         */
        public var responseExpires: String?
            set(value) {
                value?.let { this.parameters["response-expires"] = it }
            }
            get() = parameters["response-expires"]

        /**
         * The cache-control header in the response that OSS returns.
         */
        public var responseCacheControl: String?
            set(value) {
                value?.let { this.parameters["response-cache-control"] = it }
            }
            get() = parameters["response-cache-control"]

        /**
         * The content-disposition header in the response that OSS returns.
         */
        public var responseContentDisposition: String?
            set(value) {
                value?.let { this.parameters["response-content-disposition"] = it }
            }
            get() = parameters["response-content-disposition"]

        /**
         * The content-encoding header in the response that OSS returns.
         */
        public var responseContentEncoding: String?
            set(value) {
                value?.let { this.parameters["response-content-encoding"] = it }
            }
            get() = parameters["response-content-encoding"]

        /**
         * The version ID of the object that you want to query.
         */
        public var versionId: String?
            set(value) {
                value?.let { this.parameters["versionId"] = it }
            }
            get() = parameters["versionId"]

        public fun build(): GetObjectRequest {
            return GetObjectRequest(this)
        }

        public constructor(from: GetObjectRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.key = from.key
        }
    }
}
