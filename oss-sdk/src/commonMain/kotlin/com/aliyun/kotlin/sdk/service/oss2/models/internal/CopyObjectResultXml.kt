package com.aliyun.kotlin.sdk.service.oss2.models.internal

internal class CopyObjectResultXml {

    /**
     * The ETag value of the destination object.
     */
    var eTag: String? = null

    /**
     * The time when the destination object was last modified.
     */
    var lastModified: String? = null

    companion object {
        operator fun invoke(builder: CopyObjectResultXml.() -> Unit): CopyObjectResultXml =
            CopyObjectResultXml().apply(builder)
    }
}
