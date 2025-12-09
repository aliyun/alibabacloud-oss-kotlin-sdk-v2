package com.aliyun.kotlin.sdk.service.oss2.paginator

import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.models.ListMultipartUploadsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListMultipartUploadsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

public fun OSSClient.listMultipartUploadsPaginator(request: ListMultipartUploadsRequest, options: PaginatorOptions? = null): Flow<ListMultipartUploadsResult> =
    flow {
        var req = when (options?.limit) {
            null -> request
            else -> request.copy {
                maxUploads = options.limit
            }
        }

        while (true) {
            val result = this@listMultipartUploadsPaginator.listMultipartUploads(req)
            emit(result)
            if (!(result.isTruncated ?: false)) {
                break
            }
            req = req.copy {
                this.uploadIdMarker = result.nextUploadIdMarker
                this.keyMarker = result.nextKeyMarker
            }
        }
    }
