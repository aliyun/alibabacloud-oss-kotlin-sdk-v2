package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The container that stores the information about multipart upload tasks.
 */
public class Upload(builder: Builder) {
    /**
     * The name of the object for which a multipart upload task was initiated.
     * The results returned by OSS are listed in ascending alphabetical order of object names. Multiple multipart upload tasks that are initiated to upload the same object are listed in ascending order of upload IDs.
     */
    public val key: String? = builder.key

    /**
     * The ID of the multipart upload task.
     */
    public val uploadId: String? = builder.uploadId

    /**
     * The time when the multipart upload task was initiated.
     */
    public val initiated: String? = builder.initiated

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): Upload =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The name of the object for which a multipart upload task was initiated.
         * The results returned by OSS are listed in ascending alphabetical order of object names. Multiple multipart upload tasks that are initiated to upload the same object are listed in ascending order of upload IDs.
         */
        public var key: String? = null

        /**
         * The ID of the multipart upload task.
         */
        public var uploadId: String? = null

        /**
         * The time when the multipart upload task was initiated.
         */
        public var initiated: String? = null

        public fun build(): Upload {
            return Upload(this)
        }
    }
}
