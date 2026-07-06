package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The container that stores the uploaded parts.
 */
public class Part(builder: Builder) {
    /**
     * The time when the part was uploaded.
     */
    public val lastModified: String? = builder.lastModified

    /**
     * The ETag value that is returned by OSS after the part is uploaded.
     */
    public val eTag: String? = builder.eTag

    /**
     * The part number.
     */
    public val partNumber: Long? = builder.partNumber

    /**
     * The size of the part.
     */
    public val size: Long? = builder.size

    /**
     * The CRC64 value of the part, returned by OSS when the object was uploaded with CRC64 enabled.
     */
    public val hashCrc64ecma: String? = builder.hashCrc64ecma

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): Part =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The time when the part was uploaded.
         */
        public var lastModified: String? = null

        /**
         * The ETag value that is returned by OSS after the part is uploaded.
         */
        public var eTag: String? = null

        /**
         * The part number.
         */
        public var partNumber: Long? = null

        /**
         * The size of the part.
         */
        public var size: Long? = null

        /**
         * The CRC64 value of the part, returned by OSS when the object was uploaded with CRC64 enabled.
         */
        public var hashCrc64ecma: String? = null

        public fun build(): Part {
            return Part(this)
        }
    }
}
