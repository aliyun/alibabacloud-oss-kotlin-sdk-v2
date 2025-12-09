package com.aliyun.kotlin.sdk.service.oss2.signer

import com.aliyun.kotlin.sdk.service.oss2.credentials.Credentials
import com.aliyun.kotlin.sdk.service.oss2.transport.RequestMessage
import kotlin.time.Duration

/**
 * Represents the context information required for signing operations, including the request,
 * credentials, and other related parameters.
 */
public class SigningContext {
    /**
     * Product identifier used to generate signing scope.
     */
    public var product: String? = null

    /**
     * Region identifier used to generate signing scope.
     */
    public var region: String? = null

    /**
     * Bucket name used to construct the request resource path.
     */
    public var bucket: String? = null

    /**
     * Object key used to construct the request resource path.
     */
    public var key: String? = null

    /**
     * The current request object containing HTTP method, URI, and headers.
     */
    public var request: RequestMessage? = null

    /**
     * The credential object used for signing.
     */
    public var credentials: Credentials? = null

    /**
     * Indicates whether query-based authentication should be used.
     */
    public var isAuthMethodQuery: Boolean = false

    /**
     * The generated canonical string to be signed (for debugging or logging).
     */
    public var stringToSign: String? = null

    /**
     * The expiration time of the signature.
     * It is the epoch number of seconds from the epoch instant 1970-01-01T00:00:00Z
     */
    public var expirationInEpoch: Long? = null

    /**
     * The timestamp when the signature was created.
     * It is the epoch number of seconds from the epoch instant 1970-01-01T00:00:00Z
     */
    public var signTimeInEpoch: Long? = null

    /**
     * The difference between server time and local time
     */
    public var clockOffset: Duration? = null

    /**
     * Additional list of HTTP header fields that should participate in signing.
     */
    public var additionalHeaders: List<String>? = null

    /**
     * List of sub-resource parameters such as "acl", "versionId".
     */
    public var subResource: List<String>? = null

    public var scopeToSign: String? = null
}
