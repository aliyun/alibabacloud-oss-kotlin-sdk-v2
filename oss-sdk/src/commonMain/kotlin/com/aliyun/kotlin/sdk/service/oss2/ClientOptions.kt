package com.aliyun.kotlin.sdk.service.oss2

import com.aliyun.kotlin.sdk.service.oss2.credentials.CredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.retry.Retryer
import com.aliyun.kotlin.sdk.service.oss2.signer.Signer
import com.aliyun.kotlin.sdk.service.oss2.transport.HttpTransport
import com.aliyun.kotlin.sdk.service.oss2.types.AddressStyleType
import com.aliyun.kotlin.sdk.service.oss2.types.AuthMethodType
import com.aliyun.kotlin.sdk.service.oss2.types.FeatureFlagsType

public class ClientOptions(builder: Builder) {

    /**
     * Product name
     */
    public val product: String

    /**
     * The region in which the bucket is located.
     */
    public val region: String

    /**
     * The domain names that other services can use to access OSS.
     */
    public val endpoint: String

    /**
     * Custom signer
     */
    public val signer: Signer

    /**
     * Retry policy
     */
    public val retryer: Retryer

    /**
     * The credentials provider to use when signing requests.
     */
    public val credentialsProvider: CredentialsProvider

    /**
     * Address stitching method
     */
    public val addressStyle: AddressStyleType

    /**
     * Signature method
     */
    public val authMethod: AuthMethodType

    /**
     * HTTP transmitter, responsible for sending HTTP requests
     */
    public val httpClient: HttpTransport

    /**
     * Additional signable headers.
     */
    public val additionalHeaders: List<String>

    /**
     * The feature Flags
     */
    public val featureFlags: FeatureFlagsType

    init {
        this.product = builder.product ?: Defaults.PRODUCT
        this.region = builder.region ?: ""
        this.endpoint = builder.endpoint ?: ""
        this.addressStyle = builder.addressStyle ?: AddressStyleType.VirtualHosted
        this.authMethod = builder.authMethod ?: AuthMethodType.Header
        this.signer = requireNotNull(builder.signer) { "signer is null" }
        this.retryer = requireNotNull(builder.retryer) { "retryer is null" }
        this.credentialsProvider = requireNotNull(builder.credentialsProvider) { "credentialsProvider is null" }
        this.httpClient = requireNotNull(builder.httpClient) { "httpClient is null" }
        this.additionalHeaders = builder.additionalHeaders ?: listOf()
        this.featureFlags = builder.featureFlags ?: Defaults.FEATURE_FLAGS
    }

    public inline fun copy(
        block: ClientOptions.Builder.() -> Unit = {
        }
    ): ClientOptions = Builder(this).apply(block).build()

    public fun toBuilder(): Builder {
        return Builder(this)
    }

    public companion object {
        /**
         * Builds a [ClientOptions] instance with the given [builder] function
         *
         * @param builder specifies a function to build a map
         */
        public inline fun build(builder: Builder.() -> Unit): ClientOptions = Builder().apply(builder).build()
    }

    public class Builder() {

        /**
         * Product name
         */
        public var product: String? = null

        /**
         * The region in which the bucket is located.
         */
        public var region: String? = null

        /**
         * The domain names that other services can use to access OSS.
         */
        public var endpoint: String? = null

        /**
         * Custom signer
         */
        public var signer: Signer? = null

        /**
         * Retry policy
         */
        public var retryer: Retryer? = null

        /**
         * The credentials provider to use when signing requests.
         */
        public var credentialsProvider: CredentialsProvider? = null

        /**
         * Address stitching method
         */
        public var addressStyle: AddressStyleType? = null

        /**
         * Signature method
         */
        public var authMethod: AuthMethodType? = null

        /**
         * HTTP transmitter, responsible for sending HTTP requests
         */
        public var httpClient: HttpTransport? = null

        /**
         * Additional signable headers.
         */
        public var additionalHeaders: List<String>? = null

        /**
         * The feature Flags
         */
        public var featureFlags: FeatureFlagsType? = null

        public fun build(): ClientOptions {
            return ClientOptions(this)
        }

        public constructor(opt: ClientOptions) : this() {
            this.product = opt.product
            this.region = opt.region
            this.endpoint = opt.endpoint
            this.addressStyle = opt.addressStyle
            this.authMethod = opt.authMethod
            this.additionalHeaders = opt.additionalHeaders
            this.featureFlags = opt.featureFlags
            this.signer = opt.signer
            this.retryer = opt.retryer
            this.credentialsProvider = opt.credentialsProvider
            this.httpClient = opt.httpClient
        }
    }
}
