package com.aliyun.kotlin.sdk.service.oss2.operations

import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.OperationMetadataKey.Companion.SUB_RESOURCE
import com.aliyun.kotlin.sdk.service.oss2.OperationOptions
import com.aliyun.kotlin.sdk.service.oss2.internal.ClientImpl
import com.aliyun.kotlin.sdk.service.oss2.models.*
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.addContentMd5
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.serializeInput
import com.aliyun.kotlin.sdk.service.oss2.transform.fromXmlRegionInfoList
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils

internal object Region {

    internal suspend fun describeRegions(impl: ClientImpl, request: DescribeRegionsRequest, options: OperationOptions?): DescribeRegionsResult {
        val input = OperationInput {
            opName = "DescribeRegions"
            method = "GET"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("regions", "")
            }
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("regions")

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)
        val body = output.body?.toByteArray()

        return DescribeRegionsResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
            innerBody = fromXmlRegionInfoList(body)
        }
    }
}
