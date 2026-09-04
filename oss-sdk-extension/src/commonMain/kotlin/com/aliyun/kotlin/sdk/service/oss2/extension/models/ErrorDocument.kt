package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores the default 404 page.
 */
@Serializable
@SerialName("ErrorDocument")
public data class ErrorDocument(
    /**
     * The error page.
     */
    @XmlElement("Key") public var key: String? = null,

    /**
     * The HTTP status code returned with the error page.
     */
    @XmlElement("HttpStatus") public var httpStatus: Long? = null
) {
    public companion object {
        public operator fun invoke(builder: ErrorDocument.() -> Unit): ErrorDocument =
            ErrorDocument().apply(builder)
    }
}
