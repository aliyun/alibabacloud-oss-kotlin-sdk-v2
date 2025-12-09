@file:OptIn(ExperimentalTime::class)

package com.aliyun.kotlin.sdk.service.oss2.signer

import com.aliyun.kotlin.sdk.service.oss2.hash.hmacSha256
import com.aliyun.kotlin.sdk.service.oss2.hash.sha256
import com.aliyun.kotlin.sdk.service.oss2.transport.RequestMessage
import com.aliyun.kotlin.sdk.service.oss2.utils.HexUtils
import com.aliyun.kotlin.sdk.service.oss2.utils.HttpUtils
import kotlinx.datetime.*
import kotlinx.datetime.format.*
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.DateTimeComponents.Companion.Format
import kotlin.collections.isNotEmpty
import kotlin.text.isNotEmpty
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * Implements the OSS V4 signature protocol for request signing.
 */
public open class SignerV4 : Signer {
    /**
     * Signs the given request context.
     * Decides whether to use header-based or query-based authentication.
     *
     * @param signingContext The context containing signing information
     */
    override suspend fun sign(signingContext: SigningContext) {
        if (signingContext.isAuthMethodQuery) {
            return authQuery(signingContext)
        }

        return authHeader(signingContext)
    }

    /**
     * Authenticates the request using HTTP headers.
     *
     * @param signingCtx The context containing signing information
     */
    private fun authHeader(signingCtx: SigningContext) {
        val cred = requireNotNull(signingCtx.credentials)

        preAuthHeader(signingCtx)
        val signature = calcSignature(
            cred.accessKeySecret,
            requireNotNull(signingCtx.scopeToSign?.substring(0, 8)),
            signingCtx.region ?: "",
            signingCtx.product ?: "",
            requireNotNull(signingCtx.stringToSign)
        )
        // System.out.printf("signature:\n%s\n", signature);
        postAuthHeader(signingCtx, signature)
    }

    /**
     * Authenticates the request using query parameters.
     *
     * @param signingCtx The context containing signing information
     */
    private fun authQuery(signingCtx: SigningContext) {
        val cred = requireNotNull(signingCtx.credentials)

        preAuthQuery(signingCtx)
        val signature = calcSignature(
            cred.accessKeySecret,
            requireNotNull(signingCtx.scopeToSign?.substring(0, 8)),
            signingCtx.region ?: "",
            signingCtx.product ?: "",
            requireNotNull(signingCtx.stringToSign)
        )
        // System.out.printf("signature:\n%s\n", signature);
        postAuthQuery(signingCtx, signature)
    }

    internal fun preAuthHeader(signingCtx: SigningContext) {
        val request = requireNotNull(signingCtx.request)
        val cred = requireNotNull(signingCtx.credentials)

        val now = getSignTime(signingCtx)
        val iso8601Date = now.format(ISO8601_FORMAT)
        val rfc2822Date = now.format(DateTimeComponents.Formats.RFC_1123)
        val date = iso8601Date.substring(0, 8)

        val region = signingCtx.region ?: ""
        val product = signingCtx.product ?: ""
        val scope = buildScope(date, region, product)

        request.headers.put("x-oss-date", iso8601Date)
        request.headers.put("Date", rfc2822Date)

        cred.securityToken?.let {
            if (it.isNotEmpty()) {
                request.headers.put("x-oss-security-token", it)
            }
        }
        request.headers.put("x-oss-content-sha256", "UNSIGNED-PAYLOAD")

        val normalizeHeader = normalizeHeaders(request, signingCtx.additionalHeaders)
        val encodedParameters = extractParamsWithoutDecode(request.url)

        val canonicalRequest = calcCanonicalRequest(signingCtx, normalizeHeader, encodedParameters)
        val stringToSign = calcStringToSign(iso8601Date, scope, canonicalRequest)
        // System.out.printf("canonicalRequest:\n%s\n", canonicalRequest);

        signingCtx.stringToSign = stringToSign
        signingCtx.signTimeInEpoch = now.epochSeconds
        signingCtx.additionalHeaders = normalizeHeader.second
        signingCtx.scopeToSign = scope
    }

    internal fun postAuthHeader(signingCtx: SigningContext, signature: String) {
        val request = requireNotNull(signingCtx.request)
        val cred = requireNotNull(signingCtx.credentials)
        val scope = requireNotNull(signingCtx.scopeToSign)

        val authHeader = buildString {
            append("OSS4-HMAC-SHA256 Credential=")
            append(cred.accessKeyId)
            append("/")
            append(scope)

            signingCtx.additionalHeaders?.let {
                if (it.isNotEmpty()) {
                    append(",AdditionalHeaders=")
                    append(it.joinToString(";"))
                }
            }

            append(",Signature=")
            append(signature)
        }
        request.headers.put("Authorization", authHeader)
    }

    internal fun preAuthQuery(signingCtx: SigningContext) {
        val request = requireNotNull(signingCtx.request)
        val cred = requireNotNull(signingCtx.credentials)

        val now = getSignTime(signingCtx)
        val expiration = getExpirationTime(signingCtx)
        val iso8601Date = now.format(ISO8601_FORMAT)
        val date = iso8601Date.substring(0, 8)
        val expires = expiration.minus(now).inWholeSeconds

        val region = signingCtx.region ?: ""
        val product = signingCtx.product ?: ""
        val scope = buildScope(date, region, product)

        val normalizedHeaders = normalizeHeaders(request, signingCtx.additionalHeaders)

        val encodedParameters = extractParamsWithoutDecode(request.url)

        // val parameters = HttpUtils.uriParams(request.url)
        removeSignatureParams(encodedParameters)
        encodedParameters.put("x-oss-signature-version", "OSS4-HMAC-SHA256")
        encodedParameters.put("x-oss-date", iso8601Date)
        encodedParameters.put("x-oss-expires", expires.toString())
        encodedParameters.put("x-oss-credential", HttpUtils.urlEncode("${cred.accessKeyId}/$scope"))

        cred.securityToken?.let {
            if (it.isNotEmpty()) {
                encodedParameters.put(
                    "x-oss-security-token",
                    HttpUtils.urlEncode(it)
                )
            }
        }

        if (normalizedHeaders.second.isNotEmpty()) {
            encodedParameters.put(
                "x-oss-additional-headers",
                HttpUtils.urlEncode(normalizedHeaders.second.joinToString(";"))
            )
        }
        val query = joinToQueryString(encodedParameters)
        signingCtx.request!!.url = buildString {
            append(request.url.split("?")[0])
            if (query.isNotEmpty()) {
                append("?").append(query)
            }
        }

        val canonicalRequest = calcCanonicalRequest(signingCtx, normalizedHeaders, encodedParameters)
        val stringToSign = calcStringToSign(iso8601Date, scope, canonicalRequest)
        // System.out.printf("canonicalRequest:\n%s\n", canonicalRequest);

        signingCtx.stringToSign = stringToSign
        signingCtx.signTimeInEpoch = now.epochSeconds
        signingCtx.expirationInEpoch = expiration.epochSeconds
        signingCtx.scopeToSign = scope
        signingCtx.additionalHeaders = normalizedHeaders.second
    }

    internal fun postAuthQuery(signingCtx: SigningContext, signature: String) {
        val request = requireNotNull(signingCtx.request)

        val encodedParameters = extractParamsWithoutDecode(request.url)
        encodedParameters.put("x-oss-signature", HttpUtils.urlEncode(signature))

        val query = joinToQueryString(encodedParameters)
        signingCtx.request!!.url = buildString {
            append(request.url.split("?")[0])
            if (query.isNotEmpty()) {
                append("?").append(query)
            }
        }
    }

    /**
     * Gets the current signing time, preferring the one from the context.
     *
     * @param signingCtx The context containing signing time info
     * @return The timestamp used for signing
     */
    private fun getSignTime(signingCtx: SigningContext): Instant {
        return when (val value = signingCtx.signTimeInEpoch) {
            null -> Clock.System.now().plus(signingCtx.clockOffset ?: Duration.ZERO)
            else -> Instant.fromEpochSeconds(value)
        }
    }

    /**
     * Gets the expiration time of the signature, preferring the one from the context.
     *
     * @param signingCtx The context containing expiration info
     * @return The expiration timestamp
     */
    private fun getExpirationTime(signingCtx: SigningContext): Instant {
        return when (val value = signingCtx.expirationInEpoch) {
            null -> Clock.System.now().plus(15, DateTimeUnit.MINUTE)
            else -> Instant.fromEpochSeconds(value)
        }
    }

    /**
     * Builds the signing scope string.
     *
     * @param date    Signing date
     * @param region  Region identifier
     * @param product Product identifier
     * @return The constructed scope string
     */
    private fun buildScope(date: String, region: String, product: String): String {
        return buildString {
            append(date)
            append("/")
            append(region)
            append("/")
            append(product)
            append("/aliyun_v4_request")
        }
    }

    /**
     * Extracts and normalizes headers that should be signed.
     *
     * @param request           Current request object
     * @param additionalHeaders Optional list of headers to include in signing
     * @return A sorted and low-cased headers list and additional signed header key list
     */
    private fun normalizeHeaders(
        request: RequestMessage,
        additionalHeaders: List<String>?
    ): Pair<List<Pair<String, String>>, List<String>> {
        val filterSignedHeader = mutableListOf<String>()
        val addSignedHeaders = when (additionalHeaders) {
            null -> setOf()
            else -> additionalHeaders.map { it.lowercase() }.toSet()
        }
        val includeAdditionalHeader = { key: String ->
            val exist = addSignedHeaders.contains(key)
            if (exist) {
                filterSignedHeader.add(key)
            }
            exist
        }

        val headers = request
            .headers
            .map { it.key.lowercase() to it.value }
            .asSequence()
            .filter { includeDefaultSignHeader(it.first) || includeAdditionalHeader(it.first) }
            .sortedBy { it.first }
            .toList()

        return Pair(headers, filterSignedHeader.sortedBy { it }.toList())
    }

    /**
     * Extracts query parameters from the given URI, do not do url decode
     */
    private fun extractParamsWithoutDecode(uri: String): MutableMap<String, String> {
        val start = uri.indexOf("?")

        // no query string
        if (start < 0) {
            return mutableMapOf()
        }

        val rawQuery = uri.substring(start + 1)

        if (rawQuery.isNotEmpty()) {
            return rawQuery
                .split("&").associate { segment ->
                    val parts = segment.split("=")
                    val key = parts[0]
                    val value = when (parts.size) {
                        1 -> ""
                        2 -> parts[1]
                        else -> throw IllegalArgumentException("invalid query string segment $segment")
                    }
                    key to value
                }
                .toMutableMap()
        }

        return mutableMapOf()
    }

    /**
     * Builds the canonical request string used for signing.
     *
     * @param signingCtx        The signing context object
     * @param normalizesHeaders headers to include in signing
     * @return The canonical request string
     */
    private fun calcCanonicalRequest(
        signingCtx: SigningContext,
        normalizesHeaders: Pair<List<Pair<String, String>>, List<String>>,
        encodedParameters: Map<String, String>
    ): String {
        /*
            Canonical Request
            HTTP Verb + "\n" +
            Canonical URI + "\n" +
            Canonical Query String + "\n" +
            Canonical Headers + "\n" +
            Additional Headers + "\n" +
            Hashed PayLoad
         */

        val request = requireNotNull(signingCtx.request)
        val canonicalUri = buildCanonicalUri(signingCtx)
        val canonicalQuery = buildCanonicalQuery(encodedParameters)
        val canonicalHeaders = buildCanonicalHeaders(normalizesHeaders.first)
        val canonicalAdditional = normalizesHeaders.second.joinToString(";")
        val payloadHash = request.headers["x-oss-content-sha256"] ?: "UNSIGNED-PAYLOAD"

        return buildString {
            appendLine(request.method)
            appendLine(canonicalUri)
            appendLine(canonicalQuery)
            appendLine(canonicalHeaders)
            appendLine(canonicalAdditional)
            append(payloadHash)
        }
    }

    /**
     * Builds the canonical URI path for signing.
     *
     * @param signingCtx The signing context object
     * @return The URL-encoded URI string
     */
    private fun buildCanonicalUri(signingCtx: SigningContext): String {
        val sb = StringBuilder("/")

        signingCtx.bucket?.let {
            if (it.isNotEmpty()) {
                sb.append(signingCtx.bucket).append("/")
            }
        }

        signingCtx.key?.let {
            if (it.isNotEmpty()) {
                sb.append(it)
            }
        }

        return HttpUtils.urlEncodePath(sb.toString())
    }

    /**
     * Builds the canonical query part for signing.
     *
     * @param queryMap The encoded query
     * @return The encoded query parameter string
     */
    private fun buildCanonicalQuery(queryMap: Map<String, String>): String {
        return queryMap
            .entries
            .sortedBy { it.key }
            .map { (key, value) ->
                when (value) {
                    "" -> key
                    else -> "$key=$value"
                }
            }
            .toList()
            .joinToString("&")
    }

    /**
     * Builds the canonical headers section for signature calculation.
     *
     * @param normalizedHeaders  The normalized headers
     */
    private fun buildCanonicalHeaders(normalizedHeaders: List<Pair<String, String>>): String {
        if (normalizedHeaders.isEmpty()) {
            return ""
        }
        val str = normalizedHeaders
            .map { it -> "${it.first}:${it.second}" }
            .toList()
            .joinToString("\n")
        return "$str\n"
    }

    /**
     * Constructs the string to be signed.
     *
     * @param iso8601Date      ISO8601-formatted date string
     * @param scope            Signing scope
     * @param canonicalRequest Canonical request string
     * @return The full string to be signed
     */
    private fun calcStringToSign(
        iso8601Date: String,
        scope: String,
        canonicalRequest: String
    ): String {
        /**
         "OSS4-HMAC-SHA256" + "\n" +
         TimeStamp + "\n" +
         Scope + "\n" +
         Hex(SHA256Hash(Canonical Request))
         */
        return buildString {
            appendLine("OSS4-HMAC-SHA256")
            appendLine(iso8601Date)
            appendLine(scope)
            append(HexUtils.encodeHex(canonicalRequest.toByteArray().sha256()))
        }
    }

    /**
     * Calculates the signature using HMacSHA256 algorithm.
     *
     * @param accessKeySecret The secret key used for signing
     * @param date            Signing date
     * @param region          Region identifier
     * @param product         Product identifier
     * @param stringToSign    The string to sign
     * @return Hex-encoded signature result
     */
    private fun calcSignature(
        accessKeySecret: String,
        date: String,
        region: String,
        product: String,
        stringToSign: String
    ): String {
        val key = "aliyun_v4$accessKeySecret".toByteArray()
        val dateKey = date.toByteArray().hmacSha256(key)
        val regionKey = region.toByteArray().hmacSha256(dateKey)
        val productKey = product.toByteArray().hmacSha256(regionKey)
        val requestKey = "aliyun_v4_request".toByteArray().hmacSha256(productKey)
        val signatureBytes = stringToSign.toByteArray().hmacSha256(requestKey)

        return HexUtils.encodeHex(signatureBytes)
    }

    /**
     * Removes query parameters related to signing to avoid duplication.
     *
     * @param params Query parameter map
     */
    private fun removeSignatureParams(params: MutableMap<String, String>) {
        params.remove("x-oss-signature")
        params.remove("x-oss-security-token")
        params.remove("x-oss-additional-headers")
    }

    private fun joinToQueryString(queryMap: Map<String, String>): String {
        return queryMap
            .entries
            .map { (key, value) ->
                when (value) {
                    "" -> key
                    else -> "$key=$value"
                }
            }
            .toList()
            .joinToString("&")
    }

    /**
     * Checks whether the specified HTTP header is a default signed header.
     *
     * @param key HTTP header name, in lowed case
     * @return true if it's a default signed header, false otherwise
     */
    private fun includeDefaultSignHeader(key: String): Boolean {
        return key.startsWith("x-oss-") || key == "content-type" || key == "content-md5"
    }

    private companion object {
        /**
         * Date-time format string used for ISO8601 formatted timestamps.
         * "yyyyMMdd'T'HHmmss'Z'"
         */
        private val ISO8601_FORMAT: DateTimeFormat<DateTimeComponents> = Format {
            year()
            monthNumber()
            day()
            char('T')
            hour()
            minute()
            second()
            chars("Z")
        }
    }
}
