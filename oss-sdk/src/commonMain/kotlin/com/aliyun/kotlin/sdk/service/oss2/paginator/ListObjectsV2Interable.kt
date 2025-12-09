package com.aliyun.kotlin.sdk.service.oss2.paginator

import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectsV2Request
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectsV2Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

public fun OSSClient.listObjectsV2Paginator(request: ListObjectsV2Request, options: PaginatorOptions? = null): Flow<ListObjectsV2Result> =
    flow {
        var req = when (options?.limit) {
            null -> request
            else -> request.copy {
                maxKeys = options.limit
            }
        }

        while (true) {
            val result = this@listObjectsV2Paginator.listObjectsV2(req)
            emit(result)
            if (!(result.isTruncated ?: false)) {
                break
            }
            req = req.copy {
                this.continuationToken = result.nextContinuationToken
            }
        }
    }
