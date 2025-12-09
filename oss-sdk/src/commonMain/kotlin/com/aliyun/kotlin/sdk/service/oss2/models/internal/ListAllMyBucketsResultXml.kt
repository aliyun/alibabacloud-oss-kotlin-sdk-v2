package com.aliyun.kotlin.sdk.service.oss2.models.internal

import com.aliyun.kotlin.sdk.service.oss2.models.BucketSummary
import com.aliyun.kotlin.sdk.service.oss2.models.Owner

/**
 * The container that stores the result of ListBuckets(GetService) request.
 */
internal class ListAllMyBucketsResultXml {
    /**
     * The prefix contained in the names of returned buckets.
     */
    var prefix: String? = null

    /**
     * The name of the bucket from which the buckets are returned.
     */
    var marker: String? = null

    /**
     * The maximum number of buckets that can be returned.
     */
    var maxKeys: Long? = null

    /**
     * Indicates whether all results are returned. Valid values:- true: All results are not returned in the response. - false: All results are returned in the response.
     */
    var isTruncated: Boolean? = null

    /**
     * The marker for the next ListBuckets (GetService) request. You can use the value of this parameter as the value of marker in the next ListBuckets (GetService) request to retrieve the unreturned results.
     */
    var nextMarker: String? = null

    /**
     * The container that stores the information about multiple buckets.
     */
    var buckets: List<BucketSummary> = listOf()

    /**
     * The container that stores the information about the bucket owner.
     */
    var owner: Owner? = null

    companion object {
        operator fun invoke(builder: ListAllMyBucketsResultXml.() -> Unit): ListAllMyBucketsResultXml =
            ListAllMyBucketsResultXml().apply(builder)
    }
}
