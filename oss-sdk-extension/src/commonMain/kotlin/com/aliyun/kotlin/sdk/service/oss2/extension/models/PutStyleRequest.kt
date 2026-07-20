package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the PutStyle operation.
 */
public class PutStyleRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The name of the image style.
     */
    public val styleName: String?
        get() = parameters["styleName"]

    /**
     * The category of the style.
     */
    public val category: String?
        get() = parameters["category"]

    /**
     * The container that stores the content information about the image style.
     */
    public var style: Style? = builder.style

    public inline fun copy(block: Builder.() -> Unit = {}): PutStyleRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutStyleRequest =
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

        /**
         * The category of the style.
         */
        public var category: String?
            set(value) {
                this.parameters["category"] = requireNotNull(value)
            }
            get() = parameters["category"]

        /**
         * The container that stores the content information about the image style.
         */
        public var style: Style? = null

        public fun build(): PutStyleRequest {
            return PutStyleRequest(this)
        }

        public constructor(from: PutStyleRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.style = from.style
        }
    }
}
