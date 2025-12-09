package com.aliyun.kotlin.sdk.service.oss2.signer

public class RemoteSignerV1(
    private val delegate: SignatureDelegate
) : SignerV1() {

    override suspend fun sign(signingContext: SigningContext) {
        val request = requireNotNull(signingContext.request)

        if (signingContext.isAuthMethodQuery) {
            preAuthQuery(signingContext)
        } else {
            preAuthHeader(signingContext)
        }
        val signInfo: Map<String, String> = mapOf(
            "version" to "v1",

            // resource
            "method" to request.method,
            "bucket" to (signingContext.bucket ?: ""),
            "key" to (signingContext.key ?: ""),

            // signing context
            "stringToSign" to requireNotNull(signingContext.stringToSign),
            // "date" to requireNotNull(signingContext.scopeToSign?.substring(0, 8)),
            "accessKeyId" to requireNotNull(signingContext.credentials?.accessKeyId)
        )

        val signResult = delegate.signature(signInfo)
        val signature = requireNotNull(signResult["signature"]) { "signature is required" }

        if (signingContext.isAuthMethodQuery) {
            postAuthQuery(signingContext, signature)
        } else {
            postAuthHeader(signingContext, signature)
        }
    }
}
