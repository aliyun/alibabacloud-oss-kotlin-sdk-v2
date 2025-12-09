package com.aliyun.kotlin.sdk.service.oss2.paginator

import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectVersionsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectVersionsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

public fun OSSClient.listObjectVersionsPaginator(request: ListObjectVersionsRequest, options: PaginatorOptions? = null): Flow<ListObjectVersionsResult> =
    flow {
        var req = when (options?.limit) {
            null -> request
            else -> request.copy {
                maxKeys = options.limit
            }
        }

        while (true) {
            val result = this@listObjectVersionsPaginator.listObjectVersions(req)
            emit(result)
            if (!(result.isTruncated ?: false)) {
                break
            }
            req = req.copy {
                this.keyMarker = result.nextKeyMarker
                this.versionIdMarker = result.nextVersionIdMarker
            }
        }
    }
