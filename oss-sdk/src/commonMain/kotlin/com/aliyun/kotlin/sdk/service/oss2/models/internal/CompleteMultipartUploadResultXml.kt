package com.aliyun.kotlin.sdk.service.oss2.models.internal

internal class CompleteMultipartUploadResultXml() {

    /**
     * The encoding type of the object name in the response. If this parameter is specified in the request, the object name is encoded in the response.
     */
    var encodingType: String? = null

    /**
     * The URL that is used to access the uploaded object.
     */
    var location: String? = null

    /**
     * The name of the bucket that contains the object you want to restore.
     */
    var bucket: String? = null

    /**
     * The name of the uploaded object.
     */
    var key: String? = null

    /**
     * The ETag that is generated when an object is created. ETags are used to identify the content of objects.If an object is created by calling the CompleteMultipartUpload operation, the ETag value is not the MD5 hash of the object content but a unique value calculated based on a specific rule. The ETag of an object can be used to check whether the object content is modified. However, we recommend that you use the MD5 hash of an object rather than the ETag value of the object to verify data integrity.
     */
    var eTag: String? = null

    companion object {
        operator fun invoke(
            builder: CompleteMultipartUploadResultXml.() -> Unit
        ): CompleteMultipartUploadResultXml = CompleteMultipartUploadResultXml().apply(builder)
    }
}
