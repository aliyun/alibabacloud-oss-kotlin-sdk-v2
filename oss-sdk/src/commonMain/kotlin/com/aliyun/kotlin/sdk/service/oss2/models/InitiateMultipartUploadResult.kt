package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.models.internal.InitiateMultipartUploadResultXml

/**
 * The result for the InitiateMultipartUpload operation.
 */
public class InitiateMultipartUploadResult(builder: Builder) : ResultModel(builder) {

    private val delegate = innerBody as? InitiateMultipartUploadResultXml

    /**
     * The name of the bucket to which the object is uploaded by the multipart upload task.
     */
    public val bucket: String? = delegate?.bucket

    /**
     * The name of the object that is uploaded by the multipart upload task.
     */
    public val key: String? = delegate?.key

    /**
     * The upload ID that uniquely identifies the multipart upload task. The upload ID is used to call UploadPart and CompleteMultipartUpload later.
     */
    public val uploadId: String? = delegate?.uploadId

    /**
     * The encoding type of the object name in the response. If the encoding-type parameter is specified in the request, the object name in the response is encoded.
     */
    public val encodingType: String? = delegate?.encodingType

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): InitiateMultipartUploadResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): InitiateMultipartUploadResult {
            return InitiateMultipartUploadResult(this)
        }
    }
}
