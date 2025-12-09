package com.aliyun.kotlin.sdk.service.oss2.signer

public interface SignatureDelegate {
    public suspend fun signature(info: Map<String, String>): Map<String, String>
}
