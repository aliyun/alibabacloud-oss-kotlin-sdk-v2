package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the HeadObject operation.
 */
public class HeadObjectRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The full path of the object.
     */
    public val key: String? = builder.key

    /**
     * If the time that is specified in the request is earlier than the time when the object is modified, OSS returns 200 OK and the metadata of the object. Otherwise, OSS returns 304 not modified. Default value: null.
     */
    public val ifModifiedSince: String?
        get() = headers["If-Modified-Since"]

    /**
     * If the time that is specified in the request is later than or the same as the time when the object is modified, OSS returns 200 OK and the metadata of the object. Otherwise, OSS returns 412 precondition failed. Default value: null.
     */
    public val ifUnmodifiedSince: String?
        get() = headers["If-Unmodified-Since"]

    /**
     * If the ETag value that is specified in the request matches the ETag value of the object, OSS returns 200 OK and the metadata of the object. Otherwise, OSS returns 412 precondition failed. Default value: null.
     */
    public val ifMatch: String?
        get() = headers["If-Match"]

    /**
     * If the ETag value that is specified in the request does not match the ETag value of the object, OSS returns 200 OK and the metadata of the object. Otherwise, OSS returns 304 Not Modified. Default value: null.
     */
    public val ifNoneMatch: String?
        get() = headers["If-None-Match"]

    /**
     * The version ID of the object for which you want to query metadata.
     */
    public val versionId: String?
        get() = parameters["versionId"]

    public inline fun copy(block: Builder.() -> Unit = {}): HeadObjectRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): HeadObjectRequest =
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
         * If the time that is specified in the request is earlier than the time when the object is modified, OSS returns 200 OK and the metadata of the object. Otherwise, OSS returns 304 not modified. Default value: null.
         */
        public var ifModifiedSince: String?
            set(value) {
                value?.let { this.headers["If-Modified-Since"] = it }
            }
            get() = headers["If-Modified-Since"]

        /**
         * If the time that is specified in the request is later than or the same as the time when the object is modified, OSS returns 200 OK and the metadata of the object. Otherwise, OSS returns 412 precondition failed. Default value: null.
         */
        public var ifUnmodifiedSince: String?
            set(value) {
                value?.let { this.headers["If-Unmodified-Since"] = it }
            }
            get() = headers["If-Unmodified-Since"]

        /**
         * If the ETag value that is specified in the request matches the ETag value of the object, OSS returns 200 OK and the metadata of the object. Otherwise, OSS returns 412 precondition failed. Default value: null.
         */
        public var ifMatch: String?
            set(value) {
                value?.let { this.headers["If-Match"] = it }
            }
            get() = headers["If-Match"]

        /**
         * If the ETag value that is specified in the request does not match the ETag value of the object, OSS returns 200 OK and the metadata of the object. Otherwise, OSS returns 304 Not Modified. Default value: null.
         */
        public var ifNoneMatch: String?
            set(value) {
                value?.let { this.headers["If-None-Match"] = it }
            }
            get() = headers["If-None-Match"]

        /**
         * The version ID of the object for which you want to query metadata.
         */
        public var versionId: String?
            set(value) {
                value?.let { this.parameters["versionId"] = it }
            }
            get() = parameters["versionId"]

        public fun build(): HeadObjectRequest {
            return HeadObjectRequest(this)
        }

        public constructor(from: HeadObjectRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.key = from.key
        }
    }
}
