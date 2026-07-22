package com.aliyun.kotlin.sdk.service.oss2.agentic.models.internal

import com.aliyun.kotlin.sdk.service.oss2.agentic.models.BucketSpaceSummary
import com.aliyun.kotlin.sdk.service.oss2.models.Owner

/**
 * The parsed XML body of the ListBucketSpaces result.
 */
internal class ListBucketSpacesResultXml {
    var owner: Owner? = null
    var prefix: String? = null
    var maxKeys: Int? = null
    var continuationToken: String? = null
    var nextContinuationToken: String? = null
    var startAfter: String? = null
    var isTruncated: Boolean? = null
    var bucketSpaces: List<BucketSpaceSummary>? = null
}
