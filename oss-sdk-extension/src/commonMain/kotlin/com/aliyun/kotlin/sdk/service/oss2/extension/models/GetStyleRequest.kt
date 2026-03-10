package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the GetStyle operation.
 */
public class GetStyleRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The name of the image style.
     */
    public val styleName: String?
        get() = parameters["styleName"]

    public inline fun copy(block: Builder.() -> Unit = {}): GetStyleRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetStyleRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The name of the image style.
         */
        public var styleName: String?
            set(value) {
                this.parameters["styleName"] = requireNotNull(value)
            }
            get() = parameters["styleName"]

        public fun build(): GetStyleRequest {
            return GetStyleRequest(this)
        }

        public constructor(from: GetStyleRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
        }
    }
}
