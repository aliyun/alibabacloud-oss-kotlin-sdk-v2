package com.aliyun.kotlin.sdk.service.oss2.credentials

/**
 * Static Credentials Provider Class
 */
public class StaticCredentialsProvider : CredentialsProvider {

    private val cred: Credentials

    /**
     * Constructor to create a credential object with access key ID, secret, and security token
     *
     * @param accessKeyId     The access key ID
     * @param accessKeySecret The access key secret
     * @param securityToken   The security token
     */
    public constructor(accessKeyId: String, accessKeySecret: String, securityToken: String? = null) {
        this.cred = Credentials(accessKeyId, accessKeySecret, securityToken)
    }

    override suspend fun getCredentials(): Credentials {
        return cred
    }
}
