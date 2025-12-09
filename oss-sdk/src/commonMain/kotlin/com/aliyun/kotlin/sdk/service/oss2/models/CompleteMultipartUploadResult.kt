package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.models.internal.CompleteMultipartUploadResultXml

/**
 * The result for the CompleteMultipartUpload operation.
 */
public class CompleteMultipartUploadResult(builder: Builder) : ResultModel(builder) {

    private val completeMultipartUploadResultXml: CompleteMultipartUploadResultXml? = innerBody as? CompleteMultipartUploadResultXml

    /**
     * The version ID of the object.
     */
    public val versionId: String?
        get() = headers["x-oss-version-id"]

    /**
     * The encoding type of the object name in the response. If this parameter is specified in the request, the object name is encoded in the response.
     */
    public val encodingType: String?
        get() = completeMultipartUploadResultXml?.encodingType

    /**
     * The URL that is used to access the uploaded object.
     */
    public val location: String?
        get() = completeMultipartUploadResultXml?.location

    /**
     * The name of the bucket that contains the object you want to restore.
     */
    public val bucket: String?
        get() = completeMultipartUploadResultXml?.bucket

    /**
     * The name of the uploaded object.
     */
    public val key: String?
        get() = completeMultipartUploadResultXml?.key

    /**
     * The ETag that is generated when an object is created. ETags are used to identify the content of objects.If an object is created by calling the CompleteMultipartUpload operation, the ETag value is not the MD5 hash of the object content but a unique value calculated based on a specific rule. The ETag of an object can be used to check whether the object content is modified. However, we recommend that you use the MD5 hash of an object rather than the ETag value of the object to verify data integrity.
     */
    public val eTag: String?
        get() = completeMultipartUploadResultXml?.eTag

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): CompleteMultipartUploadResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): CompleteMultipartUploadResult {
            return CompleteMultipartUploadResult(this)
        }
    }
}
