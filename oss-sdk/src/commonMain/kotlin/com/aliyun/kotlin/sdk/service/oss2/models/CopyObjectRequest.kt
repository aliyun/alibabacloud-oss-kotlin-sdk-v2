package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the CopyObject operation.
 */
public class CopyObjectRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The full path of the object.
     */
    public val key: String? = builder.key

    /**
     * The name of the source bucket.
     */
    public val sourceBucket: String? = builder.sourceBucket

    /**
     * The full path of the source object.
     */
    public val sourceKey: String? = builder.sourceKey

    /**
     * The version id of the source object.
     */
    public val sourceVersionId: String?
        get() = parameters["versionId"]

    /**
     * Specifies whether the CopyObject operation overwrites objects with the same name. The **x-oss-forbid-overwrite** request header does not take effect when versioning is enabled or suspended for the destination bucket. In this case, the CopyObject operation overwrites the existing object that has the same name as the destination object.
     * *   If you do not specify the **x-oss-forbid-overwrite** header or set the header to **false**, an existing object that has the same name as the object that you want to copy is overwritten.
     * *   If you set the **x-oss-forbid-overwrite** header to **true**, an existing object that has the same name as the object that you want to copy is not overwritten.If you specify the **x-oss-forbid-overwrite** header, the queries per second (QPS) performance of OSS may be degraded. If you want to specify the **x-oss-forbid-overwrite** header in a large number of requests (QPS greater than 1,000), contact technical support.
     *
     * Default value: false.
     */
    public val forbidOverwrite: Boolean?
        get() = headers["x-oss-forbid-overwrite"].toBoolean()

    /**
     * The object copy condition. If the ETag value of the source object is the same as the ETag value that you specify in the request, OSS copies the object and returns 200 OK. By default, this header is left empty.
     */
    public val copySourceIfMatch: String?
        get() = headers["x-oss-copy-source-if-match"]

    /**
     * The object copy condition. If the ETag value of the source object is different from the ETag value that you specify in the request, OSS copies the object and returns 200 OK. By default, this header is left empty.
     */
    public val copySourceIfNoneMatch: String?
        get() = headers["x-oss-copy-source-if-none-match"]

    /**
     * The object copy condition. If the time that you specify in the request is the same as or later than the modification time of the object, OSS copies the object and returns 200 OK. By default, this header is left empty.
     */
    public val copySourceIfUnmodifiedSince: String?
        get() = headers["x-oss-copy-source-if-unmodified-since"]

    /**
     * If the source object is modified after the time that you specify in the request, OSS copies the object. By default, this header is left empty.
     */
    public val copySourceIfModifiedSince: String?
        get() = headers["x-oss-copy-source-if-modified-since"]

    /**
     * The method that is used to configure the metadata of the destination object. Default value: COPY.
     * *   **COPY**: The metadata of the source object is copied to the destination object. The **x-oss-server-side-encryption** attribute of the source object is not copied to the destination object. The **x-oss-server-side-encryption** header in the CopyObject request specifies the method that is used to encrypt the destination object.
     * *   **REPLACE**: The metadata that you specify in the request is used as the metadata of the destination object.  If the path of the source object is the same as the path of the destination object and versioning is disabled for the bucket in which the source and destination objects are stored, the metadata that you specify in the CopyObject request is used as the metadata of the destination object regardless of the value of the x-oss-metadata-directive header.
     */
    public val metadataDirective: String?
        get() = headers["x-oss-metadata-directive"]

    /**
     * The entropy coding-based encryption algorithm that OSS uses to encrypt an object when you create the object. The valid values of the header are **AES256** and **KMS**. You must activate Key Management Service (KMS) in the OSS console before you can use the KMS encryption algorithm. Otherwise, the KmsServiceNotEnabled error is returned.
     * *   If you do not specify the **x-oss-server-side-encryption** header in the CopyObject request, the destination object is not encrypted on the server regardless of whether the source object is encrypted on the server.
     * *   If you specify the **x-oss-server-side-encryption** header in the CopyObject request, the destination object is encrypted on the server after the CopyObject operation is performed regardless of whether the source object is encrypted on the server. In addition, the response to a CopyObject request contains the **x-oss-server-side-encryption** header whose value is the encryption algorithm of the destination object. When the destination object is downloaded, the **x-oss-server-side-encryption** header is included in the response. The value of this header is the encryption algorithm of the destination object.
     */
    public val serverSideEncryption: String?
        get() = headers["x-oss-server-side-encryption"]

    /**
     * The server side data encryption algorithm. Invalid value: SM4
     */
    public val serverSideDataEncryption: String?
        get() = headers["x-oss-server-side-data-encryption"]

    /**
     * The ID of the customer master key (CMK) that is managed by KMS. This parameter is available only if you set **x-oss-server-side-encryption** to KMS.
     */
    public val serverSideEncryptionKeyId: String?
        get() = headers["x-oss-server-side-encryption-key-id"]

    /**
     * The access control list (ACL) of the destination object when the object is created. Default value: default.Valid values:
     * *   default: The ACL of the object is the same as the ACL of the bucket in which the object is stored.
     * *   private: The ACL of the object is private. Only the owner of the object and authorized users have read and write permissions on the object. Other users do not have permissions on the object.
     * *   public-read: The ACL of the object is public-read. Only the owner of the object and authorized users have read and write permissions on the object. Other users have only read permissions on the object. Exercise caution when you set the ACL of the bucket to this value.
     * *   public-read-write: The ACL of the object is public-read-write. All users have read and write permissions on the object. Exercise caution when you set the ACL of the bucket to this value.
     *
     * For more information about ACLs, see [Object ACL](~~100676~~).
     */
    public val objectAcl: String?
        get() = headers["x-oss-object-acl"]

    /**
     * The storage class of the object that you want to upload. Default value: Standard. If you specify a storage class when you upload the object, the storage class applies regardless of the storage class of the bucket to which you upload the object. For example, if you set **x-oss-storage-class** to Standard when you upload an object to an IA bucket, the storage class of the uploaded object is Standard.
     * Valid values:
     * *   Standard
     * *   IA
     * *   Archive
     * *   ColdArchive
     *
     * For more information about storage classes, see [Overview](~~51374~~).
     */
    public val storageClass: String?
        get() = headers["x-oss-storage-class"]

    /**
     * The tag of the destination object. You can add multiple tags to the destination object. Example: TagA=A\&TagB=B.  The tag key and tag value must be URL-encoded. If a key-value pair does not contain an equal sign (=), the tag value is considered an empty string.
     */
    public val tagging: String?
        get() = headers["x-oss-tagging"]

    /**
     * The method that is used to add tags to the destination object. Default value: Copy.
     * Valid values:
     * *   **Copy**: The tags of the source object are copied to the destination object.
     * *   **Replace**: The tags that you specify in the request are added to the destination object.
     */
    public val taggingDirective: String?
        get() = headers["x-oss-tagging-directive"]

    /**
     * A map of metadata to store with the object.
     * The number of metadata entries returned in the headers that are prefixed with x-oss-meta-
     */
    public val metadata: Map<String, String>?
        get() = headers.filter { it.key.startsWith("x-oss-meta-") }.mapKeys { it.key.substring(11) }

    public inline fun copy(block: Builder.() -> Unit = {}): CopyObjectRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): CopyObjectRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The full path of the object.
         */
        public var key: String? = null

        /**
         * The name of the source bucket.
         */
        public var sourceBucket: String? = null

        /**
         * The full path of the source object.
         */
        public var sourceKey: String? = null

        /**
         * The version id of the source object.
         */
        public var sourceVersionId: String?
            set(value) {
                value?.let { this.parameters["versionId"] = it }
            }
            get() = parameters["versionId"]

        /**
         * Specifies whether the CopyObject operation overwrites objects with the same name. The **x-oss-forbid-overwrite** request header does not take effect when versioning is enabled or suspended for the destination bucket. In this case, the CopyObject operation overwrites the existing object that has the same name as the destination object.
         * *   If you do not specify the **x-oss-forbid-overwrite** header or set the header to **false**, an existing object that has the same name as the object that you want to copy is overwritten.
         * *   If you set the **x-oss-forbid-overwrite** header to **true**, an existing object that has the same name as the object that you want to copy is not overwritten.If you specify the **x-oss-forbid-overwrite** header, the queries per second (QPS) performance of OSS may be degraded. If you want to specify the **x-oss-forbid-overwrite** header in a large number of requests (QPS greater than 1,000), contact technical support.
         *
         * Default value: false.
         */
        public var forbidOverwrite: Boolean?
            set(value) {
                value?.let { this.headers["x-oss-forbid-overwrite"] = it.toString() }
            }
            get() = headers["x-oss-forbid-overwrite"].toBoolean()

        /**
         * The object copy condition. If the ETag value of the source object is the same as the ETag value that you specify in the request, OSS copies the object and returns 200 OK. By default, this header is left empty.
         */
        public var copySourceIfMatch: String?
            set(value) {
                value?.let { this.headers["x-oss-copy-source-if-match"] = it }
            }
            get() = headers["x-oss-copy-source-if-match"]

        /**
         * The object copy condition. If the ETag value of the source object is different from the ETag value that you specify in the request, OSS copies the object and returns 200 OK. By default, this header is left empty.
         */
        public var copySourceIfNoneMatch: String?
            set(value) {
                value?.let { this.headers["x-oss-copy-source-if-none-match"] = it }
            }
            get() = headers["x-oss-copy-source-if-none-match"]

        /**
         * The object copy condition. If the time that you specify in the request is the same as or later than the modification time of the object, OSS copies the object and returns 200 OK. By default, this header is left empty.
         */
        public var copySourceIfUnmodifiedSince: String?
            set(value) {
                value?.let { this.headers["x-oss-copy-source-if-unmodified-since"] = it }
            }
            get() = headers["x-oss-copy-source-if-unmodified-since"]

        /**
         * If the source object is modified after the time that you specify in the request, OSS copies the object. By default, this header is left empty.
         */
        public var copySourceIfModifiedSince: String?
            set(value) {
                value?.let { this.headers["x-oss-copy-source-if-modified-since"] = it }
            }
            get() = headers["x-oss-copy-source-if-modified-since"]

        /**
         * The method that is used to configure the metadata of the destination object. Default value: COPY.
         * *   **COPY**: The metadata of the source object is copied to the destination object. The **x-oss-server-side-encryption** attribute of the source object is not copied to the destination object. The **x-oss-server-side-encryption** header in the CopyObject request specifies the method that is used to encrypt the destination object.
         * *   **REPLACE**: The metadata that you specify in the request is used as the metadata of the destination object.  If the path of the source object is the same as the path of the destination object and versioning is disabled for the bucket in which the source and destination objects are stored, the metadata that you specify in the CopyObject request is used as the metadata of the destination object regardless of the value of the x-oss-metadata-directive header.
         */
        public var metadataDirective: String?
            set(value) {
                value?.let { this.headers["x-oss-metadata-directive"] = it }
            }
            get() = headers["x-oss-metadata-directive"]

        /**
         * The entropy coding-based encryption algorithm that OSS uses to encrypt an object when you create the object. The valid values of the header are **AES256** and **KMS**. You must activate Key Management Service (KMS) in the OSS console before you can use the KMS encryption algorithm. Otherwise, the KmsServiceNotEnabled error is returned.
         * *   If you do not specify the **x-oss-server-side-encryption** header in the CopyObject request, the destination object is not encrypted on the server regardless of whether the source object is encrypted on the server.
         * *   If you specify the **x-oss-server-side-encryption** header in the CopyObject request, the destination object is encrypted on the server after the CopyObject operation is performed regardless of whether the source object is encrypted on the server. In addition, the response to a CopyObject request contains the **x-oss-server-side-encryption** header whose value is the encryption algorithm of the destination object. When the destination object is downloaded, the **x-oss-server-side-encryption** header is included in the response. The value of this header is the encryption algorithm of the destination object.
         */
        public var serverSideEncryption: String?
            set(value) {
                value?.let { this.headers["x-oss-server-side-encryption"] = it }
            }
            get() = headers["x-oss-server-side-encryption"]

        /**
         * The server side data encryption algorithm. Invalid value: SM4
         */
        public var serverSideDataEncryption: String?
            set(value) {
                value?.let { this.headers["x-oss-server-side-data-encryption"] = it }
            }
            get() = headers["x-oss-server-side-data-encryption"]

        /**
         * The ID of the customer master key (CMK) that is managed by KMS. This parameter is available only if you set **x-oss-server-side-encryption** to KMS.
         */
        public var serverSideEncryptionKeyId: String?
            set(value) {
                value?.let { this.headers["x-oss-server-side-encryption-key-id"] = it }
            }
            get() = headers["x-oss-server-side-encryption-key-id"]

        /**
         * The access control list (ACL) of the destination object when the object is created. Default value: default.Valid values:
         * *   default: The ACL of the object is the same as the ACL of the bucket in which the object is stored.
         * *   private: The ACL of the object is private. Only the owner of the object and authorized users have read and write permissions on the object. Other users do not have permissions on the object.
         * *   public-read: The ACL of the object is public-read. Only the owner of the object and authorized users have read and write permissions on the object. Other users have only read permissions on the object. Exercise caution when you set the ACL of the bucket to this value.
         * *   public-read-write: The ACL of the object is public-read-write. All users have read and write permissions on the object. Exercise caution when you set the ACL of the bucket to this value.
         *
         * For more information about ACLs, see [Object ACL](~~100676~~).
         */
        public var objectAcl: String?
            set(value) {
                value?.let { this.headers["x-oss-object-acl"] = it }
            }
            get() = headers["x-oss-object-acl"]

        /**
         * The storage class of the object that you want to upload. Default value: Standard. If you specify a storage class when you upload the object, the storage class applies regardless of the storage class of the bucket to which you upload the object. For example, if you set **x-oss-storage-class** to Standard when you upload an object to an IA bucket, the storage class of the uploaded object is Standard.
         * Valid values:
         * *   Standard
         * *   IA
         * *   Archive
         * *   ColdArchive
         *
         * For more information about storage classes, see [Overview](~~51374~~).
         */
        public var storageClass: String?
            set(value) {
                value?.let { this.headers["x-oss-storage-class"] = it }
            }
            get() = headers["x-oss-storage-class"]

        /**
         * The tag of the destination object. You can add multiple tags to the destination object. Example: TagA=A\&TagB=B.  The tag key and tag value must be URL-encoded. If a key-value pair does not contain an equal sign (=), the tag value is considered an empty string.
         */
        public var tagging: String?
            set(value) {
                value?.let { this.headers["x-oss-tagging"] = it }
            }
            get() = headers["x-oss-tagging"]

        /**
         * The method that is used to add tags to the destination object. Default value: Copy. Valid values:
         * * **Copy**: The tags of the source object are copied to the destination object.
         * * **Replace**: The tags that you specify in the request are added to the destination object.
         */
        public var taggingDirective: String?
            set(value) {
                value?.let { this.headers["x-oss-tagging-directive"] = it }
            }
            get() = headers["x-oss-tagging-directive"]

        /**
         * A map of metadata to store with the object.
         * The number of metadata entries returned in the headers that are prefixed with x-oss-meta-
         */
        public var metadata: Map<String, String>?
            set(value) {
                value?.forEach { headers["x-oss-meta-${it.key}"] = it.value }
            }
            get() = headers.filter { it.key.startsWith("x-oss-meta-") }.mapKeys { it.key.substring(11) }

        public fun build(): CopyObjectRequest {
            return CopyObjectRequest(this)
        }

        public constructor(from: CopyObjectRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.key = from.key
        }
    }
}
