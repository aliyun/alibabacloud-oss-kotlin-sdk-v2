package com.aliyun.kotlin.sdk.service.oss2.credentials

import com.aliyun.kotlin.sdk.service.oss2.internal.getPlatformEnv

/**
 * Environment Variable Credentials Provider Class
 */
public class EnvironmentVariableCredentialsProvider : CredentialsProvider {
    /**
     * The credential object parsed from environment variables
     */
    private val credentials: Credentials

    /**
     * Constructor that loads credentials from system environment variables
     *
     * @throws IllegalArgumentException If any required environment variables (OSS_ACCESS_KEY_ID or OSS_ACCESS_KEY_SECRET) are missing
     */
    init {
        var accessKeyId = getPlatformEnv("OSS_ACCESS_KEY_ID") ?: ""
        var accessKeySecret = getPlatformEnv("OSS_ACCESS_KEY_SECRET") ?: ""
        val securityToken = getPlatformEnv("OSS_SESSION_TOKEN")

        this.credentials = Credentials(accessKeyId, accessKeySecret, securityToken)
    }

    /**
     * Retrieves the credentials loaded from environment variables
     *
     * @return A read-only reference to the [Credentials] object being held by this provider
     */
    override suspend fun getCredentials(): Credentials {
        return credentials
    }
}
