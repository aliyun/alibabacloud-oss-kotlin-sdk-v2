package com.aliyun.kotlin.sdk.service.oss2.credentials

/**
 * Anonymous Credentials Provider Class
 */
public class AnonymousCredentialsProvider : CredentialsProvider {
    /**
     * Anonymous credential object with empty access key ID and secret
     */
    private val cred = Credentials("", "", null)

    /**
     * Get the credential information
     *
     *
     * This method is used to return the Credentials object currently held by the instance.
     * It allows other authentication or authorization components to use these credentials for identity verification or permission checks.
     *
     * @return A read-only reference to the [Credentials] object being held by this provider
     */
    override suspend fun getCredentials(): Credentials {
        return cred
    }
}
