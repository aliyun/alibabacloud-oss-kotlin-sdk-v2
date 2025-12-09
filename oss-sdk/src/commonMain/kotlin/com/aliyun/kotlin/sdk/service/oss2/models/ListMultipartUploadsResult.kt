package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.models.internal.ListMultipartUploads

/**
 * The result for the ListMultipartUploads operation.
 */
public class ListMultipartUploadsResult(builder: Builder) : ResultModel(builder) {

    private val delegate = builder.innerBody as? ListMultipartUploads

    /**
     * The name of the object that corresponds to the multipart upload task after which the list begins.
     */
    public val keyMarker: String? = delegate?.keyMarker

    /**
     * The upload ID of the multipart upload task after which the list begins.
     */
    public val uploadIdMarker: String? = delegate?.uploadIdMarker

    /**
     * The object name marker in the response for the next request to return the remaining results.
     */
    public val nextKeyMarker: String? = delegate?.nextKeyMarker

    /**
     * The maximum number of multipart upload tasks returned by OSS.
     */
    public val maxUploads: Long? = delegate?.maxUploads

    /**
     * Indicates whether the list of multipart upload tasks returned in the response is truncated. Default value: false.
     * Valid values:
     * - true: Only part of the results are returned this time.
     * - false: All results are returned.
     */
    public val isTruncated: Boolean? = delegate?.isTruncated

    /**
     * The name of the bucket.
     */
    public val bucket: String? = delegate?.bucket

    /**
     * The method used to encode the object name in the response. If encoding-type is specified in the request, values of those elements including Delimiter, KeyMarker, Prefix, NextKeyMarker, and Key are encoded in the returned result.
     */
    public val encodingType: String? = delegate?.encodingType

    /**
     * The NextUploadMarker value that is used for the UploadMarker value in the next request if the response does not contain all required results.
     */
    public val nextUploadIdMarker: String? = delegate?.nextUploadIdMarker

    /**
     * The prefix that the returned object names must contain. If you specify a prefix in the request, the specified prefix is included in the response.
     */
    public val prefix: String? = delegate?.prefix

    /**
     * The character used to group objects by name. If you specify the Delimiter parameter in the request, the response contains the CommonPrefixes element. Objects whose names contain the same string from the prefix to the next occurrence of the delimiter are grouped as a single result element in
     */
    public val delimiter: String? = delegate?.delimiter

    /**
     * The ID list of the multipart upload tasks.
     */
    public val uploads: List<Upload>? = delegate?.uploads

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ListMultipartUploadsResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): ListMultipartUploadsResult {
            return ListMultipartUploadsResult(this)
        }
    }
}
