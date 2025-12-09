package com.aliyun.kotlin.sdk.service.oss2.models.internal

import com.aliyun.kotlin.sdk.service.oss2.models.Upload

internal class ListMultipartUploads {
    /**
     * The name of the object that corresponds to the multipart upload task after which the list begins.
     */
    var keyMarker: String? = null

    /**
     * The upload ID of the multipart upload task after which the list begins.
     */
    var uploadIdMarker: String? = null

    /**
     * The object name marker in the response for the next request to return the remaining results.
     */
    var nextKeyMarker: String? = null

    /**
     * The maximum number of multipart upload tasks returned by OSS.
     */
    var maxUploads: Long? = null

    /**
     * Indicates whether the list of multipart upload tasks returned in the response is truncated. Default value: false. Valid values:- true: Only part of the results are returned this time.- false: All results are returned.
     */
    var isTruncated: Boolean? = null

    /**
     * The name of the bucket.
     */
    var bucket: String? = null

    /**
     * The method used to encode the object name in the response. If encoding-type is specified in the request, values of those elements including Delimiter, KeyMarker, Prefix, NextKeyMarker, and Key are encoded in the returned result.
     */
    var encodingType: String? = null

    /**
     * The NextUploadMarker value that is used for the UploadMarker value in the next request if the response does not contain all required results.
     */
    var nextUploadIdMarker: String? = null

    /**
     * The prefix that the returned object names must contain. If you specify a prefix in the request, the specified prefix is included in the response.
     */
    var prefix: String? = null

    /**
     * The character used to group objects by name. If you specify the Delimiter parameter in the request, the response contains the CommonPrefixes element. Objects whose names contain the same string from the prefix to the next occurrence of the delimiter are grouped as a single result element in
     */
    var delimiter: String? = null

    /**
     * The ID list of the multipart upload tasks.
     */
    var uploads: List<Upload>? = null

    companion object {
        operator fun invoke(builder: ListMultipartUploads.() -> Unit): ListMultipartUploads =
            ListMultipartUploads().apply(builder)
    }
}
