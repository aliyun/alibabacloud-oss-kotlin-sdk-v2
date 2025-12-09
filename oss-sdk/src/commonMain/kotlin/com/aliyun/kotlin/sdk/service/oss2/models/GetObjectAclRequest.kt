package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the GetObjectAcl operation.
 */
public class GetObjectAclRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The name of the object.
     */
    public val key: String? = builder.key

    /**
     * The version id of the target object.
     */
    public val versionId: String?
        get() = parameters["versionId"]

    public inline fun copy(block: Builder.() -> Unit = {}): GetObjectAclRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetObjectAclRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The name of the object.
         */
        public var key: String? = null

        /**
         * The version id of the target object.
         */
        public var versionId: String?
            set(value) {
                value?.let { this.parameters["versionId"] = it }
            }
            get() = parameters["versionId"]

        public fun build(): GetObjectAclRequest {
            return GetObjectAclRequest(this)
        }

        public constructor(from: GetObjectAclRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.key = from.key
        }
    }
}
