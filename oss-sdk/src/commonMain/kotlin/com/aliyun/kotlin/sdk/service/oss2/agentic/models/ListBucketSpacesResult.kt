package com.aliyun.kotlin.sdk.service.oss2.agentic.models

import com.aliyun.kotlin.sdk.service.oss2.agentic.models.internal.ListBucketSpacesResultXml
import com.aliyun.kotlin.sdk.service.oss2.models.Owner
import com.aliyun.kotlin.sdk.service.oss2.models.ResultModel

/**
 * The result for the ListBucketSpaces operation.
 */
public class ListBucketSpacesResult(builder: Builder) : ResultModel(builder) {

    private val delegate: ListBucketSpacesResultXml?
        get() = innerBody as? ListBucketSpacesResultXml

    /**
     * The owner of the bucket spaces.
     */
    public val owner: Owner?
        get() = delegate?.owner

    /**
     * The prefix that the returned names must contain.
     */
    public val prefix: String?
        get() = delegate?.prefix

    /**
     * The maximum number of results returned.
     */
    public val maxKeys: Int?
        get() = delegate?.maxKeys

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
     * The list of bucket spaces.
     */
    public val bucketSpaces: List<BucketSpaceSummary>?
        get() = delegate?.bucketSpaces

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ListBucketSpacesResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): ListBucketSpacesResult {
            return ListBucketSpacesResult(this)
        }
    }
}
