package com.aliyun.kotlin.sdk.service.oss2.signer

/**
 * A no-operation signer implementation that performs no actual signing.
 */
public class NopSigner : Signer {
    /**
     * Does nothing. Used in scenarios where signing is not required.
     *
     * @param signingContext The signing context
     */
    override suspend fun sign(signingContext: SigningContext) {
    }
}
