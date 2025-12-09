package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The configurations of the bucket storage class and redundancy type.
 */
public class CreateBucketConfiguration(builder: Builder) {
    /**
     * The storage class of the bucket. Valid values:
     * *   Standard (default)
     * *   IA
     * *   Archive
     * *   ColdArchive
     */
    public val storageClass: String? = builder.storageClass

    /**
     * The redundancy type of the bucket.
     * *   LRS (default)    LRS stores multiple copies of your data on multiple devices in the same zone. LRS ensures data durability and availability even if hardware failures occur on two devices.
     * *   ZRS    ZRS stores multiple copies of your data across three zones in the same region. Even if a zone becomes unavailable due to unexpected events, such as power outages and fires, data can still be accessed.
     *
     * You cannot set the redundancy type of Archive buckets to ZRS.
     */
    public val dataRedundancyType: String? = builder.dataRedundancyType

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): CreateBucketConfiguration =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The storage class of the bucket. Valid values:
         * *   Standard (default)
         * *   IA
         * *   Archive
         * *   ColdArchive
         */
        public var storageClass: String? = null

        /**
         * The redundancy type of the bucket.
         * *   LRS (default)    LRS stores multiple copies of your data on multiple devices in the same zone. LRS ensures data durability and availability even if hardware failures occur on two devices.
         * *   ZRS    ZRS stores multiple copies of your data across three zones in the same region. Even if a zone becomes unavailable due to unexpected events, such as power outages and fires, data can still be accessed.
         *
         * You cannot set the redundancy type of Archive buckets to ZRS.
         */
        public var dataRedundancyType: String? = null

        public fun build(): CreateBucketConfiguration {
            return CreateBucketConfiguration(this)
        }
    }
}
