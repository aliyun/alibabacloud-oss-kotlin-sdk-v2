package com.aliyun.kotlin.sdk.service.oss2.paginator

import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.models.ListBucketsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListBucketsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * A paginator for ListBuckets
 *
 * This is a variant of the ListBuckets operation.
 * The return type is a custom iterable that can be used to iterate through all the pages.
 * SDK will internally handle making service calls for you.
 * @param request A {@link ListBucketsRequest} for ListBuckets operation.
 * @param options The paginator options.
 * @return A [kotlinx.coroutines.flow.Flow] that can collect [ListBucketsResult]
 * @throws RuntimeException If an error occurs
 */
public fun OSSClient.listBucketsPaginator(
    request: ListBucketsRequest,
    options: PaginatorOptions? = null
): Flow<ListBucketsResult> =
    flow {
        var req = when (options?.limit) {
            null -> request
            else -> request.copy {
                maxKeys = options.limit
            }
        }

        while (true) {
            val result = this@listBucketsPaginator.listBuckets(req)
            emit(result)
            if (!(result.isTruncated ?: false)) {
                break
            }
            req = req.copy {
                this.marker = result.nextMarker
            }
        }
    }
