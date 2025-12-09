package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.progress.ProgressListener
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream

/**
 * The request for the AppendObject operation.
 */
public class AppendObjectRequest(builder: Builder) : RequestModel(builder) {

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
     * The method used to encrypt objects on the specified OSS server.
     * Valid values:
     * - AES256: Keys managed by OSS are used for encryption and decryption (SSE-OSS).
     * - KMS: Keys managed by Key Management Service (KMS) are used for encryption and decryption.
     * - SM4: The SM4 block cipher algorithm is used for encryption and decryption.
     */
    public val serverSideEncryption: String?
        get() = headers["x-oss-server-side-encryption"]

    /**
     * The access control list (ACL) of the object. Default value: default.  V
     * alid values:
     * - default: The ACL of the object is the same as that of the bucket in which the object is stored.
     * - private: The ACL of the object is private. Only the owner of the object and authorized users can read and write this object.
     * - public-read: The ACL of the object is public-read. Only the owner of the object and authorized users can read and write this object. Other users can only read the object. Exercise caution when you set the object ACL to this value.
     * - public-read-write: The ACL of the object is public-read-write. All users can read and write this object. Exercise caution when you set the object ACL to this value.
     * For more information about the ACL, see [ACL](~~100676~~).
     */
    public val objectAcl: String?
        get() = headers["x-oss-object-acl"]

    /**
     * The storage class of the object that you want to upload.
     * Valid values:
     * - Standard
     * - IA
     * - Archive
     * If you specify the object storage class when you upload an object, the storage class of the uploaded object is the specified value regardless of the storage class of the bucket to which the object is uploaded.
     * If you set x-oss-storage-class to Standard when you upload an object to an IA bucket, the object is stored as a Standard object.
     * For more information about storage classes, see the "Overview" topic in Developer Guide. notice The value that you specify takes effect only when you call the AppendObject operation on an object for the first time.
     */
    public val storageClass: String?
        get() = headers["x-oss-storage-class"]

    /**
     * A map of metadata to store with the object.
     * The number of metadata entries returned in the headers that are prefixed with x-oss-meta-
     */
    public val metadata: Map<String, String>?
        get() = headers.filter { it.key.startsWith("x-oss-meta-") }.mapKeys { it.key.substring(11) }

    /**
     * The web page caching behavior for the object. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**.
     * Default value: null.
     */
    public val cacheControl: String?
        get() = headers["Cache-Control"]

    /**
     * The name of the object when the object is downloaded. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**.
     * Default value: null.
     */
    public val contentDisposition: String?
        get() = headers["Content-Disposition"]

    /**
     * The encoding format of the object content. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**.
     * Default value: null.
     */
    public val contentEncoding: String?
        get() = headers["Content-Encoding"]

    /**
     * The Content-MD5 header value is a string calculated by using the MD5 algorithm. The header is used to check whether the content of the received message is the same as that of the sent message. To obtain the value of the Content-MD5 header, calculate a 128-bit number based on the message content except for the header, and then encode the number in Base64.
     * Default value: null.Limits: none.
     */
    public val contentMd5: String?
        get() = headers["Content-MD5"]

    /**
     * The expiration time. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**.
     * Default value: null.
     */
    public val expires: String?
        get() = headers["Expires"]

    /**
     * The position from which the AppendObject operation starts.
     * Each time an AppendObject operation succeeds, the x-oss-next-append-position header is included in the response to specify the position from which the next AppendObject operation starts. The value of position in the first AppendObject operation performed on an object must be 0. The value of position in subsequent AppendObject operations performed on the object is the current length of the object.
     * For example, if the value of position specified in the first AppendObject request is 0 and the value of content-length is 65536, the value of position in the second AppendObject request must be 65536.
     * - If the value of position in the AppendObject request is 0 and the name of the object that you want to append is unique, you can set headers such as x-oss-server-side-encryption in an AppendObject request in the same way as you set in a PutObject request. If you add the x-oss-server-side-encryption header to an AppendObject request, the x-oss-server-side-encryption header is included in the response to the request. If you want to modify metadata, you can call the CopyObject operation.
     * - If you call an AppendObject operation to append a 0 KB object whose position value is valid to an Appendable object, the status of the Appendable object is not changed.
     */
    public val position: Long?
        get() = parameters["position"]?.toLong()

    /**
     * The request body.
     */
    public var body: ByteStream? = builder.body

    /**
     * Gets the initial value of CRC64. If not set, the crc check is ignored.
     */
    public val initHashCRC64: Long? = builder.initHashCRC64

    public inline fun copy(block: Builder.() -> Unit = {}): AppendObjectRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): AppendObjectRequest =
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
         * The method used to encrypt objects on the specified OSS server.
         * Valid values:
         * - AES256: Keys managed by OSS are used for encryption and decryption (SSE-OSS).
         * - KMS: Keys managed by Key Management Service (KMS) are used for encryption and decryption.
         * - SM4: The SM4 block cipher algorithm is used for encryption and decryption.
         */
        public var serverSideEncryption: String?
            set(value) {
                value?.let { this.headers["x-oss-server-side-encryption"] = it }
            }
            get() = headers["x-oss-server-side-encryption"]

        /**
         * The access control list (ACL) of the object. Default value: default.
         * Valid values:
         * - default: The ACL of the object is the same as that of the bucket in which the object is stored.
         * - private: The ACL of the object is private. Only the owner of the object and authorized users can read and write this object.
         * - public-read: The ACL of the object is public-read. Only the owner of the object and authorized users can read and write this object. Other users can only read the object. Exercise caution when you set the object ACL to this value.
         * - public-read-write: The ACL of the object is public-read-write. All users can read and write this object. Exercise caution when you set the object ACL to this value.
         * For more information about the ACL, see [ACL](~~100676~~).
         */
        public var objectAcl: String?
            set(value) {
                value?.let { this.headers["x-oss-object-acl"] = it }
            }
            get() = headers["x-oss-object-acl"]

        /**
         * The storage class of the object that you want to upload.
         * Valid values:
         * - Standard
         * - IA
         * - Archive
         * If you specify the object storage class when you upload an object, the storage class of the uploaded object is the specified value regardless of the storage class of the bucket to which the object is uploaded.
         * If you set x-oss-storage-class to Standard when you upload an object to an IA bucket, the object is stored as a Standard object.
         * For more information about storage classes, see the "Overview" topic in Developer Guide. notice The value that you specify takes effect only when you call the AppendObject operation on an object for the first time.
         */
        public var storageClass: String?
            set(value) {
                value?.let { this.headers["x-oss-storage-class"] = it }
            }
            get() = headers["x-oss-storage-class"]

        /**
         * The metadata of the object that you want to upload.
         */
        public var metadata: Map<String, String>?
            set(value) {
                value?.forEach { headers["x-oss-meta-${it.key}"] = it.value }
            }
            get() = headers.filter { it.key.startsWith("x-oss-meta-") }.mapKeys { it.key.substring(11) }

        /**
         * The web page caching behavior for the object. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**.
         * Default value: null.
         */
        public var cacheControl: String?
            set(value) {
                value?.let { this.headers["Cache-Control"] = it }
            }
            get() = headers["Cache-Control"]

        /**
         * The name of the object when the object is downloaded. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**.
         * Default value: null.
         */
        public var contentDisposition: String?
            set(value) {
                value?.let { this.headers["Content-Disposition"] = it }
            }
            get() = headers["Content-Disposition"]

        /**
         * The encoding format of the object content. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**.
         * Default value: null.
         */
        public var contentEncoding: String?
            set(value) {
                value?.let { this.headers["Content-Encoding"] = it }
            }
            get() = headers["Content-Encoding"]

        /**
         * The Content-MD5 header value is a string calculated by using the MD5 algorithm. The header is used to check whether the content of the received message is the same as that of the sent message. To obtain the value of the Content-MD5 header, calculate a 128-bit number based on the message content except for the header, and then encode the number in Base64.
         * Default value: null.Limits: none.
         */
        public var contentMd5: String?
            set(value) {
                value?.let { this.headers["Content-MD5"] = it }
            }
            get() = headers["Content-MD5"]

        /**
         * The expiration time. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**.
         * Default value: null.
         */
        public var expires: String?
            set(value) {
                value?.let { this.headers["Expires"] = it }
            }
            get() = headers["Expires"]

        /**
         * The position from which the AppendObject operation starts.
         * Each time an AppendObject operation succeeds, the x-oss-next-append-position header is included in the response to specify the position from which the next AppendObject operation starts. The value of position in the first AppendObject operation performed on an object must be 0.
         * The value of position in subsequent AppendObject operations performed on the object is the current length of the object. For example, if the value of position specified in the first AppendObject request is 0 and the value of content-length is 65536, the value of position in the second AppendObject request must be 65536.
         * - If the value of position in the AppendObject request is 0 and the name of the object that you want to append is unique, you can set headers such as x-oss-server-side-encryption in an AppendObject request in the same way as you set in a PutObject request. If you add the x-oss-server-side-encryption header to an AppendObject request, the x-oss-server-side-encryption header is included in the response to the request. If you want to modify metadata, you can call the CopyObject operation.
         * - If you call an AppendObject operation to append a 0 KB object whose position value is valid to an Appendable object, the status of the Appendable object is not changed.
         */
        public var position: Long?
            set(value) {
                value?.let { parameters["position"] = it.toString() }
            }
            get() = parameters["position"]?.toLong()

        /**
         * The request body.
         */
        public var body: ByteStream? = null

        /**
         * Gets the initial value of CRC64. If not set, the crc check is ignored.
         */
        public var initHashCRC64: Long? = null

        public fun build(): AppendObjectRequest {
            return AppendObjectRequest(this)
        }

        public constructor(from: AppendObjectRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.key = from.key
            this.body = from.body
        }
    }
}
