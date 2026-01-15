package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores information about the bucket in which exported inventory lists are stored.
 */
@Serializable
@SerialName("OSSBucketDestination")
public data class InventoryOSSBucketDestination(
    /**
     * The name of the bucket in which exported inventory lists are stored.
     */
    @XmlElement("Bucket") public var bucket: String? = null,

    /**
     * The prefix of the path in which the exported inventory lists are stored.
     */
    @XmlElement("Prefix") public var prefix: String? = null,

    /**
     * The container that stores the encryption method of the exported inventory lists.
     */
    @XmlElement("Encryption") public var encryption: InventoryEncryption? = null,

    /**
     * The format of exported inventory lists. The exported inventory lists are CSV objects compressed by using GZIP.
     */
    @XmlElement("Format") public var format: String? = null,

    /**
     * The ID of the account to which permissions are granted by the bucket owner.
     */
    @XmlElement("AccountId") public var accountId: String? = null,

    /**
     * The Alibaba Cloud Resource Name (ARN) of the role that has the permissions to read all objects from the source bucket and write objects to the destination bucket. Format: `acs:ram::uid:role/rolename`.
     */
    @XmlElement("RoleArn") public var roleArn: String? = null
) {
    public companion object {
        public operator fun invoke(builder: InventoryOSSBucketDestination.() -> Unit): InventoryOSSBucketDestination =
            InventoryOSSBucketDestination().apply(builder)
    }
}
