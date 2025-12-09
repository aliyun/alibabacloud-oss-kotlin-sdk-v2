package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.models.internal.ListBucketV2Result

/**
 * The result for the ListObjectsV2 operation.
 */
public class ListObjectsV2Result(builder: Builder) : ResultModel(builder) {

    private val delegate = builder.innerBody as? ListBucketV2Result

    /**
     * Indicates whether the returned results are truncated.
     * Valid values: true and false.
     * -true: indicates that not all of the results are returned for the request.
     * -false: indicates that all of the results are returned this time.**
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
     * If the continuation-token parameter is specified in the request, the response contains ContinuationToken.
     */
    public val continuationToken: String? = delegate?.continuationToken

    /**
     * The token from which the next list operation starts. The NextContinuationToken value is used as the ContinuationToken value to query subsequent results.
     */
    public val nextContinuationToken: String? = delegate?.nextContinuationToken

    /**
     * If the delimiter parameter is specified in the request, the response contains CommonPrefixes. Objects whose names contain the same string from the prefix to the next occurrence of the delimiter are grouped as a single result element in CommonPrefixes.
     */
    public val commonPrefixes: List<CommonPrefix>? = delegate?.commonPrefixes

    /**
     * The name of the bucket.
     */
    public val name: String? = delegate?.name

    /**
     * If the start-after parameter is specified in the request, the response contains StartAfter.
     */
    public val startAfter: String? = delegate?.startAfter

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ListObjectsV2Result =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): ListObjectsV2Result {
            return ListObjectsV2Result(this)
        }
    }
}
