package com.aliyun.kotlin.sdk.service.oss2.transform

import com.aliyun.kotlin.sdk.service.oss2.models.BucketSummary
import com.aliyun.kotlin.sdk.service.oss2.models.Owner
import com.aliyun.kotlin.sdk.service.oss2.models.internal.ListAllMyBucketsResultXml
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.deserializeXmlBody

internal fun fromXmlListAllMyBucketsResult(data: ByteArray?): ListAllMyBucketsResultXml {
    val root = deserializeXmlBody(data, "ListAllMyBucketsResult")

    /*
<ListAllMyBucketsResult>
  <Prefix>my</Prefix>
  <Marker>mybucket</Marker>
  <MaxKeys>10</MaxKeys>
  <IsTruncated>true</IsTruncated>
  <NextMarker>mybucket10</NextMarker>
  <Owner>
    <ID>512**</ID>
    <DisplayName>51264</DisplayName>
  </Owner>
  <Buckets>
    <Bucket>
      <CreationDate>2014-02-17T18:12:43.000Z</CreationDate>
      <ExtranetEndpoint>oss-cn-shanghai.aliyuncs.com</ExtranetEndpoint>
      <IntranetEndpoint>oss-cn-shanghai-internal.aliyuncs.com</IntranetEndpoint>
      <Location>oss-cn-shanghai</Location>
      <Name>app-base-oss</Name>
      <Region>cn-shanghai</Region>
      <StorageClass>Standard</StorageClass>
    </Bucket>
    <Bucket>
      <CreationDate>2014-02-25T11:21:04.000Z</CreationDate>
      <ExtranetEndpoint>oss-cn-hangzhou.aliyuncs.com</ExtranetEndpoint>
      <IntranetEndpoint>oss-cn-hangzhou-internal.aliyuncs.com</IntranetEndpoint>
      <Location>oss-cn-hangzhou</Location>
      <Name>mybucket</Name>
      <Region>cn-hangzhou</Region>
      <StorageClass>IA</StorageClass>
    </Bucket>
  </Buckets>
</ListAllMyBucketsResult>
     */

    val result = ListAllMyBucketsResultXml()

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
            "Buckets" -> {
                val it0BucketList = mutableListOf<BucketSummary>()
                it0.value.first().children.forEach { it1 ->
                    when (it1.key) {
                        "Bucket" -> {
                            it1.value.forEach { it2 ->
                                val it2Node = BucketSummary.Builder()
                                it2.children.forEach { it3 ->
                                    when (it3.key) {
                                        "CreationDate" -> it2Node.creationDate = it3.value.first().text
                                        "ExtranetEndpoint" -> it2Node.extranetEndpoint = it3.value.first().text
                                        "IntranetEndpoint" -> it2Node.intranetEndpoint = it3.value.first().text
                                        "Location" -> it2Node.location = it3.value.first().text
                                        "Name" -> it2Node.name = it3.value.first().text
                                        "Region" -> it2Node.region = it3.value.first().text
                                        "Comment" -> it2Node.comment = it3.value.first().text
                                        "StorageClass" -> it2Node.storageClass = it3.value.first().text
                                        "ResourceGroupId" -> it2Node.resourceGroupId = it3.value.first().text
                                    }
                                }
                                it0BucketList.add(it2Node.build())
                            }
                        }
                    }
                }
                result.buckets = it0BucketList
            }
            "Prefix" -> {
                result.prefix = it0.value.first().text
            }
            "Marker" -> {
                result.marker = it0.value.first().text
            }
            "IsTruncated" -> {
                result.isTruncated = it0.value.first().text?.toBoolean()
            }
            "NextMarker" -> {
                result.nextMarker = it0.value.first().text
            }
            "MaxKeys" -> {
                result.maxKeys = it0.value.first().text?.toLong()
            }
        }
    }

    return result
}
