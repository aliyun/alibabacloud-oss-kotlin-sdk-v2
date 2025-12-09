package com.aliyun.kotlin.sdk.service.oss2.transform

import com.aliyun.kotlin.sdk.service.oss2.models.*
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.deserializeXmlBody

internal fun fromXmlRegionInfoList(data: ByteArray?): RegionInfoList {
    val root = deserializeXmlBody(data, "RegionInfoList")
    val result = RegionInfoList.Builder()
    root.children.forEach { it0 ->
        when (it0.key) {
            "RegionInfo" -> {
                val regionInfos = mutableListOf<RegionInfo>()
                it0.value.forEach { it1 ->
                    val regionInfo = RegionInfo.Builder()
                    it1.children.forEach { it2 ->
                        when (it2.key) {
                            "Region" -> {
                                regionInfo.region = it2.value.first().text
                            }
                            "InternetEndpoint" -> {
                                regionInfo.internetEndpoint = it2.value.first().text
                            }
                            "InternalEndpoint" -> {
                                regionInfo.internalEndpoint = it2.value.first().text
                            }
                            "AccelerateEndpoint" -> {
                                regionInfo.accelerateEndpoint = it2.value.first().text
                            }
                        }
                    }
                    regionInfos.add(regionInfo.build())
                }
                result.regionInfos = regionInfos
            }
        }
    }

    return result.build()
}
