package com.aliyun.kotlin.sdk.service.oss2.models.internal

import com.aliyun.kotlin.sdk.service.oss2.models.Part

/**
 * The container that stores the response of the ListParts request.
 */
internal class ListPartResult {
    /**
     * Indicates whether the list of parts returned in the response has been truncated. A value of true indicates that the response does not contain all required results. A value of false indicates that the response contains all required results.Valid values: true and false.
     */
    var isTruncated: Boolean? = null

    /**
     * The list of all parts.
     */
    var parts: List<Part>? = null

    /**
     * The name of the bucket.
     */
    var bucket: String? = null

    /**
     * The name of the object.
     */
    var key: String? = null

    /**
     * The ID of the upload task.
     */
    var uploadId: String? = null

    /**
     * The position from which the list starts. All parts whose part numbers are greater than the value of this parameter are listed.
     */
    var partNumberMarker: Long? = null

    /**
     * The NextPartNumberMarker value that is used for the PartNumberMarker value in a subsequent request when the response does not contain all required results.
     */
    var nextPartNumberMarker: Long? = null

    /**
     * The maximum number of parts in the response.
     */
    var maxParts: Long? = null

    /**
     * The method used to encode the object name in the response. If encoding-type is specified in the request, values of Key are encoded in the returned result.
     */
    var encodingType: String? = null

    companion object {
        operator fun invoke(builder: ListPartResult.() -> Unit): ListPartResult =
            ListPartResult().apply(builder)
    }
}
