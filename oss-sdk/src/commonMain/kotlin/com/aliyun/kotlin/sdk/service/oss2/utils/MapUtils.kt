package com.aliyun.kotlin.sdk.service.oss2.utils

public object MapUtils {

    /**
     A map with case-insensitive String keys, the value can't be null
     */
    public fun headersMap(): MutableMap<String, String> {
        return CaseInsensitiveMap()
    }

    /**
     A map with case-sensitive String keys, the value can be null
     */
    public fun parametersMap(): MutableMap<String, String?> {
        return mutableMapOf()
    }
}
