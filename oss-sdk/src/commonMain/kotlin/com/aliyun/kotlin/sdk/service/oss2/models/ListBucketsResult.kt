package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.models.internal.ListAllMyBucketsResultXml

/**
 * The result for the ListBuckets operation.
 */
public class ListBucketsResult(builder: Builder) : ResultModel(builder) {

    private val delegate = builder.innerBody as? ListAllMyBucketsResultXml

    /**
     * The prefix contained in the names of returned buckets.
     */
    public val prefix: String?
        get() = delegate?.prefix

    /**
     * The name of the bucket from which the buckets are returned.
     */
    public val marker: String?
        get() = delegate?.marker

    /**
     * The maximum number of buckets that can be returned.
     */
    public val maxKeys: Long?
        get() = delegate?.maxKeys

    /**
     * Indicates whether all results are returned.
     * Valid values:
     * - true: All results are not returned in the response.
     * - false: All results are returned in the response.
     */
    public val isTruncated: Boolean?
        get() = delegate?.isTruncated

    /**
     * The marker for the next ListBuckets (GetService) request. You can use the value of this parameter as the value of marker in the next ListBuckets (GetService) request to retrieve the unreturned results.
     */
    public val nextMarker: String?
        get() = delegate?.nextMarker

    /**
     * The container that stores the information about multiple buckets.
     */
    public val buckets: List<BucketSummary>?
        get() = delegate?.buckets

    /**
     * The container that stores the information about the bucket owner.
     */
    public val owner: Owner?
        get() = delegate?.owner

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ListBucketsResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): ListBucketsResult {
            return ListBucketsResult(this)
        }
    }
}
