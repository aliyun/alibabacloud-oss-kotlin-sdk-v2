
package com.aliyun.kotlin.sdk.service.oss2.signer

import com.aliyun.kotlin.sdk.service.oss2.hash.hmacSha1
import com.aliyun.kotlin.sdk.service.oss2.utils.HttpUtils
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlin.io.encoding.Base64
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * A no-operation signer implementation that performs no actual signing.
 */
public open class SignerV1 : Signer {
    /**
     * A set containing all query parameters that should be treated as sub-resources.
     */
    private companion object {
        val SUBRESOURCE_KEY_SET: MutableList<String> = mutableListOf<String>(
            "acl", "bucketInfo", "location", "stat", "delete", "append",
            "tagging", "objectMeta", "uploads", "uploadId", "partNumber",
            "security-token", "position", "response-content-type", "response-content-language",
            "response-expires", "response-cache-control", "response-content-disposition",
            "response-content-encoding", "restore", "callback", "callback-var",
            "versions", "versioning", "versionId", "sequential", "continuation-token",
            "regionList", "cloudboxes", "symlink", "resourceGroup", "cleanRestoredObject"
        )
    }

    /**
     * Determines if the given HTTP header should be signed.
     *
     * @param key The HTTP header name
     * @return true if the header should be signed, false otherwise
     */
    private fun isSignHeader(key: String?): Boolean {
        return key != null && key.startsWith("x-oss-")
    }

    /**
     * Extracts the date value from HTTP headers.
     *
     * @param headers The HTTP headers map
     * @return The extracted date string
     */
    private fun getDateFromHeaders(headers: MutableMap<String, String>): String {
        val xOssDate = headers.entries
            .filter {
                it.key.lowercase() == "x-oss-date"
            }
            .map { it.value }
            .firstOrNull()

        val dateHeader = headers.entries
            .filter {
                it.key.lowercase() == "date"
            }
            .map { it.value }
            .firstOrNull()

        val date = xOssDate ?: (dateHeader ?: "")

        return date
    }

    /**
     * Signs the given request context.
     * Decides whether to use header-based or query-based authentication.
     *
     * @param signingContext The context containing signing information
     */
    override suspend fun sign(signingContext: SigningContext) {
        if (signingContext.isAuthMethodQuery) {
            authQuery(signingContext)
        } else {
            authHeader(signingContext)
        }
    }

    /**
     * Authenticates the request using HTTP headers.
     *
     * @param signingCtx The context containing signing information
     */
    private fun authHeader(signingCtx: SigningContext) {
        val cred = requireNotNull(signingCtx.credentials)

        preAuthHeader(signingCtx)
        val signature = calcSignature(cred.accessKeySecret, requireNotNull(signingCtx.stringToSign))
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
        val signature = calcSignature(cred.accessKeySecret, requireNotNull(signingCtx.stringToSign))
        postAuthQuery(signingCtx, signature)
    }

    @OptIn(ExperimentalTime::class)
    internal fun preAuthHeader(signingCtx: SigningContext) {
        val request = requireNotNull(signingCtx.request)
        val cred = requireNotNull(signingCtx.credentials)

        val datetimeNow = when (val value = signingCtx.signTimeInEpoch) {
            null -> Clock.System.now().plus(signingCtx.clockOffset ?: Duration.ZERO)
            else -> Instant.fromEpochSeconds(value)
        }

        val dateRfc2822 = datetimeNow.format(DateTimeComponents.Formats.RFC_1123)
        request.headers["Date"] = dateRfc2822
        cred.securityToken?.let { request.headers["security-token"] = it }

        val stringToSign = calcStringToSign(signingCtx, null)
        signingCtx.stringToSign = stringToSign
        signingCtx.signTimeInEpoch = datetimeNow.epochSeconds
    }

    internal fun postAuthHeader(signingCtx: SigningContext, signature: String) {
        val request = requireNotNull(signingCtx.request)
        val cred = requireNotNull(signingCtx.credentials)

        val credentialHeader = "OSS ${cred.accessKeyId}:$signature"
        request.headers["Authorization"] = credentialHeader
    }

    @OptIn(ExperimentalTime::class)
    internal fun preAuthQuery(signingCtx: SigningContext) {
        val request = requireNotNull(signingCtx.request)
        val cred = requireNotNull(signingCtx.credentials)

        val datetimeNow = when (val value = signingCtx.signTimeInEpoch) {
            null -> Clock.System.now().plus(signingCtx.clockOffset ?: Duration.ZERO)
            else -> Instant.fromEpochSeconds(value)
        }
        val expirationTime = when (val value = signingCtx.expirationInEpoch) {
            null -> Clock.System.now().plus(signingCtx.clockOffset ?: Duration.ZERO)
            else -> Instant.fromEpochSeconds(value)
        }
        val expires = expirationTime.epochSeconds

        val parameters = HttpUtils.extractParams(request.url)
        parameters.remove("Signature")
        parameters.remove("security-token")

        parameters["OSSAccessKeyId"] = cred.accessKeyId
        parameters["Expires"] = expires.toString()

        cred.securityToken?.let { parameters["security-token"] = it }

        val query = HttpUtils.encodeQueryParameters(parameters)
        signingCtx.request?.url = buildString {
            append(request.url.split("?")[0])
            if (query.isNotEmpty()) {
                append("?").append(query)
            }
        }
        val stringToSign = calcStringToSign(signingCtx, expires.toString())

        signingCtx.stringToSign = stringToSign
        signingCtx.signTimeInEpoch = datetimeNow.epochSeconds
        signingCtx.expirationInEpoch = expirationTime.epochSeconds
    }

    internal fun postAuthQuery(signingCtx: SigningContext, signature: String) {
        val request = requireNotNull(signingCtx.request)

        val parameters = HttpUtils.extractParams(request.url)
        parameters["Signature"] = signature
        val query = HttpUtils.encodeQueryParameters(parameters)
        signingCtx.request?.url = buildString {
            append(request.url.split("?")[0])
            if (query.isNotEmpty()) {
                append("?").append(query)
            }
        }
    }

    /**
     * Builds the canonical string to be signed.
     *
     * @param signingCtx The context containing signing information
     * @param date       An optional date string to include
     * @return The canonical string to be signed
     */
    private fun calcStringToSign(signingCtx: SigningContext, date: String?): String {
        val request = requireNotNull(signingCtx.request)

        var canonicalUri: String? = "/"

        signingCtx.bucket?.let { canonicalUri += "$it/" }
        signingCtx.key?.let { canonicalUri += it }

        var canonicalQuery = ""
        val queryMap = HttpUtils.extractParamsWithoutDecode(request.url)
        val sortedEntries = queryMap.entries.sortedBy { it.key }

        val queryParts: MutableList<String> = mutableListOf()
        for (entry in sortedEntries) {
            val key = HttpUtils.urlDecode(entry.key)
            val value = HttpUtils.urlDecode(entry.value)
            if (SUBRESOURCE_KEY_SET.contains(key) || signingCtx.subResource?.contains(key) == true) {
                if (!value.isEmpty()) {
                    queryParts.add("$key=$value")
                } else {
                    queryParts.add(key)
                }
            }
        }
        if (!queryParts.isEmpty()) {
            canonicalQuery = "?" + queryParts.joinToString("&")
        }

        val canonicalResource = canonicalUri + canonicalQuery

        val canonicalHeaders = buildCanonicalHeaders(request.headers)

        var contentMd5: String? = ""
        var contentType: String? = ""

        for (entry in request.headers.entries) {
            val key: String = entry.key.lowercase()
            if (key == "content-md5") {
                contentMd5 = entry.value
            } else if (key == "content-type") {
                contentType = entry.value
            }
        }

        val dateHeader = date ?: getDateFromHeaders(request.headers)

        return buildString {
            appendLine(request.method)
            appendLine(contentMd5)
            appendLine(contentType)
            appendLine(dateHeader)
            append(canonicalHeaders + canonicalResource)
        }
    }

    /**
     * Calculates the signature using HmacSHA1 algorithm.
     *
     * @param accessKeySecret The secret key used for signing
     * @param stringToSign    The canonical string to sign
     * @return The Base64 encoded signature result
     */
    private fun calcSignature(
        accessKeySecret: String,
        stringToSign: String
    ): String {
        val signatureBytes = stringToSign.toByteArray().hmacSha1(accessKeySecret.toByteArray())
        return Base64.encode(signatureBytes)
    }

    /**
     * Builds the canonical headers section for signature calculation.
     *
     * @param headers The HTTP headers map
     * @return The canonical headers string
     */
    private fun buildCanonicalHeaders(headers: MutableMap<String, String>): String {
        val lowKeyMap: MutableMap<String, String> = mutableMapOf()
        for (entry in headers.entries) {
            val key = entry.key.lowercase()
            if (entry.value.isEmpty()) {
                continue
            }
            if (isSignHeader(key)) {
                lowKeyMap.put(key, entry.value)
            }
        }

        return lowKeyMap.entries
            .sortedBy { it.key }
            .joinToString("") { "${it.key}:${it.value}\n" }
    }
}
