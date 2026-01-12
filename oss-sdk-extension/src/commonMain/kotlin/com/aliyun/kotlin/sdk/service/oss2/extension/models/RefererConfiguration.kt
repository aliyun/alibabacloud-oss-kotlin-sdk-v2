package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores the hotlink protection configurations.
 */
@Serializable
@SerialName("RefererConfiguration")
@XmlRoot
public data class RefererConfiguration(
    /**
     * Specifies whether to allow a request whose Referer field is empty. Valid values:
     * *   true (default)
     * *   false
     */
    @XmlElement("AllowEmptyReferer") public var allowEmptyReferer: Boolean? = null,

    /**
     * Specifies whether to truncate the query string in the URL when the Referer is matched. Valid values:
     * *   true (default)
     * *   false
     */
    @XmlElement("AllowTruncateQueryString") public var allowTruncateQueryString: Boolean? = null,

    /**
     * Specifies whether to truncate the path and parts that follow the path in the URL when the Referer is matched. Valid values:
     * *   true
     * *   false
     */
    @XmlElement("TruncatePath") public var truncatePath: Boolean? = null,

    /**
     * The container that stores the Referer whitelist.  ****The PutBucketReferer operation overwrites the existing Referer whitelist with the Referer whitelist specified in RefererList.
     * If RefererList is not specified in the request, which specifies that no Referer elements are included, the operation clears the existing Referer whitelist.
     */
    @XmlElement("RefererList") public var refererList: RefererList? = null,

    /**
     * The container that stores the Referer blacklist.
     */
    @XmlElement("RefererBlacklist") public var refererBlacklist: RefererBlacklist? = null
) {
    public companion object {
        public operator fun invoke(builder: RefererConfiguration.() -> Unit): RefererConfiguration =
            RefererConfiguration().apply(builder)
    }
}
