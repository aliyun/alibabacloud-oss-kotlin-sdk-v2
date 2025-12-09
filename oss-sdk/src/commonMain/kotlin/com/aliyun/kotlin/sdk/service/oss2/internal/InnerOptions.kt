package com.aliyun.kotlin.sdk.service.oss2.internal

import com.aliyun.kotlin.sdk.service.oss2.logging.LogAgent
import com.aliyun.kotlin.sdk.service.oss2.types.AddressStyleType

internal class InnerOptions {
    var userAgent: String = ""

    // endpoint's protocol host, and authority
    var scheme: String = ""
    var host: String = ""
    var authority: String = ""

    var addressStyle: AddressStyleType = AddressStyleType.VirtualHosted

    var logger: LogAgent? = null
}
