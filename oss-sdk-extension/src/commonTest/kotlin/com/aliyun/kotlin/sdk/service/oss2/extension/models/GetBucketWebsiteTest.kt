package com.aliyun.kotlin.sdk.service.oss2.extension.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GetBucketWebsiteTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = GetBucketWebsiteRequest {}
        assertNull(request.bucket)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.isEmpty()
        }
    }

    @Test
    fun buildRequestWithFullValuesFromDsl() {
        val request = GetBucketWebsiteRequest {
            bucket = "bucket"
        }

        assertEquals("bucket", request.bucket)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.isEmpty()
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = GetBucketWebsiteRequest.Builder()
        builder.bucket = "bucket"

        val request = GetBucketWebsiteRequest(builder)
        assertEquals("bucket", request.bucket)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.isEmpty()
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = GetBucketWebsiteResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.websiteConfiguration)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val configuration = WebsiteConfiguration {
            indexDocument = IndexDocument {
                suffix = "index.html"
                supportSubDir = true
                type = 0
            }
            errorDocument = ErrorDocument {
                key = "error.html"
                httpStatus = 404
            }
            routingRules = RoutingRules {
                routingRules = listOf(
                    RoutingRule {
                        ruleNumber = 1
                        condition = RoutingRuleCondition {
                            includeHeaders = listOf(
                                IncludeHeader {
                                    key = "host"
                                    equals = "test.oss-cn-beijing-internal.aliyuncs.com"
                                    startsWith = ""
                                    endsWith = ""
                                }
                            )
                            keyPrefixEquals = "abc/"
                            keySuffixEquals = "def/"
                            httpErrorCodeReturnedEquals = 404
                        }
                        redirect = RoutingRuleRedirect {
                            redirectType = "Mirror"
                            mirrorUsingRole = true
                            mirrorAsyncStatus = 1
                            mirrorCheckMd5 = false
                            replaceKeyPrefixWith = ""
                            replaceKeyWith = "prefix/\${key}.suffix"
                            mirrorReturnHeaders = MirrorReturnHeaders {
                                returnHeaders = listOf(
                                    ReturnHeader {
                                        key = "key"
                                        value = "value"
                                    }
                                )
                            }
                            protocol = "http"
                            mirrorURLProbe = "mirrorURLProbe"
                            mirrorTaggings = MirrorTaggings {
                                listOf(
                                    Taggings {
                                        key = "t-key"
                                        value = "t-value"
                                    }
                                )
                            }
                            enableReplacePrefix = true
                            mirrorIsExpressTunnel = false
                            mirrorDstVpcId = "mirrorDstVpcId"
                            mirrorDstSlaveVpcId = "mirrorDstSlaveVpcId"
                            mirrorMultiAlternates = MirrorMultiAlternates {
                                mirrorMultiAlternates = listOf(
                                    MirrorMultiAlternate {
                                        mirrorMultiAlternateNumber = 1
                                        mirrorMultiAlternateURL = "mirrorMultiAlternateURL"
                                        mirrorMultiAlternateVpcId = "mirrorMultiAlternateVpcId"
                                        mirrorMultiAlternateDstRegion = "mirrorMultiAlternateDstRegion"
                                    }
                                )
                            }
                            mirrorHeaders = MirrorHeaders {
                                sets = listOf(
                                    MirrorSet {
                                        key = "header-key5"
                                        value = "header-value5"
                                    }
                                )
                                passAll = true
                                passes = listOf(
                                    "header-key1",
                                    "header-key2"
                                )
                                removes = listOf(
                                    "header-key3",
                                    "header-key4"
                                )
                            }
                            mirrorURLSlave = "mirrorURLSlave"
                            mirrorDstRegion = "mirrorDstRegion"
                            mirrorAllowHeadObject = true
                            mirrorUserLastModified = true
                            transparentMirrorResponseCodes = "transparentMirrorResponseCodes"
                            mirrorPassQueryString = true
                            mirrorFollowRedirect = true
                            mirrorProxyPass = true
                            mirrorAllowGetImageInfo = true
                            mirrorAllowVideoSnapshot = true
                            mirrorRole = "mirrorRole"
                            mirrorAuth = MirrorAuth {
                                authType = "authType"
                                region = "region"
                                accessKeyId = "accessKeyId"
                                accessKeySecret = "accessKeySecret"
                            }
                            passQueryString = true
                            mirrorURL = "http://example.com/"
                            mirrorSNI = true
                            mirrorSaveOssMeta = true
                            mirrorSwitchAllErrors = true
                            hostName = "hostName"
                            httpRedirectCode = 1
                            mirrorPassOriginalSlashes = true
                            mirrorTunnelId = "mirrorTunnelId"
                        }
                        luaConfig = RoutingRuleLuaConfig {
                            script = "script"
                        }
                    }
                )
            }
        }
        val result = GetBucketWebsiteResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = configuration
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(configuration, result.websiteConfiguration)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val configuration = WebsiteConfiguration {
            indexDocument = IndexDocument {
                suffix = "index.html"
                supportSubDir = true
                type = 0
            }
            errorDocument = ErrorDocument {
                key = "error.html"
                httpStatus = 404
            }
            routingRules = RoutingRules {
                routingRules = listOf(
                    RoutingRule {
                        ruleNumber = 1
                        condition = RoutingRuleCondition {
                            includeHeaders = listOf(
                                IncludeHeader {
                                    key = "host"
                                    equals = "test.oss-cn-beijing-internal.aliyuncs.com"
                                    startsWith = ""
                                    endsWith = ""
                                }
                            )
                            keyPrefixEquals = "abc/"
                            keySuffixEquals = "def/"
                            httpErrorCodeReturnedEquals = 404
                        }
                        redirect = RoutingRuleRedirect {
                            redirectType = "Mirror"
                            mirrorUsingRole = true
                            mirrorAsyncStatus = 1
                            mirrorCheckMd5 = false
                            replaceKeyPrefixWith = ""
                            replaceKeyWith = "prefix/\${key}.suffix"
                            mirrorReturnHeaders = MirrorReturnHeaders {
                                returnHeaders = listOf(
                                    ReturnHeader {
                                        key = "key"
                                        value = "value"
                                    }
                                )
                            }
                            protocol = "http"
                            mirrorURLProbe = "mirrorURLProbe"
                            mirrorTaggings = MirrorTaggings {
                                listOf(
                                    Taggings {
                                        key = "t-key"
                                        value = "t-value"
                                    }
                                )
                            }
                            enableReplacePrefix = true
                            mirrorIsExpressTunnel = false
                            mirrorDstVpcId = "mirrorDstVpcId"
                            mirrorDstSlaveVpcId = "mirrorDstSlaveVpcId"
                            mirrorMultiAlternates = MirrorMultiAlternates {
                                mirrorMultiAlternates = listOf(
                                    MirrorMultiAlternate {
                                        mirrorMultiAlternateNumber = 1
                                        mirrorMultiAlternateURL = "mirrorMultiAlternateURL"
                                        mirrorMultiAlternateVpcId = "mirrorMultiAlternateVpcId"
                                        mirrorMultiAlternateDstRegion = "mirrorMultiAlternateDstRegion"
                                    }
                                )
                            }
                            mirrorHeaders = MirrorHeaders {
                                sets = listOf(
                                    MirrorSet {
                                        key = "header-key5"
                                        value = "header-value5"
                                    }
                                )
                                passAll = true
                                passes = listOf(
                                    "header-key1",
                                    "header-key2"
                                )
                                removes = listOf(
                                    "header-key3",
                                    "header-key4"
                                )
                            }
                            mirrorURLSlave = "mirrorURLSlave"
                            mirrorDstRegion = "mirrorDstRegion"
                            mirrorAllowHeadObject = true
                            mirrorUserLastModified = true
                            transparentMirrorResponseCodes = "transparentMirrorResponseCodes"
                            mirrorPassQueryString = true
                            mirrorFollowRedirect = true
                            mirrorProxyPass = true
                            mirrorAllowGetImageInfo = true
                            mirrorAllowVideoSnapshot = true
                            mirrorRole = "mirrorRole"
                            mirrorAuth = MirrorAuth {
                                authType = "authType"
                                region = "region"
                                accessKeyId = "accessKeyId"
                                accessKeySecret = "accessKeySecret"
                            }
                            passQueryString = true
                            mirrorURL = "http://example.com/"
                            mirrorSNI = true
                            mirrorSaveOssMeta = true
                            mirrorSwitchAllErrors = true
                            hostName = "hostName"
                            httpRedirectCode = 1
                            mirrorPassOriginalSlashes = true
                            mirrorTunnelId = "mirrorTunnelId"
                        }
                        luaConfig = RoutingRuleLuaConfig {
                            script = "script"
                        }
                    }
                )
            }
        }
        val builder = GetBucketWebsiteResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = configuration

        val result = GetBucketWebsiteResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(configuration, result.websiteConfiguration)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
