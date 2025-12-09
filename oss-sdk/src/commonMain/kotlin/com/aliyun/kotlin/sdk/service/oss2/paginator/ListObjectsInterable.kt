package com.aliyun.kotlin.sdk.service.oss2.paginator

import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

public fun OSSClient.listObjectsPaginator(request: ListObjectsRequest, options: PaginatorOptions? = null): Flow<ListObjectsResult> =
    flow {
        var req = when (options?.limit) {
            null -> request
            else -> request.copy {
                maxKeys = options.limit
            }
        }

        while (true) {
            val result = this@listObjectsPaginator.listObjects(req)
            emit(result)
            if (!(result.isTruncated ?: false)) {
                break
            }
            req = req.copy {
                this.marker = result.nextMarker
            }
        }
    }
