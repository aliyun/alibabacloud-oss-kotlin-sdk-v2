package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.types.Abortable
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream

/**
 * The result for the GetObject operation.
 */
public class GetObjectResult(builder: Builder) : ResultModel(builder), AutoCloseable {

    /**
     * A standard MIME type describing the format of the object data.
     */
    public val contentType: String?
        get() = headers["Content-Type"]

    /**
     * The storage class of the object.
     */
    public val storageClass: String?
        get() = headers["x-oss-storage-class"]

    /**
     * The requester. This header is included in the response if the pay-by-requester mode
     */
    public val requestCharged: String?
        get() = headers["x-oss-request-charged"]

    /**
     * The position for the next append operation.
     * If the type of the object is Appendable, this header is included in the response.
     */
    public val nextAppendPosition: Long?
        get() = headers["x-oss-next-append-position"]?.toLong()

    /**
     * The 64-bit CRC value of the object.
     * This value is calculated based on the ECMA-182 standard.
     */
    public val hashCrc64ecma: String?
        get() = headers["x-oss-hash-crc64ecma"]

    /**
     * The status of the object when you restore an object.
     * If the storage class of the bucket is Archive and a RestoreObject request is submitted,
     */
    public val restore: String?
        get() = headers["x-oss-restore"]

    /**
     * The result of an event notification that is triggered for the object.
     */
    public val processStatus: String?
        get() = headers["x-oss-process-status"]

    /**
     * The time when the returned objects were last modified.
     */
    public val lastModified: String?
        get() = headers["Last-Modified"]

    /**
     * The number of tags added to the object.
     */
    public val taggingCount: Long?
        get() = headers["x‑oss‑tagging‑count"]?.toLong()

    /**
     * The ID of the customer master key (CMK) that is managed by Key Management Service (KMS).
     */
    public val serverSideEncryptionKeyId: String?
        get() = headers["x-oss-server-side-encryption-key-id"]

    /**
     * A standard MIME type describing the format of the object data.
     */
    public val objectType: String?
        get() = headers["x-oss-object-type"]

    /**
     * The entity tag (ETag).
     * An ETag is created when an object is created to identify the content of the object.
     */
    public val eTag: String?
        get() = headers["ETag"]

    /**
     * A map of metadata to store with the object.
     * The number of metadata entries returned in the headers that are prefixed with x-oss-meta-
     */
    public val metadata: Map<String, String>?
        get() = headers.filter { it.key.startsWith("x-oss-meta-") }.mapKeys { it.key.substring(11) }

    /**
     * If the requested object is encrypted by
     * using a server-side encryption algorithm based on entropy encoding, OSS automatically decrypts
     * the object and returns the decrypted object after OSS receives the GetObject request.
     * The x-oss-server-side-encryption header is included in the response to indicate the encryption algorithm
     * used to encrypt the object on the server.
     */
    public val serverSideEncryption: String?
        get() = headers["x-oss-server-side-encryption"]

    /**
     * The lifecycle information about the object.
     * If lifecycle rules are configured for the object, this header is included in the response.
     * This header contains the following parameters: expiry-date that indicates the expiration time of the object,
     * and rule-id that indicates the ID of the matched lifecycle rule.
     */
    public val expiration: String?
        get() = headers["x-oss-expiration"]

    /**
     * Content-Md5 for the uploaded object.
     */
    public val contentMd5: String?
        get() = headers["Content-Md5"]

    /**
     * Size of the body in bytes.
     */
    public val contentLength: Long?
        get() = headers["Content-Length"]?.toLong()

    /**
     * Object data.
     */
    public val body: ByteStream?
        get() = innerBody as? ByteStream

    /**
     * Close the response forcefully . The remaining data in the server will not
     * be downloaded.
     */
    public fun abort() {
        if (this.innerBody is Abortable) {
            this.innerBody.abort()
        }
    }

    public override fun close() {
        if (this.innerBody is AutoCloseable) {
            this.innerBody.close()
        }
    }

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetObjectResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): GetObjectResult {
            return GetObjectResult(this)
        }
    }
}
