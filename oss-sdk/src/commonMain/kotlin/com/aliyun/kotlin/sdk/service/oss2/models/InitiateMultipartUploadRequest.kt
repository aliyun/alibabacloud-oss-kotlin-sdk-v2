package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the InitiateMultipartUpload operation.
 */
public class InitiateMultipartUploadRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket to which the object is uploaded by the multipart upload task.
     */
    public val bucket: String? = builder.bucket

    /**
     * The name of the object that is uploaded by the multipart upload task.
     */
    public val key: String? = builder.key

    /**
     * Specifies whether the InitiateMultipartUpload operation overwrites the existing object that has the same name as the object that you want to upload. When versioning is enabled or suspended for the bucket to which you want to upload the object, the **x-oss-forbid-overwrite** header does not take effect. In this case, the InitiateMultipartUpload operation overwrites the existing object that has the same name as the object that you want to upload.
     * - If you do not specify the **x-oss-forbid-overwrite** header or set the **x-oss-forbid-overwrite** header to **false**, the object that is uploaded by calling the PutObject operation overwrites the existing object that has the same name.
     * - If the value of **x-oss-forbid-overwrite** is set to **true**, existing objects cannot be overwritten by objects that have the same names. If you specify the **x-oss-forbid-overwrite** request header, the queries per second (QPS) performance of OSS is degraded. If you want to use the **x-oss-forbid-overwrite** request header to perform a large number of operations (QPS greater than 1,000), contact technical support
     */
    public val forbidOverwrite: Boolean?
        get() = headers["x-oss-forbid-overwrite"].toBoolean()

    /**
     * The storage class of the bucket. Default value: Standard.  Valid values:- Standard- IA- Archive- ColdArchive
     */
    public val storageClass: String?
        get() = headers["x-oss-storage-class"]

    /**
     * The tag of the object. You can configure multiple tags for the object. Example: TagA=A&amp;TagB=B. The key and value of a tag must be URL-encoded. If a tag does not contain an equal sign (=), the value of the tag is considered an empty string.
     */
    public val tagging: String?
        get() = headers["x-oss-tagging"]

    /**
     * The server-side encryption method that is used to encrypt each part of the object that you want to upload.
     * Valid values: **AES256**, **KMS**, and **SM4**.
     * You must activate Key Management Service (KMS) before you set this header to KMS. If you specify this header in the request, this header is included in the response. OSS uses the method specified by this header to encrypt each uploaded part. When you download the object, the x-oss-server-side-encryption header is included in the response and the header value is set to the algorithm that is used to encrypt the object.
     */
    public val serverSideEncryption: String?
        get() = headers["x-oss-server-side-encryption"]

    /**
     * The algorithm that is used to encrypt the object that you want to upload. If this header is not specified, the object is encrypted by using AES-256. This header is valid only when **x-oss-server-side-encryption** is set to KMS. Valid value: SM4.
     */
    public val serverSideDataEncryption: String?
        get() = headers["x-oss-server-side-data-encryption"]

    /**
     * The ID of the CMK that is managed by KMS. This header is valid only when **x-oss-server-side-encryption** is set to KMS.
     */
    public val serverSideEncryptionKeyId: String?
        get() = headers["x-oss-server-side-encryption-key-id"]

    /**
     * The caching behavior of the web page when the object is downloaded. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**. Default value: null.
     */
    public val cacheControl: String?
        get() = headers["Cache-Control"]

    /**
     * The name of the object when the object is downloaded. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**. Default value: null.
     */
    public val contentDisposition: String?
        get() = headers["Content-Disposition"]

    /**
     * The content encoding format of the object when the object is downloaded. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**. Default value: null.
     */
    public val contentEncoding: String?
        get() = headers["Content-Encoding"]

    /**
     * The expiration time of the request. Unit: milliseconds. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**. Default value: null.
     */
    public val expires: String?
        get() = headers["Expires"]

    /**
     * The method used to encode the object name in the response. Only URL encoding is supported. The object name can contain characters encoded in UTF-8. However, the XML 1.0 standard cannot be used to parse specific control characters, such as characters whose ASCII values range from 0 to 10. You can configure the encoding-type parameter to encode object names that include characters that cannot be parsed by XML 1.0 in the response.brDefault value: null
     */
    public val encodingType: String?
        get() = parameters["encoding-type"]

    public inline fun copy(
        block: Builder.() -> Unit = {
        }
    ): InitiateMultipartUploadRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): InitiateMultipartUploadRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket to which the object is uploaded by the multipart upload task.
         */
        public var bucket: String? = null

        /**
         * The name of the object that is uploaded by the multipart upload task.
         */
        public var key: String? = null

        /**
         * Specifies whether the InitiateMultipartUpload operation overwrites the existing object that has the same name as the object that you want to upload. When versioning is enabled or suspended for the bucket to which you want to upload the object, the **x-oss-forbid-overwrite** header does not take effect. In this case, the InitiateMultipartUpload operation overwrites the existing object that has the same name as the object that you want to upload.
         * - If you do not specify the **x-oss-forbid-overwrite** header or set the **x-oss-forbid-overwrite** header to **false**, the object that is uploaded by calling the PutObject operation overwrites the existing object that has the same name.
         * - If the value of **x-oss-forbid-overwrite** is set to **true**, existing objects cannot be overwritten by objects that have the same names. If you specify the **x-oss-forbid-overwrite** request header, the queries per second (QPS) performance of OSS is degraded. If you want to use the **x-oss-forbid-overwrite** request header to perform a large number of operations (QPS greater than 1,000), contact technical support
         */
        public var forbidOverwrite: Boolean?
            set(value) {
                value?.let { this.headers["x-oss-forbid-overwrite"] = it.toString() }
            }
            get() = headers["x-oss-forbid-overwrite"].toBoolean()

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
         * The tag of the object. You can configure multiple tags for the object. Example: TagA=A&amp;TagB=B. The key and value of a tag must be URL-encoded. If a tag does not contain an equal sign (=), the value of the tag is considered an empty string.
         */
        public var tagging: String?
            set(value) {
                value?.let { this.headers["x-oss-tagging"] = it }
            }
            get() = headers["x-oss-tagging"]

        /**
         * The server-side encryption method that is used to encrypt each part of the object that you want to upload.
         * Valid values: **AES256**, **KMS**, and **SM4**.
         * You must activate Key Management Service (KMS) before you set this header to KMS. If you specify this header in the request, this header is included in the response. OSS uses the method specified by this header to encrypt each uploaded part. When you download the object, the x-oss-server-side-encryption header is included in the response and the header value is set to the algorithm that is used to encrypt the object.
         */
        public var serverSideEncryption: String?
            set(value) {
                value?.let { this.headers["x-oss-server-side-encryption"] = it }
            }
            get() = headers["x-oss-server-side-encryption"]

        /**
         * The algorithm that is used to encrypt the object that you want to upload. If this header is not specified, the object is encrypted by using AES-256. This header is valid only when **x-oss-server-side-encryption** is set to KMS. Valid value: SM4.
         */
        public var serverSideDataEncryption: String?
            set(value) {
                value?.let { this.headers["x-oss-server-side-data-encryption"] = it }
            }
            get() = headers["x-oss-server-side-data-encryption"]

        /**
         * The ID of the CMK that is managed by KMS. This header is valid only when **x-oss-server-side-encryption** is set to KMS.
         */
        public var serverSideEncryptionKeyId: String?
            set(value) {
                value?.let { this.headers["x-oss-server-side-encryption-key-id"] = it }
            }
            get() = headers["x-oss-server-side-encryption-key-id"]

        /**
         * The caching behavior of the web page when the object is downloaded. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**. Default value: null.
         */
        public var cacheControl: String?
            set(value) {
                value?.let { this.headers["Cache-Control"] = it }
            }
            get() = headers["Cache-Control"]

        /**
         * The name of the object when the object is downloaded. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**. Default value: null.
         */
        public var contentDisposition: String?
            set(value) {
                value?.let { this.headers["Content-Disposition"] = it }
            }
            get() = headers["Content-Disposition"]

        /**
         * The content encoding format of the object when the object is downloaded. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**. Default value: null.
         */
        public var contentEncoding: String?
            set(value) {
                value?.let { this.headers["Content-Encoding"] = it }
            }
            get() = headers["Content-Encoding"]

        /**
         * The expiration time of the request. Unit: milliseconds. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**. Default value: null.
         */
        public var expires: String?
            set(value) {
                value?.let { this.headers["Expires"] = it }
            }
            get() = headers["Expires"]

        /**
         * The method used to encode the object name in the response. Only URL encoding is supported. The object name can contain characters encoded in UTF-8. However, the XML 1.0 standard cannot be used to parse specific control characters, such as characters whose ASCII values range from 0 to 10. You can configure the encoding-type parameter to encode object names that include characters that cannot be parsed by XML 1.0 in the response.brDefault value: null
         */
        public var encodingType: String?
            set(value) {
                value?.let { this.parameters["encoding-type"] = it }
            }
            get() = parameters["encoding-type"]

        public fun build(): InitiateMultipartUploadRequest {
            return InitiateMultipartUploadRequest(this)
        }

        public constructor(from: InitiateMultipartUploadRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.key = from.key
        }
    }
}
