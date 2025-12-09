package com.aliyun.kotlin.sdk.service.oss2.credentials

/**
 * Credentials Provider Interface
 */
public interface CredentialsProvider {
    /**
     * Gets the current credential object
     *
     * @return An instance
     * representing the current valid authentication information
     */
    public suspend fun getCredentials(): Credentials
}
