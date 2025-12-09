package com.aliyun.kotlin.sdk.service.oss2

import com.aliyun.kotlin.sdk.service.oss2.credentials.CredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.logging.LogAgent
import com.aliyun.kotlin.sdk.service.oss2.retry.Retryer
import com.aliyun.kotlin.sdk.service.oss2.signer.Signer
import com.aliyun.kotlin.sdk.service.oss2.transport.HttpTransport
import kotlin.time.Duration

public class ClientConfiguration(
    /**
     * The region in which the bucket is located.
     */
    public var region: String? = null,

    /**
     * The domain names that other services can use to access OSS.
     */
    public var endpoint: String? = null,

    /**
     * Custom signer
     */
    public var signer: Signer? = null,

    /**
     * Max retry count, default value is 3
     */
    public var retryMaxAttempts: Int? = null,

    /**
     * Retry policy
     */
    public var retryer: Retryer? = null,

    /**
     * The credentials provider to use when signing requests.
     */
    public var credentialsProvider: CredentialsProvider? = null,

    /**
     * Authentication with OSS Signature Version, Defaults is "v4"
     */
    public var signatureVersion: String? = null,

    /**
     * Disable Https protocol on request. Default value is false
     */
    public var disableSsl: Boolean? = null,

    /**
     * Dual-stack endpoints are provided in some regions.
     * This allows an IPv4 client and an IPv6 client to access a bucket by using the same endpoint.
     * Set this to `true` to use a dual-stack endpoint for the requests.
     */
    public var useDualStackEndpoint: Boolean? = null,

    /**
     * You can use an internal endpoint to communicate between Alibaba Cloud services located  within the same region over the internal network.
     * You are not charged for the traffic generated over the internal network.
     * Set this to `true` to use a accelerate endpoint for the requests.
     */
    public var useInternalEndpoint: Boolean? = null,

    /**
     * OSS provides the transfer acceleration feature to accelerate date transfers of data uploads and downloads across countries and regions.
     * Set this to `true` to use a accelerate endpoint for the requests.
     */
    public var useAccelerateEndpoint: Boolean? = null,

    /**
     * Request mode using cname. Default value is false
     */
    public var useCName: Boolean? = null,

    /**
     * Use the path style request mode, where the bucket name is placed in the path. Default value is false
     */
    public var usePathStyle: Boolean? = null,

    /**
     * HTTP transmitter, responsible for sending HTTP requests
     */
    public var httpTransport: HttpTransport? = null,

    /**
     * Additional signable headers.
     */
    public var additionalHeaders: List<String>? = null,

    /**
     * Sets the user specific identifier appended to the User-Agent header.
     */
    public var userAgent: String? = null,

    /**
     * The connection timeout in seconds
     */
    public var connectTimeout: Duration? = null,

    /**
     * The read and write timeout in seconds
     */
    public var readWriteTimeout: Duration? = null,

    /**
     * Skip certificate check. Default value is false
     */
    public var insecureSkipVerify: Boolean? = null,

    /**
     * Enable redirection. Default value is false
     */
    public var enabledRedirect: Boolean? = null,

    /**
     * Proxy host.
     */
    public var proxyHost: String? = null,

    /**
     * Check data integrity of uploads via the crc64 by default. This feature takes effect for PutObject, AppendObject, UploadPart.
     */
    public var disableUploadCRC64Check: Boolean? = null,

    /**
     * Check data integrity of download via the crc64 by default. This feature only takes effect for GetObjectToFile
     */
    public var disableDownloadCRC64Check: Boolean? = null,

    /**
     * oss log agent
     */
    public var logger: LogAgent? = null,
) {

    public companion object {
        public fun loadDefault(): ClientConfiguration {
            return ClientConfiguration()
        }
    }
}
