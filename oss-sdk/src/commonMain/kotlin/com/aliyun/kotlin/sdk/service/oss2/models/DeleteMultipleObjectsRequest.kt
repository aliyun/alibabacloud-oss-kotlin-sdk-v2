package com.aliyun.kotlin.sdk.service.oss2.models

public class DeleteMultipleObjectsRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The delete object containing quiet mode and object identifiers.
     */
    public val delete: Delete? = builder.delete

    /**
     * The encoding type of the object names in the response. Valid value: url
     */
    public val encodingType: String?
        get() = headers["Encoding-type"]

    public inline fun copy(
        block: Builder.() -> Unit = {
        }
    ): DeleteMultipleObjectsRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DeleteMultipleObjectsRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The delete object containing quiet mode and object identifiers.
         */
        public var delete: Delete? = null

        /**
         * The encoding type of the object names in the response. Valid value: url
         */
        public var encodingType: String?
            set(value) {
                value?.let { headers["Encoding-type"] = it }
            }
            get() = headers["Encoding-type"]

        public fun build(): DeleteMultipleObjectsRequest {
            return DeleteMultipleObjectsRequest(this)
        }

        public constructor(from: DeleteMultipleObjectsRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.delete = from.delete
        }
    }
}
