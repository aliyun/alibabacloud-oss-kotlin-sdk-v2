package com.aliyun.kotlin.sdk.service.oss2.agentic.paginator

import com.aliyun.kotlin.sdk.service.oss2.agentic.AgenticBucketClient
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListAgenticBucketsRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListAgenticBucketsResult
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListBucketSpacesRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListBucketSpacesResult
import com.aliyun.kotlin.sdk.service.oss2.paginator.PaginatorOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * A paginator for ListAgenticBuckets.
 *
 * SDK will internally handle making service calls for you.
 * @param request A [ListAgenticBucketsRequest] for the ListAgenticBuckets operation.
 * @param options The paginator options.
 * @return A [kotlinx.coroutines.flow.Flow] that can collect [ListAgenticBucketsResult].
 */
public fun AgenticBucketClient.listAgenticBucketsPaginator(
    request: ListAgenticBucketsRequest,
    options: PaginatorOptions? = null,
): Flow<ListAgenticBucketsResult> =
    flow {
        var req = when (options?.limit) {
            null -> request
            else -> request.copy { maxKeys = options.limit }
        }

        while (true) {
            val result = listAgenticBuckets(req)
            emit(result)
            if (!(result.isTruncated ?: false)) {
                break
            }
            req = req.copy { continuationToken = result.nextContinuationToken }
        }
    }

/**
 * A paginator for ListBucketSpaces.
 *
 * SDK will internally handle making service calls for you.
 * @param request A [ListBucketSpacesRequest] for the ListBucketSpaces operation.
 * @param options The paginator options.
 * @return A [kotlinx.coroutines.flow.Flow] that can collect [ListBucketSpacesResult].
 */
public fun AgenticBucketClient.listBucketSpacesPaginator(
    request: ListBucketSpacesRequest,
    options: PaginatorOptions? = null,
): Flow<ListBucketSpacesResult> =
    flow {
        var req = when (options?.limit) {
            null -> request
            else -> request.copy { maxKeys = options.limit }
        }

        while (true) {
            val result = listBucketSpaces(req)
            emit(result)
            if (!(result.isTruncated ?: false)) {
                break
            }
            req = req.copy { continuationToken = result.nextContinuationToken }
        }
    }
