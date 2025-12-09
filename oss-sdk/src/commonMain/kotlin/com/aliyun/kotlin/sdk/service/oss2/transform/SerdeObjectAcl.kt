package com.aliyun.kotlin.sdk.service.oss2.transform

import com.aliyun.kotlin.sdk.service.oss2.models.*
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.deserializeXmlBody

internal fun fromXmlObjectAccessControlPolicy(data: ByteArray?): AccessControlPolicy {
    val root = deserializeXmlBody(data, "AccessControlPolicy")
    val accessControlPolicy = AccessControlPolicy.Builder()
    root.children.forEach { it0 ->
        when (it0.key) {
            "AccessControlList" -> {
                val accessControlList = AccessControlList.Builder()
                it0.value.first().children.forEach { it1 ->
                    when (it1.key) {
                        "Grant" -> {
                            accessControlList.grant = it1.value.first().text
                        }
                    }
                }
                accessControlPolicy.accessControlList = accessControlList.build()
            }
            "Owner" -> {
                val owner = Owner.Builder()
                it0.value.first().children.forEach { it1 ->
                    when (it1.key) {
                        "ID" -> owner.id = it1.value.first().text
                        "DisplayName" -> owner.displayName = it1.value.first().text
                    }
                }
                accessControlPolicy.owner = owner.build()
            }
        }
    }

    return accessControlPolicy.build()
}
