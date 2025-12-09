package com.aliyun.kotlin.sdk.service.oss2.internal

import com.aliyun.kotlin.sdk.service.oss2.Defaults
import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.types.AddressStyleType
import com.aliyun.kotlin.sdk.service.oss2.utils.HttpUtils
import com.aliyun.kotlin.sdk.service.oss2.utils.InetAddressUtils

/**
 * Utility class for handling common logic such as endpoint construction and address formatting
 */
internal object OssUtils {
    /**
     * Adds a scheme (http/https) to the given URL string if not already present
     *
     * @param value      The original URL string
     * @param disableSsl Whether to use HTTP instead of HTTPS
     * @return A URL string with the appropriate scheme prepended
     */
    fun addScheme(value: String, disableSsl: Boolean): String {
        if (!value.matches("^[^:]+://.*".toRegex())) {
            val scheme = if (disableSsl) "http" else Defaults.HTTP_SCHEME
            return "$scheme://$value"
        }
        return value
    }

    /**
     * Generates an OSS endpoint based on the region and endpoint type
     *
     * @param value      Region name such as "cn-hangzhou"
     * @param type       Type of the endpoint (Internal, DualStack, etc.)
     * @param disableSsl Whether to use HTTP instead of HTTPS
     * @return Formatted endpoint URL string
     */
    fun regionToEndpoint(value: String, type: EndpointType, disableSsl: Boolean): String {
        val scheme = if (disableSsl) "http" else Defaults.HTTP_SCHEME
        val endpoint = when (type) {
            EndpointType.DualStack -> "$value.oss.aliyuncs.com"
            EndpointType.Internal -> "oss-$value-internal.aliyuncs.com"
            EndpointType.Accelerate -> "oss-accelerate.aliyuncs.com"
            EndpointType.Overseas -> "oss-accelerate-overseas.aliyuncs.com"
            else -> "oss-$value.aliyuncs.com"
        }

        return "$scheme://$endpoint"
    }

    /**
     * Builds the host and path portion based on the provided address style
     *
     * @param input   Operation input containing bucket and object key information
     * @param baseUrl Base domain or host name
     * @param style   Address style type (VirtualHosted / Path / CName)
     * @return Constructed host/path string based on the input and address style
     */
    fun buildHostPath(input: OperationInput, baseUrl: String, style: AddressStyleType): String {
        val paths: MutableList<String> = mutableListOf()
        var host = baseUrl

        if (input.bucket != null) {
            when (style) {
                AddressStyleType.Path -> {
                    paths.add(input.bucket)
                    if (input.key == null) {
                        paths.add("")
                    }
                }
                AddressStyleType.CName -> {}
                AddressStyleType.VirtualHosted -> host = "${input.bucket}.$host"
            }
        }

        if (input.key != null) {
            paths.add(HttpUtils.urlEncodePath(input.key))
        }

        return "$host/${paths.joinToString("/")}"
    }

    /**
     * Enumeration of different types of endpoints supported by OSS service
     */
    internal enum class EndpointType {
        Default,
        DualStack,
        Internal,
        Accelerate,
        Overseas
    }

    /**
     * If it is IP or local host, set to path-style
     */
    internal fun isIpHost(host: String): Boolean {
        return InetAddressUtils.isValid(host) || host == "localhost"
    }
}
