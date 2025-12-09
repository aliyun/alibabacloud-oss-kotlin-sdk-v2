package com.aliyun.kotlin.sdk.service.oss2.transform

import com.aliyun.kotlin.sdk.service.oss2.models.*
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.deserializeXmlBody

internal fun fromXmlAccessControlPolicy(data: ByteArray?): AccessControlPolicy {
    /*
    <AccessControlPolicy>
        <Owner>
            <ID>0022012****</ID>
            <DisplayName>user_example</DisplayName>
        </Owner>
        <AccessControlList>
            <Grant>public-read</Grant>
        </AccessControlList>
    </AccessControlPolicy>
     */
    val root = deserializeXmlBody(data, "AccessControlPolicy")

    val result = AccessControlPolicy.Builder()

    root.children.forEach { it0 ->
        when (it0.key) {
            "Owner" -> {
                val it0Node = Owner.Builder()
                it0.value.first().children.forEach { it1 ->
                    when (it1.key) {
                        "ID" -> it0Node.id = it1.value.first().text
                        "DisplayName" -> it0Node.displayName = it1.value.first().text
                    }
                }
                result.owner = it0Node.build()
            }
            "AccessControlList" -> {
                val it0Node = AccessControlList.Builder()
                it0.value.first().children.forEach { it1 ->
                    when (it1.key) {
                        "Grant" -> it0Node.grant = it1.value.first().text
                    }
                }
                result.accessControlList = it0Node.build()
            }
        }
    }

    return result.build()
}
