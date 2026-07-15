package com.aliyun.kotlin.sdk.service.oss2.agentic.models

import com.aliyun.kotlin.sdk.service.oss2.agentic.models.internal.ListAgenticBucketsResultXml
import com.aliyun.kotlin.sdk.service.oss2.models.ResultModel

/**
 * The result for the ListAgenticBuckets operation.
 */
public class ListAgenticBucketsResult(builder: Builder) : ResultModel(builder) {

    private val delegate: ListAgenticBucketsResultXml?
        get() = innerBody as? ListAgenticBucketsResultXml

    /**
     * The region in which the agentic buckets are located.
     */
    public val region: String?
        get() = delegate?.region

    /**
     * The owner of the agentic buckets.
     */
    public val owner: String?
        get() = delegate?.owner

    /**
     * The token from which the list operation continues.
     */
    public val continuationToken: String?
        get() = delegate?.continuationToken

    /**
     * The token used to continue the next list operation.
     */
    public val nextContinuationToken: String?
        get() = delegate?.nextContinuationToken

    /**
     * Indicates whether the returned results are truncated.
     */
    public val isTruncated: Boolean?
        get() = delegate?.isTruncated

    /**
     * The list of agentic buckets.
     */
    public val agenticBuckets: List<AgenticBucketSummary>?
        get() = delegate?.agenticBuckets

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ListAgenticBucketsResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): ListAgenticBucketsResult {
            return ListAgenticBucketsResult(this)
        }
    }
}
