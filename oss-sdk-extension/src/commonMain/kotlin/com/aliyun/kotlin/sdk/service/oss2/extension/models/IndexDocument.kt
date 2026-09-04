package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores the default homepage.
 */
@Serializable
@SerialName("IndexDocument")
public data class IndexDocument(
    /**
     * The default homepage.
     */
    @XmlElement("Suffix") public var suffix: String? = null,

    /**
     * Specifies whether to redirect the access to the default homepage of the subdirectory when the subdirectory is accessed. Valid values:
     * *   **true**: The access is redirected to the default homepage of the subdirectory.
     * *   **false** (default): The access is redirected to the default homepage of the root directory.
     * For example, the default homepage is set to index.html, and `bucket.oss-cn-hangzhou.aliyuncs.com/subdir/` is the site that you want to access.
     * If **SupportSubDir** is set to false, the access is redirected to `bucket.oss-cn-hangzhou.aliyuncs.com/index.html`.
     * If **SupportSubDir** is set to true, the access is redirected to `bucket.oss-cn-hangzhou.aliyuncs.com/subdir/index.html`.
     */
    @XmlElement("SupportSubDir") public var supportSubDir: Boolean? = null,

    /**
     * The operation to perform when the default homepage is set, the name of the accessed object does not end with a forward slash (/), and the object does not exist.
     * This parameter takes effect only when **SupportSubDir** is set to true. It takes effect after RoutingRule but before ErrorFile.
     * For example, the default homepage is set to index.html, `bucket.oss-cn-hangzhou.aliyuncs.com/abc` is the site that you want to access, and the abc object does not exist. In this case, different operations are performed based on the value of **Type**.
     * *   **0** (default): OSS checks whether the object named abc/index.html, which is in the `Object + Forward slash (/) + Homepage` format, exists. If the object exists, OSS returns HTTP status code 302 and the Location header value that contains URL-encoded `/abc/`. The URL-encoded /abc/ is in the `Forward slash (/) + Object + Forward slash (/)` format. If the object does not exist, OSS returns HTTP status code 404 and continues to check ErrorFile.
     * *   **1**: OSS returns HTTP status code 404 and the NoSuchKey error code and continues to check ErrorFile.*   **2**: OSS checks whether abc/index.html exists. If abc/index.html exists, the content of the object is returned. If abc/index.html does not exist, OSS returns HTTP status code 404 and continues to check ErrorFile.
     */
    @XmlElement("Type") public var type: Long? = null
) {
    public companion object {
        public operator fun invoke(builder: IndexDocument.() -> Unit): IndexDocument =
            IndexDocument().apply(builder)
    }
}
