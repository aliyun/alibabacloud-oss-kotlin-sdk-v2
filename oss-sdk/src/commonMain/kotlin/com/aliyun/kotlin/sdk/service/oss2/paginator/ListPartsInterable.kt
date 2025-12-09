package com.aliyun.kotlin.sdk.service.oss2.paginator

import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.models.ListPartsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListPartsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

public fun OSSClient.listPartsPaginator(request: ListPartsRequest, options: PaginatorOptions? = null): Flow<ListPartsResult> =
    flow {
        var req = when (options?.limit) {
            null -> request
            else -> request.copy {
                maxParts = options.limit
            }
        }

        while (true) {
            val result = this@listPartsPaginator.listParts(req)
            emit(result)
            if (!(result.isTruncated ?: false)) {
                break
            }
            req = req.copy {
                this.partNumberMarker = result.nextPartNumberMarker
            }
        }
    }
