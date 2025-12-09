@file:OptIn(ExperimentalTime::class)

package com.aliyun.kotlin.sdk.service.oss2.internal

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.ClientOptions
import com.aliyun.kotlin.sdk.service.oss2.Defaults
import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.OperationMetadataKey
import com.aliyun.kotlin.sdk.service.oss2.OperationOptions
import com.aliyun.kotlin.sdk.service.oss2.OperationOutput
import com.aliyun.kotlin.sdk.service.oss2.credentials.AnonymousCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.exceptions.CredentialsException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.OperationException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.PresignExpirationException
import com.aliyun.kotlin.sdk.service.oss2.internal.OssUtils.EndpointType
import com.aliyun.kotlin.sdk.service.oss2.retry.FixTimeRetryHandler
import com.aliyun.kotlin.sdk.service.oss2.retry.RetryHandler
import com.aliyun.kotlin.sdk.service.oss2.retry.Retryer
import com.aliyun.kotlin.sdk.service.oss2.retry.StandardRetryer
import com.aliyun.kotlin.sdk.service.oss2.signer.Signer
import com.aliyun.kotlin.sdk.service.oss2.signer.SignerV1
import com.aliyun.kotlin.sdk.service.oss2.signer.SignerV4
import com.aliyun.kotlin.sdk.service.oss2.signer.SigningContext
import com.aliyun.kotlin.sdk.service.oss2.transport.HttpTransport
import com.aliyun.kotlin.sdk.service.oss2.transport.HttpTransportConfig
import com.aliyun.kotlin.sdk.service.oss2.transport.RequestMessage
import com.aliyun.kotlin.sdk.service.oss2.transport.createHttpTransport
import com.aliyun.kotlin.sdk.service.oss2.types.AddressStyleType
import com.aliyun.kotlin.sdk.service.oss2.types.AuthMethodType
import com.aliyun.kotlin.sdk.service.oss2.types.FeatureFlagsType
import com.aliyun.kotlin.sdk.service.oss2.types.get
import com.aliyun.kotlin.sdk.service.oss2.utils.HttpUtils
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils
import com.aliyun.kotlin.sdk.service.oss2.utils.VersionInfoUtils
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * Implementation of the OSS client responsible for building and executing operation requests
 */
internal class ClientImpl(
    config: ClientConfiguration,
    optFns: List<(ClientOptions) -> ClientOptions> = listOf()
) : AutoCloseable {
    /**
     * Configuration options for the client including endpoint, signer, credentials provider, etc.
     */
    val options: ClientOptions

    /**
     *
     */
    val innerOptions: InnerOptions

    /**
     * Execution stack used to process middleware logic such as signing, retries, and transport
     */
    val executeStack: ExecuteStack

    /**
     * Constructor that initializes the client instance and execution pipeline based on configuration
     *
     * @param config Base client configuration
     * @param optFns Array of functions used to customize client options
     */
    init {
        var opts = resolveConfig(config)
        for (fn in optFns) {
            opts = fn(opts)
        }
        this.options = opts

        val innerOpts = InnerOptions()
        innerOpts.userAgent = resolveUserAgent(config)
        innerOpts.addressStyle = this.options.addressStyle
        innerOpts.logger = config.logger

        // parse endpoint to Protocol, host
        parseUrl(this.options.endpoint)?.let {
            innerOpts.scheme = it["scheme"] ?: ""
            innerOpts.authority = it["authority"] ?: ""
            innerOpts.host = it["host"] ?: ""
            if (OssUtils.isIpHost(innerOpts.host)) {
                innerOpts.addressStyle = AddressStyleType.Path
            }
        }

        this.innerOptions = innerOpts

        // build execute stack
        val transport = TransportExecuteMiddleware(opts.httpClient, config.logger)
        val stack = ExecuteStack(transport)

        stack.push(
            { x ->
                val retryHandler: RetryHandler? = if (featureFlags.contains(FeatureFlagsType.CORRECT_CLOCK_SKEW)) {
                    FixTimeRetryHandler()
                } else {
                    null
                }
                RetryerExecuteMiddleware(
                    x,
                    this.options.retryer,
                    config.logger,
                    retryHandler
                )
            },
            "Retryer"
        )

        stack.push(
            { x ->
                SignerExecuteMiddleware(
                    x,
                    this.options.credentialsProvider,
                    this.options.signer,
                    config.logger
                )
            },
            "Signer"
        )

        stack.push(
            { x -> ResponseCheckerExecuteMiddleware(x) },
            "ResponseChecker"
        )

        this.executeStack = stack
    }

    /**
     * Closes client resources including underlying HTTP connection pools
     *
     * @throws Exception If an error occurs while closing
     */
    override fun close() {
        if (this.options.httpClient is AutoCloseable) {
            this.options.httpClient.close()
        }
    }

    /**
     * executes the provided operation input and returns the output result
     *
     * @param input Input parameters for the operation
     * @param opts  Options specific to this operation
     * @return Returns a fully constructed [OperationOutput] object
     */
    suspend fun execute(input: OperationInput, opts: OperationOptions?): OperationOutput {
        // verify input
        verifyOperation(input)

        // build execute context;
        val (request, ctx) = buildRequestContext(input, opts)

        // execute
        try {
            val response = this.executeStack.execute(request, ctx)
            return OperationOutput {
                this.input = input
                this.statusCode = response.statusCode
                this.status = response.status
                this.headers = response.headers
                this.body = response.body
            }
        } catch (e: Exception) {
            throw OperationException(input.opName, e)
        }
    }

    /**
     * Sign the provided operation input and returns presigned url.
     *
     * @param input Input parameters for the operation
     * @param opts  Options specific to this operation
     * @return the presigned url.
     */
    suspend fun presignInner(input: OperationInput, opts: OperationOptions?): PresignInnerResult {
        // verify input
        verifyOperation(input)

        // build execute context;
        var (request, ctx) = buildRequestContext(input, opts)

        // build execute context;
        val provider = this.options.credentialsProvider
        val result = PresignInnerResult()
        val signingContext = requireNotNull(ctx.signingContext) { "signingContext is null." }

        if (provider !is AnonymousCredentialsProvider) {
            val cred = provider.getCredentials()
            val signer = this.options.signer

            if (!cred.hasKeys()) {
                throw CredentialsException("Credentials is null or empty.")
            }
            signingContext.credentials = cred
            signingContext.request = request
            signingContext.isAuthMethodQuery = true
            signer.sign(signingContext)

            request = requireNotNull(signingContext.request) { "signingContext.request is null." }

            // save to result
            signingContext.expirationInEpoch?.let {
                result.expiration = Instant.fromEpochSeconds(it)
            }

            // signed headers
            // content-type, content-md5, x-oss- and additionalHeaders in sign v4
            val expect: MutableList<String?> = ArrayList()
            expect.add("content-type")
            expect.add("content-md5")
            if (signer is SignerV4) {
                // check
                val nowTo7Days = Clock.System.now().plus(7.days)
                if (result.expiration != null && result.expiration!! > nowTo7Days) {
                    throw PresignExpirationException()
                }
                signingContext.additionalHeaders?.forEach { x -> expect.add(x.lowercase()) }
            }

            // signed headers
            val signedHeaders = MapUtils.headersMap()
            request.headers.forEach { (k, v) ->
                val low = k.lowercase()
                if (expect.contains(low) || low.startsWith("x-oss-")) {
                    signedHeaders.put(k, v)
                }
            }
            result.signedHeaders = signedHeaders
        }
        result.url = request.url
        result.method = request.method

        return result
    }

    val featureFlags: FeatureFlagsType
        get() = options.featureFlags

    /**
     * Validates the operation input including endpoint, bucket name, and key constraints
     *
     * @param input The operation input to validate
     */
    private fun verifyOperation(input: OperationInput) {
        // check endpoint
        require(this.innerOptions.host.isNotEmpty()) { "endpoint or region is invalid." }

        // check method
        require(input.method.isNotEmpty()) { "input.method is empty." }

        // check bucket name
        if (input.bucket != null) {
            require(Ensure.isValidateBucketName(input.bucket)) { "input.bucket is invalid, got ${input.bucket}." }
        }

        // check object name
        if (input.key != null) {
            require(Ensure.isValidateObjectName(input.key)) { "input.key is invalid, got  ${input.key}." }
        }
    }

    /**
     * Builds the request message and execution context for network transmission
     *
     * @param input  The operation input object
     * @param opOpts The operation-specific configuration options
     * @return A Pair containing the request message and execution context
     */
    private fun buildRequestContext(
        input: OperationInput,
        opOpts: OperationOptions?
    ): Pair<RequestMessage, ExecuteContext> {
        val opOpts = opOpts ?: OperationOptions.Default
        val context = ExecuteContext()

        // default api options
        context.retryMaxAttempts = opOpts.retryMaxAttempts ?: this.options.retryer.maxAttempts()

        // track request body
        context.requestBodyObserver = input.opMetadata.getOrNull(OperationMetadataKey.UPLOAD_OBSERVER)

        // response handlers
        context.responseHandlers.add(OnServiceError)
        if (input.opMetadata.contains(OperationMetadataKey.RESPONSE_HANDLE)) {
            context.responseHandlers.addAll(input.opMetadata[OperationMetadataKey.RESPONSE_HANDLE])
        }

        // stream mode
        if (input.opMetadata.contains(OperationMetadataKey.RESPONSE_HEADERS_READ)) {
            context.responseHeadersRead = input.opMetadata[OperationMetadataKey.RESPONSE_HEADERS_READ]
        }

        // signing context
        val authMethod = opOpts.authMethod ?: this.options.authMethod
        val signCtx = SigningContext().apply {
            product = options.product
            region = options.region
            bucket = input.bucket
            key = input.key
            isAuthMethodQuery = (authMethod == AuthMethodType.Query)
            subResource = listOf()
            additionalHeaders = this@ClientImpl.options.additionalHeaders
        }

        // signing time from user
        input.opMetadata.getOrNull(OperationMetadataKey.EXPIRATION_EPOCH_SEC)?.let {
            signCtx.expirationInEpoch = it
        }
        context.signingContext = signCtx

        // request
        // request::host & path & query
        val query = HttpUtils.encodeQueryParameters(input.parameters)
        val url = buildString {
            append(innerOptions.scheme)
            append("://")
            append(OssUtils.buildHostPath(input, innerOptions.authority, innerOptions.addressStyle))
            if (query.isNotEmpty()) {
                append("?").append(query)
            }
        }

        // request::headers
        val headers = input.headers
        headers.put("User-Agent", innerOptions.userAgent)

        // request::body
        // val body = input.body

        val request = RequestMessage().apply {
            this.url = url
            this.method = input.method
            this.headers = headers
            this.body = input.body
        }

        return Pair(request, context)
    }

    /**
     * Resolves and constructs the final ClientOptions from the provided configuration
     *
     * @param config Base client configuration
     * @return The resolved ClientOptions instance [ClientOptions]
     */
    private fun resolveConfig(config: ClientConfiguration): ClientOptions {
        // val endpoint = resolveEndpoint(config)
        return ClientOptions.build {
            product = Defaults.PRODUCT
            region = config.region
            endpoint = resolveEndpoint(config)
            retryer = resolveRetryer(config)
            signer = resolveSigner(config)
            credentialsProvider = config.credentialsProvider
            httpClient = resolveHttpClient(config)
            addressStyle = resolveAddressStyle(config)
            authMethod = AuthMethodType.Header
            additionalHeaders = config.additionalHeaders
            featureFlags = resolveFeatureFlags(config)
        }
    }

    /**
     * Parses and generates the service endpoint URI
     *
     * @param config Base client configuration
     * @return The parsed service endpoint URI
     */
    private fun resolveEndpoint(config: ClientConfiguration): String? {
        if (config.endpoint == null && config.region == null) {
            return null
        }

        val disableSsl = config.disableSsl ?: Defaults.DISABLE_SSL
        val region = config.region ?: ""
        var endpoint = config.endpoint ?: ""

        if (config.endpoint != null) {
            endpoint = OssUtils.addScheme(endpoint, disableSsl)
        } else if (Ensure.isValidRegion(region)) {
            val type = if (config.useDualStackEndpoint ?: false) {
                EndpointType.DualStack
            } else if (config.useInternalEndpoint ?: false) {
                EndpointType.Internal
            } else if (config.useAccelerateEndpoint ?: false) {
                EndpointType.Accelerate
            } else {
                EndpointType.Default
            }
            endpoint = OssUtils.regionToEndpoint(region, type, disableSsl)
        }

        return endpoint
        // return parseUrl(endpoint)
    }

    /**
     * Determines the address style type to use (VirtualHosted / Path)
     *
     * @param config   Base client configuration
     * @return The resolved address style type [AddressStyleType]
     */
    private fun resolveAddressStyle(config: ClientConfiguration): AddressStyleType {
        val style = if (config.useCName ?: false) {
            AddressStyleType.CName
        } else if (config.usePathStyle ?: false) {
            AddressStyleType.Path
        } else {
            AddressStyleType.VirtualHosted
        }

        return style
    }

    /**
     * Resolves and returns the retry strategy to be used by this client
     *
     * @param config Base client configuration
     * @return The resolved Retryer instance [Retryer]
     */
    private fun resolveRetryer(config: ClientConfiguration): Retryer {
        return config.retryer ?: StandardRetryer.newBuilder()
            .setMaxAttempts(config.retryMaxAttempts ?: Defaults.MAX_ATTEMPTS)
            .build()
    }

    /**
     * Resolves and returns the signer implementation to be used by this client
     *
     * @param config Base client configuration
     * @return The resolved Signer instance [Signer]
     */
    private fun resolveSigner(config: ClientConfiguration): Signer {
        return (
            config.signer ?: when (config.signatureVersion) {
                "v1" -> SignerV1()
                else -> SignerV4()
            }
            )
    }

    /**
     * Resolves and returns the HTTP client implementation to be used by this client
     *
     * @param config Base client configuration
     * @return The resolved HttpClient instance [HttpTransport]
     */
    private fun resolveHttpClient(config: ClientConfiguration): HttpTransport {
        return config.httpTransport ?: createHttpTransport(
            HttpTransportConfig(
                connectTimeout = config.connectTimeout,
                readWriteTimeout = config.readWriteTimeout,
                proxy = config.proxyHost,
                enabledRedirect = config.enabledRedirect
            )
        )
    }

    /**
     * Resolves and returns the user agent
     *
     * @param config Base client configuration
     * @return The user agent
     */
    private fun resolveUserAgent(config: ClientConfiguration): String {
        var ua = VersionInfoUtils.defaultUserAgent
        // Append httpclient name
        ua = ua + "/" + this.options.httpClient.name
        if (config.userAgent != null) {
            ua = ua + "/" + config.userAgent
        }
        return ua
    }

    private fun resolveFeatureFlags(config: ClientConfiguration): FeatureFlagsType {
        val flags = Defaults.FEATURE_FLAGS
        config.disableUploadCRC64Check?.let {
            if (it) {
                flags.remove(FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD)
            } else {
                flags.insert(FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD)
            }
        }
        config.disableDownloadCRC64Check?.let {
            if (it) {
                flags.remove(FeatureFlagsType.ENABLE_CRC64_CHECK_DOWNLOAD)
            } else {
                flags.insert(FeatureFlagsType.ENABLE_CRC64_CHECK_DOWNLOAD)
            }
        }
        return flags
    }

    companion object {
        /**
         * Instance used globally to handle service error responses
         */
        val OnServiceError = ServiceErrorResponseHandler()
    }
}
