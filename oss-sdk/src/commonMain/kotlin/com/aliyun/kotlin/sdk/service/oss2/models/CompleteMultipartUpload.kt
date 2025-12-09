package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The container that stores the content of the CompleteMultipartUpload request.
 */
public class CompleteMultipartUpload(builder: Builder) {

    /**
     * The container that stores the uploaded parts.
     */
    public val parts: List<Part>? = builder.parts

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): CompleteMultipartUpload =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The container that stores the uploaded parts.
         */
        public var parts: List<Part>? = null

        public fun build(): CompleteMultipartUpload {
            return CompleteMultipartUpload(this)
        }
    }
}
