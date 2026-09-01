package com.aliyun.kotlin.sdk.service.oss2.agentic.models.internal

import com.aliyun.kotlin.sdk.service.oss2.agentic.models.AgenticBucketSummary

/**
 * The parsed XML body of the ListAgenticBuckets result.
 */
internal class ListAgenticBucketsResultXml {
    var region: String? = null
    var owner: String? = null
    var continuationToken: String? = null
    var nextContinuationToken: String? = null
    var isTruncated: Boolean? = null
    var agenticBuckets: List<AgenticBucketSummary>? = null
}
