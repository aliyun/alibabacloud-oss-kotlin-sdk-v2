package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores the prefix used to filter objects. Only objects whose names contain the specified prefix are included in the inventory.
 */
@Serializable
@SerialName("Filter")
public data class InventoryFilter(
    /**
     * The end of the time range during which the object was last modified. Unit: seconds.Valid values: [1262275200, 253402271999]
     */
    @XmlElement("LastModifyEndTimeStamp") public var lastModifyEndTimeStamp: Long? = null,

    /**
     * The minimum size of the specified object.Unit: B.Valid values: [0 B, 48.8 TB]
     */
    @XmlElement("LowerSizeBound") public var lowerSizeBound: Long? = null,

    /**
     * The maximum size of the specified object.Unit: B.Valid values: [0B, 48.8TB]
     */
    @XmlElement("UpperSizeBound") public var upperSizeBound: Long? = null,

    /**
     * The storage class of the object. You can specify multiple storage classes.Valid values:
     * - Standard
     * - IA
     * - Archive
     * - ColdArchive
     * - All
     */
    @XmlElement("StorageClass") public var storageClass: String? = null,

    /**
     * The prefix that is specified in the inventory.
     */
    @XmlElement("Prefix") public var prefix: String? = null,

    /**
     * The beginning of the time range during which the object was last modified. Unit: seconds.Valid values: [1262275200, 253402271999]
     */
    @XmlElement("LastModifyBeginTimeStamp") public var lastModifyBeginTimeStamp: Long? = null
) {
    public companion object {
        public operator fun invoke(builder: InventoryFilter.() -> Unit): InventoryFilter =
            InventoryFilter().apply(builder)
    }
}
