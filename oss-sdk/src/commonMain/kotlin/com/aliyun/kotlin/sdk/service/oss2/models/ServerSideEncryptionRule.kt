package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The container that stores server-side encryption rules.
 */
public class ServerSideEncryptionRule(builder: Builder) {
    /**
     * The default server-side encryption method.
     * Valid values: KMS, AES256, and SM4.
     * You are charged when you call API operations to encrypt or decrypt data by using CMKs managed by KMS.
     * For more information, see [Billing of KMS](~~52608~~). If the default server-side encryption method is configured for the destination bucket and ReplicaCMKID is configured in the CRR rule:*   If objects in the source bucket are not encrypted, they are encrypted by using the default encryption method of the destination bucket after they are replicated.*   If objects in the source bucket are encrypted by using SSE-KMS or SSE-OSS, they are encrypted by using the same method after they are replicated.For more information, see [Use data replication with server-side encryption](~~177216~~).
     */
    public val sSEAlgorithm: String? = builder.sSEAlgorithm

    /**
     * The CMK ID that is specified when SSEAlgorithm is set to KMS and a specified CMK is used for encryption. In other cases, leave this parameter empty.
     */
    public val kMSMasterKeyID: String? = builder.kMSMasterKeyID

    /**
     * The algorithm that is used to encrypt objects. If this parameter is not specified, objects are encrypted by using AES256. This parameter is valid only when SSEAlgorithm is set to KMS.
     * Valid value: SM4.
     */
    public val kMSDataEncryption: String? = builder.kMSDataEncryption

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ServerSideEncryptionRule =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The default server-side encryption method. Valid values: KMS, AES256, and SM4. You are charged when you call API operations to encrypt or decrypt data by using CMKs managed by KMS.
         * For more information, see [Billing of KMS](~~52608~~). If the default server-side encryption method is configured for the destination bucket and ReplicaCMKID is configured in the CRR rule:*   If objects in the source bucket are not encrypted, they are encrypted by using the default encryption method of the destination bucket after they are replicated.*   If objects in the source bucket are encrypted by using SSE-KMS or SSE-OSS, they are encrypted by using the same method after they are replicated.For more information, see [Use data replication with server-side encryption](~~177216~~).
         */
        public var sSEAlgorithm: String? = null

        /**
         * The CMK ID that is specified when SSEAlgorithm is set to KMS and a specified CMK is used for encryption. In other cases, leave this parameter empty.
         */
        public var kMSMasterKeyID: String? = null

        /**
         * The algorithm that is used to encrypt objects. If this parameter is not specified, objects are encrypted by using AES256. This parameter is valid only when SSEAlgorithm is set to KMS.
         * Valid value: SM4.
         */
        public var kMSDataEncryption: String? = null

        public fun build(): ServerSideEncryptionRule {
            return ServerSideEncryptionRule(this)
        }
    }
}
