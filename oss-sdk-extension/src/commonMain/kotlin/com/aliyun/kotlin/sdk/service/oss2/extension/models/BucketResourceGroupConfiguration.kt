package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The configurations of the resource group to which the bucket belongs.
 */
@Serializable
@SerialName("BucketResourceGroupConfiguration")
@XmlRoot
public data class BucketResourceGroupConfiguration(
    /**
     * The ID of the resource group to which the bucket belongs.
     */
    @XmlElement("ResourceGroupId") public var resourceGroupId: String? = null
) {
    public companion object {
        public operator fun invoke(builder: BucketResourceGroupConfiguration.() -> Unit): BucketResourceGroupConfiguration =
            BucketResourceGroupConfiguration().apply(builder)
    }
}
