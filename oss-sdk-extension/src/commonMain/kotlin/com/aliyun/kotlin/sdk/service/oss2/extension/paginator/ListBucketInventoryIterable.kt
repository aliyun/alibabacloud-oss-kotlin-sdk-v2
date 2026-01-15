package com.aliyun.kotlin.sdk.service.oss2.extension.paginator

import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.extension.api.listBucketInventory
import com.aliyun.kotlin.sdk.service.oss2.extension.models.ListBucketInventoryRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.ListBucketInventoryResult
import com.aliyun.kotlin.sdk.service.oss2.models.ListBucketsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListBucketsResult
import com.aliyun.kotlin.sdk.service.oss2.paginator.PaginatorOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * A paginator for ListBucketInventory
 *
 * This is a variant of the ListBucketInventory operation.
 * The return type is a custom iterable that can be used to iterate through all the pages.
 * SDK will internally handle making service calls for you.
 * @param request A {@link ListBucketInventory} for ListBucketInventory operation.
 * @param options The paginator options.
 * @return A [kotlinx.coroutines.flow.Flow] that can collect [ListBucketInventoryResult]
 * @throws RuntimeException If an error occurs
 */
public fun OSSClient.listBucketsPaginator(
    request: ListBucketInventoryRequest,
    options: PaginatorOptions? = null
): Flow<ListBucketInventoryResult> =
    flow {
        var req = request.copy()

        while (true) {
            val result = this@listBucketsPaginator.listBucketInventory(request)
            emit(result)
            if (!(result.isTruncated ?: false)) {
                break
            }
            req = req.copy {
                this.continuationToken = result.nextContinuationToken
            }
        }
    }
