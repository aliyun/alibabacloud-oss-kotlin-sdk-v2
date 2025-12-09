package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.models.internal.ListBucketResult

/**
 * The result for the ListObjects operation.
 */
public class ListObjectsResult(builder: Builder) : ResultModel(builder) {

    private val delegate = builder.innerBody as? ListBucketResult

    /**
     * Indicates whether the returned results are truncated.Valid values: true and false. true: indicates that not all of the results are returned for the request.false indicates that all of the results are returned this time.**
     */
    public val isTruncated: Boolean? = delegate?.isTruncated

    /**
     * The number of keys returned for this request. If Delimiter is specified, the KeyCount value is the sum of the Key and CommonPrefixes values.
     */
    public val keyCount: Int? = delegate?.keyCount

    /**
     * The encoding type of the object name in the response. If the encoding-type parameter is specified in the request, the values of Delimiter, StartAfter, Prefix, NextContinuationToken, and Key are encoded in the response.
     */
    public val encodingType: String? = delegate?.encodingType

    /**
     * The container that stores the metadata of each returned object.
     */
    public val contents: List<ObjectSummary>? = delegate?.contents

    /**
     * The prefix in the names of the returned objects.
     */
    public val prefix: String? = delegate?.prefix

    /**
     * The maximum number of the returned objects in the response.
     */
    public val maxKeys: Int? = delegate?.maxKeys

    /**
     * The delimiter used to group objects by name. Objects whose names contain the same string from the prefix to the next occurrence of the delimiter are grouped as a single result element in the CommonPrefixes parameter.
     */
    public val delimiter: String? = delegate?.delimiter

    /**
     * If the marker parameter is specified in the request, the response contains Marker.
     */
    public val marker: String? = delegate?.marker

    /**
     * The token from which the next list operation starts. The NextMarker value is used as the Marker value to query subsequent results.
     */
    public val nextMarker: String? = delegate?.nextMarker

    /**
     * If the delimiter parameter is specified in the request, the response contains CommonPrefixes. Objects whose names contain the same string from the prefix to the next occurrence of the delimiter are grouped as a single result element in CommonPrefixes.
     */
    public val commonPrefixes: List<CommonPrefix>? = delegate?.commonPrefixes

    /**
     * The name of the bucket.
     */
    public val name: String? = delegate?.name

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ListObjectsResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): ListObjectsResult {
            return ListObjectsResult(this)
        }
    }
}
