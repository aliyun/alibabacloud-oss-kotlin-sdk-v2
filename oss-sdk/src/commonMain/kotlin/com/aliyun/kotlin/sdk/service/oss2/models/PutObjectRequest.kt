package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.progress.ProgressListener
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream

/**
 * The request for the PutObject operation.
 */
public class PutObjectRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The full path of the object.
     */
    public val key: String? = builder.key

    /**
     * The progress listener for monitoring data transfer during OSS operations.
     */
    public val progressListener: ProgressListener? = builder.progressListener

    /**
     * Specifies whether the object that is uploaded by calling the PutObject operation overwrites the existing object that has the same name.
     * When versioning is enabled or suspended for the bucket to which you want to upload the object, the **x-oss-forbid-overwrite** header does not take effect. In this case, the object that is uploaded by calling the PutObject operation overwrites the existing object that has the same name.
     * - If you do not specify the **x-oss-forbid-overwrite** header or set the **x-oss-forbid-overwrite** header to **false**, the object that is uploaded by calling the PutObject operation overwrites the existing object that has the same name.
     * - If the value of **x-oss-forbid-overwrite** is set to **true**, existing objects cannot be overwritten by objects that have the same names. If you specify the **x-oss-forbid-overwrite** request header, the queries per second (QPS) performance of OSS is degraded. If you want to use the **x-oss-forbid-overwrite** request header to perform a large number of operations (QPS greater than 1,000), contact technical support.
     * Default value: **false**.
     */
    public val forbidOverwrite: Boolean?
        get() = headers["x-oss-forbid-overwrite"]?.toBoolean()

    /**
     * The method that is used to encrypt the object on the OSS server when the object is created.
     * Valid values: **AES256**, **KMS**, and **SM4****.
     * If you specify the header, the header is returned in the response. OSS uses the method that is specified by this header to encrypt the uploaded object. When you download the encrypted object, the **x-oss-server-side-encryption** header is included in the response and the header value is set to the algorithm that is used to encrypt the object.
     */
    public val serverSideEncryption: String?
        get() = headers["x-oss-server-side-encryption"]

    /**
     * The encryption method on the server side when an object is created.
     * Valid values: **AES256**, **KMS**, and **SM4**.
     * If you specify the header, the header is returned in the response. OSS uses the method that is specified by this header to encrypt the uploaded object. When you download the encrypted object, the **x-oss-server-side-encryption** header is included in the response and the header value is set to the algorithm that is used to encrypt the object.
     */
    public val serverSideDataEncryption: String?
        get() = headers["x-oss-server-side-data-encryption"]

    /**
     * The ID of the customer master key (CMK) managed by Key Management Service (KMS). This header is valid only when the **x-oss-server-side-encryption** header is set to KMS.
     */
    public val serverSideEncryptionKeyId: String?
        get() = headers["x-oss-server-side-encryption-key-id"]

    /**
     * The access control list (ACL) of the object. Default value: default.
     * Valid values:
     * - default: The ACL of the object is the same as that of the bucket in which the object is stored.
     * - private: The ACL of the object is private. Only the owner of the object and authorized users can read and write this object.
     * - public-read: The ACL of the object is public-read. Only the owner of the object and authorized users can read and write this object. Other users can only read the object. Exercise caution when you set the object ACL to this value.
     * - public-read-write: The ACL of the object is public-read-write. All users can read and write this object. Exercise caution when you set the object ACL to this value.
     * For more information about the ACL, see **[ACL](~~100676~~)**.
     */
    public val objectAcl: String?
        get() = headers["x-oss-object-acl"]

    /**
     * The storage class of the bucket. Default value: Standard.
     * Valid values:
     * - Standard
     * - IA
     * - Archive
     * - ColdArchive
     */
    public val storageClass: String?
        get() = headers["x-oss-storage-class"]

    /**
     * The tag of the object. You can configure multiple tags for the object.
     * Example: TagA=A&TagB=B.
     * The key and value of a tag must be URL-encoded. If a tag does not contain an equal sign (=), the value of the tag is considered an empty string.
     */
    public val tagging: String?
        get() = headers["x-oss-tagging"]

    /**
     * A callback parameter is a Base64-encoded string that contains multiple fields in the JSON format.
     */
    public val callback: String?
        get() = headers["x-oss-callback"]

    /**
     * Configure custom parameters by using the callback-var parameter.
     */
    public val callbackVar: String?
        get() = headers["x-oss-callback-var"]

    /**
     * Specify the speed limit value.
     * The speed limit value ranges from 245760 to 838860800, with a unit of bit/s.
     */
    public val trafficLimit: String?
        get() = headers["x-oss-traffic-limit"]

    /**
     * The metadata of the object that you want to upload.
     */
    public val metadata: Map<String, String>?
        get() = headers.filter { it.key.startsWith("x-oss-meta-") }.mapKeys { it.key.substring(11) }

    /**
     * The body of the request.
     */
    public var body: ByteStream? = builder.body

    public inline fun copy(block: Builder.() -> Unit = {}): PutObjectRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutObjectRequest =
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
         * The progress listener for monitoring data transfer during OSS operations.
         */
        public var progressListener: ProgressListener? = null

        /**
         * Specifies whether the object that is uploaded by calling the PutObject operation overwrites the existing object that has the same name.
         * When versioning is enabled or suspended for the bucket to which you want to upload the object, the **x-oss-forbid-overwrite** header does not take effect. In this case, the object that is uploaded by calling the PutObject operation overwrites the existing object that has the same name.
         * - If you do not specify the **x-oss-forbid-overwrite** header or set the **x-oss-forbid-overwrite** header to **false**, the object that is uploaded by calling the PutObject operation overwrites the existing object that has the same name.
         * - If the value of **x-oss-forbid-overwrite** is set to **true**, existing objects cannot be overwritten by objects that have the same names. If you specify the **x-oss-forbid-overwrite** request header, the queries per second (QPS) performance of OSS is degraded. If you want to use the **x-oss-forbid-overwrite** request header to perform a large number of operations (QPS greater than 1,000), contact technical support.
         * Default value: **false**.
         */
        public var forbidOverwrite: Boolean?
            set(value) {
                value?.let { this.headers["x-oss-forbid-overwrite"] = it.toString() }
            }
            get() = headers["x-oss-forbid-overwrite"]?.toBoolean()

        /**
         * The method that is used to encrypt the object on the OSS server when the object is created.
         * Valid values: **AES256**, **KMS**, and **SM4****.
         * If you specify the header, the header is returned in the response. OSS uses the method that is specified by this header to encrypt the uploaded object. When you download the encrypted object, the **x-oss-server-side-encryption** header is included in the response and the header value is set to the algorithm that is used to encrypt the object.
         */
        public var serverSideEncryption: String?
            set(value) {
                value?.let { this.headers["x-oss-server-side-encryption"] = it }
            }
            get() = headers["x-oss-server-side-encryption"]

        /**
         * The encryption method on the server side when an object is created.
         * Valid values: **AES256**, **KMS**, and **SM4**.
         * If you specify the header, the header is returned in the response. OSS uses the method that is specified by this header to encrypt the uploaded object. When you download the encrypted object, the **x-oss-server-side-encryption** header is included in the response and the header value is set to the algorithm that is used to encrypt the object.
         */
        public var serverSideDataEncryption: String?
            set(value) {
                value?.let { this.headers["x-oss-server-side-data-encryption"] = it }
            }
            get() = headers["x-oss-server-side-data-encryption"]

        /**
         * The ID of the customer master key (CMK) managed by Key Management Service (KMS). This header is valid only when the **x-oss-server-side-encryption** header is set to KMS.
         */
        public var serverSideEncryptionKeyId: String?
            set(value) {
                value?.let { this.headers["x-oss-server-side-encryption-key-id"] = it }
            }
            get() = headers["x-oss-server-side-encryption-key-id"]

        /**
         * The access control list (ACL) of the object. Default value: default.
         * Valid values:
         * - default: The ACL of the object is the same as that of the bucket in which the object is stored.
         * - private: The ACL of the object is private. Only the owner of the object and authorized users can read and write this object.
         * - public-read: The ACL of the object is public-read. Only the owner of the object and authorized users can read and write this object. Other users can only read the object. Exercise caution when you set the object ACL to this value.
         * - public-read-write: The ACL of the object is public-read-write. All users can read and write this object. Exercise caution when you set the object ACL to this value.
         * For more information about the ACL, see **[ACL](~~100676~~)**.
         */
        public var objectAcl: String?
            set(value) {
                value?.let { this.headers["x-oss-object-acl"] = it }
            }
            get() = headers["x-oss-object-acl"]

        /**
         * The storage class of the bucket. Default value: Standard.
         * Valid values:
         * - Standard
         * - IA
         * - Archive
         * - ColdArchive
         */
        public var storageClass: String?
            set(value) {
                value?.let { this.headers["x-oss-storage-class"] = it }
            }
            get() = headers["x-oss-storage-class"]

        /**
         * The tag of the object.
         * You can configure multiple tags for the object. Example: TagA=A&TagB=B.
         * The key and value of a tag must be URL-encoded. If a tag does not contain an equal sign (=), the value of the tag is considered an empty string.
         */
        public var tagging: String?
            set(value) {
                value?.let { this.headers["x-oss-tagging"] = it }
            }
            get() = headers["x-oss-tagging"]

        /**
         * A callback parameter is a Base64-encoded string that contains multiple fields in the JSON format.
         */
        public var callback: String?
            set(value) {
                value?.let { this.headers["x-oss-callback"] = it }
            }
            get() = headers["x-oss-callback"]

        /**
         * Configure custom parameters by using the callback-var parameter.
         */
        public var callbackVar: String?
            set(value) {
                value?.let { this.headers["x-oss-callback-var"] = it }
            }
            get() = headers["x-oss-callback-var"]

        /**
         * Specify the speed limit value.
         * The speed limit value ranges from 245760 to 838860800, with a unit of bit/s.
         */
        public var trafficLimit: String?
            set(value) {
                value?.let { this.headers["x-oss-traffic-limit"] = it }
            }
            get() = headers["x-oss-traffic-limit"]

        /**
         * The metadata of the object that you want to upload.
         */
        public var metadata: Map<String, String>?
            set(value) {
                value?.forEach { headers["x-oss-meta-${it.key}"] = it.value }
            }
            get() = headers.filter { it.key.startsWith("x-oss-meta-") }.mapKeys { it.key.substring(11) }

        /**
         * The body of the request.
         */
        public var body: ByteStream? = null

        public fun build(): PutObjectRequest {
            return PutObjectRequest(this)
        }

        public constructor(from: PutObjectRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.key = from.key
            this.body = from.body
        }
    }
}
