package com.aliyun.kotlin.sdk.service.oss2.models

public class UploadPart(builder: Builder) {
    /**
     * The ETag value that is returned by OSS after the part is uploaded.
     */
    public val eTag: String? = builder.eTag

    /**
     * The part number.
     */
    public val partNumber: Long? = builder.partNumber

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): UploadPart =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The ETag value that is returned by OSS after the part is uploaded.
         */
        public var eTag: String? = null

        /**
         * The part number.
         */
        public var partNumber: Long? = null

        public fun build(): UploadPart {
            return UploadPart(this)
        }
    }
}

