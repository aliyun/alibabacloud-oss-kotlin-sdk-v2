package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores the configuration fields in inventory lists.
 */
@Serializable
@SerialName("OptionalFields")
public data class OptionalFields(
    /**
     * The configuration fields that are included in inventory lists. Available configuration fields:
     * *   Size: the size of the object.
     * *   LastModifiedDate: the time when the object was last modified.
     * *   ETag: the ETag of the object. It is used to identify the content of the object.
     * *   StorageClass: the storage class of the object.
     * *   IsMultipartUploaded: specifies whether the object is uploaded by using multipart upload.
     * *   EncryptionStatus: the encryption status of the object.
     */
    @XmlElement("Field") public var fields: List<String>? = null
) {
    public companion object {
        public operator fun invoke(builder: OptionalFields.() -> Unit): OptionalFields =
            OptionalFields().apply(builder)
    }
}
