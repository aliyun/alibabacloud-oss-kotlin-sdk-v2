package com.aliyun.kotlin.sdk.service.oss2.extension.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PutBucketWebsiteTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = PutBucketWebsiteRequest {}
        assertNull(request.bucket)
        assertNull(request.websiteConfiguration)

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
        val request = PutBucketWebsiteRequest {
            bucket = "bucket"
            this.websiteConfiguration = configuration
        }

        assertEquals("bucket", request.bucket)
        assertEquals(configuration, request.websiteConfiguration)

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
        val builder = PutBucketWebsiteRequest.Builder()
        builder.bucket = "bucket"
        builder.websiteConfiguration = configuration

        val request = PutBucketWebsiteRequest(builder)
        assertEquals("bucket", request.bucket)
        assertEquals(configuration, request.websiteConfiguration)

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
        val result = PutBucketWebsiteResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = PutBucketWebsiteResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = null
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = PutBucketWebsiteResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")

        val result = PutBucketWebsiteResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
