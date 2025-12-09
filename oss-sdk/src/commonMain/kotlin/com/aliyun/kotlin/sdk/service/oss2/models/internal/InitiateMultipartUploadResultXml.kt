package com.aliyun.kotlin.sdk.service.oss2.models.internal

internal class InitiateMultipartUploadResultXml {

    /**
     * The name of the bucket to which the object is uploaded by the multipart upload task.
     */
    var bucket: String? = null

    /**
     * The name of the object that is uploaded by the multipart upload task.
     */
    var key: String? = null

    /**
     * The upload ID that uniquely identifies the multipart upload task. The upload ID is used to call UploadPart and CompleteMultipartUpload later.
     */
    var uploadId: String? = null

    /**
     * The encoding type of the object name in the response. If the encoding-type parameter is specified in the request, the object name in the response is encoded.
     */
    var encodingType: String? = null

    companion object {
        operator fun invoke(builder: InitiateMultipartUploadResultXml.() -> Unit): InitiateMultipartUploadResultXml =
            InitiateMultipartUploadResultXml().apply(builder)
    }
}
