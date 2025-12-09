package com.aliyun.kotlin.sdk.service.oss2.credentials

/**
 * Credential Class for storing authentication information required for access
 */
public class Credentials {
    /**
     * Access Key ID, identifies the requester's identity
     */
    public val accessKeyId: String

    /**
     * Access Key Secret, used to sign requests and ensure request security
     */
    public val accessKeySecret: String

    /**
     * Security Token, used in temporary credential scenarios
     */
    public val securityToken: String?

    /**
     *  The token's expiration time, ISO 8601 format, 2024-04-18T11:33:40Z
     */
    public val expiration: String?

    /**
     * Constructor to create a credential object with access key ID, secret, and security token
     *
     * @param accessKeyId     The access key ID
     * @param accessKeySecret The access key secret
     */
    public constructor(accessKeyId: String, accessKeySecret: String) :
        this(accessKeyId, accessKeySecret, null, null) {
    }

    /**
     * Constructor to create a credential object with access key ID, secret, and security token
     *
     * @param accessKeyId     The access key ID
     * @param accessKeySecret The access key secret
     * @param securityToken   The security token
     */
    public constructor(accessKeyId: String, accessKeySecret: String, securityToken: String?) :
        this(accessKeyId, accessKeySecret, securityToken, null) {
    }

    /**
     * Constructor to create a credential object with access key ID, secret, and security token
     *
     * @param accessKeyId     The access key ID
     * @param accessKeySecret The access key secret
     * @param securityToken   The security token
     * @param expiration   The token's expiration time
     */
    public constructor(accessKeyId: String, accessKeySecret: String, securityToken: String?, expiration: String?) {
        this.accessKeyId = accessKeyId
        this.accessKeySecret = accessKeySecret
        this.securityToken = securityToken
        this.expiration = expiration
    }

    /**
     * Checks if the provided credentials contain valid access key ID and secret
     *
     * @return true if valid keys are present, false otherwise
     */
    public fun hasKeys(): Boolean {
        return !this.accessKeyId.isEmpty() && !this.accessKeySecret.isEmpty()
    }
}
