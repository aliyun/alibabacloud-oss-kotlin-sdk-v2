package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The container that stores all information returned for the GetBucketStat request.
 */
public class BucketStat(builder: Builder) {
    /**
     * The number of delete marker in the bucket.
     */
    public val deleteMarkerCount: Long? = builder.deleteMarkerCount

    /**
     * The number of IA objects in the bucket.
     */
    public val infrequentAccessObjectCount: Long? = builder.infrequentAccessObjectCount

    /**
     * The number of Archive objects in the bucket.
     */
    public val archiveObjectCount: Long? = builder.archiveObjectCount

    /**
     * The billed storage usage of Deep Cold Archive objects in the bucket. Unit: bytes.
     */
    public val deepColdArchiveStorage: Long? = builder.deepColdArchiveStorage

    /**
     * The actual storage usage of Deep Cold Archive objects in the bucket. Unit: bytes.
     */
    public val deepColdArchiveRealStorage: Long? = builder.deepColdArchiveRealStorage

    /**
     * The storage usage of the bucket. Unit: bytes.
     */
    public val storage: Long? = builder.storage

    /**
     * The number of mulitpart parts in the bucket.
     */
    public val multipartPartCount: Long? = builder.multipartPartCount

    /**
     * The actual storage usage of Archive objects in the bucket. Unit: bytes.
     */
    public val archiveRealStorage: Long? = builder.archiveRealStorage

    /**
     * The actual storage usage of Cold Archive objects in the bucket. Unit: bytes.
     */
    public val coldArchiveRealStorage: Long? = builder.coldArchiveRealStorage

    /**
     * The number of Cold Archive objects in the bucket.
     */
    public val coldArchiveObjectCount: Long? = builder.coldArchiveObjectCount

    /**
     * The number of Deep Cold Archive objects in the bucket.
     */
    public val deepColdArchiveObjectCount: Long? = builder.deepColdArchiveObjectCount

    /**
     * The total number of objects in the bucket.
     */
    public val objectCount: Long? = builder.objectCount

    /**
     * The number of Standard objects in the bucket.
     */
    public val standardObjectCount: Long? = builder.standardObjectCount

    /**
     * The billed storage usage of Archive objects in the bucket. Unit: bytes.
     */
    public val archiveStorage: Long? = builder.archiveStorage

    /**
     * The time when the obtained information was last modified. The value of this parameter is a UNIX timestamp. Unit: seconds.
     */
    public val lastModifiedTime: Long? = builder.lastModifiedTime

    /**
     * The billed storage usage of IA objects in the bucket. Unit: bytes.
     */
    public val infrequentAccessStorage: Long? = builder.infrequentAccessStorage

    /**
     * The storage usage of Standard objects in the bucket. Unit: bytes.
     */
    public val standardStorage: Long? = builder.standardStorage

    /**
     * The actual storage usage of IA objects in the bucket. Unit: bytes.
     */
    public val infrequentAccessRealStorage: Long? = builder.infrequentAccessRealStorage

    /**
     * The billed storage usage of Cold Archive objects in the bucket. Unit: bytes.
     */
    public val coldArchiveStorage: Long? = builder.coldArchiveStorage

    /**
     * The number of multipart upload tasks that have been initiated but are not completed or canceled.
     */
    public val multipartUploadCount: Long? = builder.multipartUploadCount

    /**
     * The number of LiveChannels in the bucket.
     */
    public val liveChannelCount: Long? = builder.liveChannelCount

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): BucketStat =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The number of delete marker in the bucket.
         */
        public var deleteMarkerCount: Long? = null

        /**
         * The number of IA objects in the bucket.
         */
        public var infrequentAccessObjectCount: Long? = null

        /**
         * The number of Archive objects in the bucket.
         */
        public var archiveObjectCount: Long? = null

        /**
         * The billed storage usage of Deep Cold Archive objects in the bucket. Unit: bytes.
         */
        public var deepColdArchiveStorage: Long? = null

        /**
         * The actual storage usage of Deep Cold Archive objects in the bucket. Unit: bytes.
         */
        public var deepColdArchiveRealStorage: Long? = null

        /**
         * The storage usage of the bucket. Unit: bytes.
         */
        public var storage: Long? = null

        /**
         * The number of multipart parts in the bucket.
         */
        public var multipartPartCount: Long? = null

        /**
         * The actual storage usage of Archive objects in the bucket. Unit: bytes.
         */
        public var archiveRealStorage: Long? = null

        /**
         * The actual storage usage of Cold Archive objects in the bucket. Unit: bytes.
         */
        public var coldArchiveRealStorage: Long? = null

        /**
         * The number of Cold Archive objects in the bucket.
         */
        public var coldArchiveObjectCount: Long? = null

        /**
         * The number of Deep Cold Archive objects in the bucket.
         */
        public var deepColdArchiveObjectCount: Long? = null

        /**
         * The total number of objects in the bucket.
         */
        public var objectCount: Long? = null

        /**
         * The number of Standard objects in the bucket.
         */
        public var standardObjectCount: Long? = null

        /**
         * The billed storage usage of Archive objects in the bucket. Unit: bytes.
         */
        public var archiveStorage: Long? = null

        /**
         * The time when the obtained information was last modified. The value of this parameter is a UNIX timestamp. Unit: seconds.
         */
        public var lastModifiedTime: Long? = null

        /**
         * The billed storage usage of IA objects in the bucket. Unit: bytes.
         */
        public var infrequentAccessStorage: Long? = null

        /**
         * The storage usage of Standard objects in the bucket. Unit: bytes.
         */
        public var standardStorage: Long? = null

        /**
         * The actual storage usage of IA objects in the bucket. Unit: bytes.
         */
        public var infrequentAccessRealStorage: Long? = null

        /**
         * The billed storage usage of Cold Archive objects in the bucket. Unit: bytes.
         */
        public var coldArchiveStorage: Long? = null

        /**
         * The number of multipart upload tasks that have been initiated but are not completed or canceled.
         */
        public var multipartUploadCount: Long? = null

        /**
         * The number of LiveChannels in the bucket.
         */
        public var liveChannelCount: Long? = null

        public fun build(): BucketStat {
            return BucketStat(this)
        }
    }
}
