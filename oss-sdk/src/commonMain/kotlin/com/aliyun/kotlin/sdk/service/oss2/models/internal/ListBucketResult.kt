package com.aliyun.kotlin.sdk.service.oss2.models.internal

import com.aliyun.kotlin.sdk.service.oss2.models.CommonPrefix
import com.aliyun.kotlin.sdk.service.oss2.models.ObjectSummary

/**
 * The container that stores the metadata of the returned objects.
 */
internal class ListBucketResult {
    /**
     * Indicates whether the returned results are truncated.Valid values: true and false. true: indicates that not all of the results are returned for the request.false indicates that all of the results are returned this time.**
     */
    var isTruncated: Boolean? = null

    /**
     * The number of keys returned for this request. If Delimiter is specified, the KeyCount value is the sum of the Key and CommonPrefixes values.
     */
    var keyCount: Int? = null

    /**
     * The encoding type of the object name in the response. If the encoding-type parameter is specified in the request, the values of Delimiter, StartAfter, Prefix, NextContinuationToken, and Key are encoded in the response.
     */
    var encodingType: String? = null

    /**
     * The container that stores the metadata of each returned object.
     */
    var contents: List<ObjectSummary>? = null

    /**
     * The prefix in the names of the returned objects.
     */
    var prefix: String? = null

    /**
     * The maximum number of the returned objects in the response.
     */
    var maxKeys: Int? = null

    /**
     * The delimiter used to group objects by name. Objects whose names contain the same string from the prefix to the next occurrence of the delimiter are grouped as a single result element in the CommonPrefixes parameter.
     */
    var delimiter: String? = null

    /**
     * If the marker parameter is specified in the request, the response contains Marker.
     */
    var marker: String? = null

    /**
     * The token from which the next list operation starts. The NextMarker value is used as the Marker value to query subsequent results.
     */
    var nextMarker: String? = null

    /**
     * If the delimiter parameter is specified in the request, the response contains CommonPrefixes. Objects whose names contain the same string from the prefix to the next occurrence of the delimiter are grouped as a single result element in CommonPrefixes.
     */
    var commonPrefixes: List<CommonPrefix>? = null

    /**
     * The name of the bucket.
     */
    var name: String? = null

    companion object {
        operator fun invoke(builder: ListBucketResult.() -> Unit): ListBucketResult =
            ListBucketResult().apply(builder)
    }
}
