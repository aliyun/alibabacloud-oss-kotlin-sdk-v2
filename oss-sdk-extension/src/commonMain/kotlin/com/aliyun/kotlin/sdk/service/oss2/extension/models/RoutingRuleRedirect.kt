package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The operation to perform after the rule is matched.  This parameter must be specified if RoutingRule is specified.
 */
@Serializable
@SerialName("RoutingRuleRedirect")
public data class RoutingRuleRedirect(
    /**
     * The redirection type. Valid values:
     * *   **Mirror**: mirroring-based back-to-origin.
     * *   **External**: external redirection. OSS returns an HTTP 3xx status code and returns an address for you to redirect to.
     * *   **AliCDN**: redirection based on Alibaba Cloud CDN. Compared with external redirection, OSS adds an additional header to the request. After Alibaba Cloud CDN identifies the header, Alibaba Cloud CDN redirects the access to the specified address and returns the obtained data instead of the HTTP 3xx status code that redirects the access to another address.
     * This parameter must be specified if Redirect is specified.
     */
    @XmlElement("RedirectType") public var redirectType: String? = null,

    /**
     * Whether to use role for mirror-based back-to-origin.
     */
    @XmlElement("MirrorUsingRole") public var mirrorUsingRole: Boolean? = null,

    /**
     * The HTTP status codes that trigger the asynchronous pull mode in mirror-based back-to-origin.
     */
    @XmlElement("MirrorAsyncStatus") public var mirrorAsyncStatus: Long? = null,

    /**
     * Specifies whether to check the MD5 hash of the body of the response returned by the origin. This parameter takes effect only when the value of RedirectType is Mirror.
     * When **MirrorCheckMd5** is set to true and the response returned by the origin includes the Content-Md5 header, OSS checks whether the MD5 hash of the obtained data matches the header value.
     * If the MD5 hash of the obtained data does not match the header value, the obtained data is not stored in OSS. Default value: false.
     */
    @XmlElement("MirrorCheckMd5") public var mirrorCheckMd5: Boolean? = null,

    /**
     * The string that is used to replace the prefix of the object name during redirection. If the prefix of an object name is empty, the string precedes the object name.
     * You can specify only one of the ReplaceKeyWith and ReplaceKeyPrefixWith parameters in a rule. For example, if you access an object named abc/test.txt, KeyPrefixEquals is set to abc/, ReplaceKeyPrefixWith is set to def/, the value of the Location header is `http://example.com/def/test.txt`.
     */
    @XmlElement("ReplaceKeyPrefixWith") public var replaceKeyPrefixWith: String? = null,

    /**
     * The string that is used to replace the requested object name when the request is redirected.
     * This parameter can be set to the ${key} variable, which indicates the object name in the request. For example, if ReplaceKeyWith is set to `prefix/${key}.suffix` and the object to access is test, the value of the Location header is `http://example.com/prefix/test.suffix`.
     */
    @XmlElement("ReplaceKeyWith") public var replaceKeyWith: String? = null,

    /**
     * Container to store the rules for setting response headers in mirror-based back-to-origin.
     */
    @XmlElement("MirrorReturnHeaders") public var mirrorReturnHeaders: MirrorReturnHeaders? = null,

    /**
     * The protocol used for redirection. This parameter takes effect only when RedirectType is set to External or AliCDN.
     * For example, if you access an object named test, Protocol is set to https, and Hostname is set to `example.com`, the value of the Location header is `https://example.com/test`. Valid values: **http** and **https**.
     */
    @XmlElement("Protocol") public var protocol: String? = null,

    /**
     * The probe URL for mirror-based back-to-origin.
     */
    @XmlElement("MirrorURLProbe") public var mirrorURLProbe: String? = null,

    /**
     * The rules for setting tags when saving files during mirror-based back-to-origin.
     */
    @XmlElement("MirrorTaggings") public var mirrorTaggings: MirrorTaggings? = null,

    /**
     * If this parameter is set to true, the prefix of the object names is replaced with the value specified by ReplaceKeyPrefixWith. If this parameter is not specified or empty, the prefix of object names is truncated.
     * When the ReplaceKeyWith parameter is not empty, the EnableReplacePrefix parameter cannot be set to true.Default value: false.
     */
    @XmlElement("EnableReplacePrefix") public var enableReplacePrefix: Boolean? = null,

    /**
     * Mirror-based back-to-origin with express tunnel.
     */
    @XmlElement("MirrorIsExpressTunnel") public var mirrorIsExpressTunnel: Boolean? = null,

    /**
     * The VPC ID for mirror-based back-to-origin express tunnel.
     */
    @XmlElement("MirrorDstVpcId") public var mirrorDstVpcId: String? = null,

    /**
     * The slave VPC ID for mirror-based back-to-origin express tunnel.
     */
    @XmlElement("MirrorDstSlaveVpcId") public var mirrorDstSlaveVpcId: String? = null,

    /**
     * The container to store the configuration for multiple origins in mirror-based back-to-origin.
     */
    @XmlElement("MirrorMultiAlternates") public var mirrorMultiAlternates: MirrorMultiAlternates? = null,

    /**
     * The headers contained in the response that is returned when you use mirroring-based back-to-origin. This parameter takes effect only when the value of RedirectType is Mirror.
     */
    @XmlElement("MirrorHeaders") public var mirrorHeaders: MirrorHeaders? = null,

    /**
     * The slave URL for mirror-based back-to-origin.
     */
    @XmlElement("MirrorURLSlave") public var mirrorURLSlave: String? = null,

    /**
     * The VPC region for mirror-based back-to-origin express tunnel.
     */
    @XmlElement("MirrorDstRegion") public var mirrorDstRegion: String? = null,

    /**
     * Whether to allow take HeadObject in mirror-based back-to-origin.
     */
    @XmlElement("MirrorAllowHeadObject") public var mirrorAllowHeadObject: Boolean? = null,

    /**
     * Use LastModifiedTime of the file from origin.
     */
    @XmlElement("MirrorUserLastModified") public var mirrorUserLastModified: Boolean? = null,

    /**
     * Specify which status codes returned by the origin server should be passed through to the client along with the body.
     * The value should be HTTP status codes such as 4xx, 5xx, etc., separated by commas (,), for example, 400,404. This setting takes effect only when RedirectType is set to Mirror. When OSS requests content from the origin server, if the origin server returns one of the status codes specified in this parameter,
     * OSS will pass through the status code and body returned by the origin server to the client. If the 404 status code is specified in this parameter, the configured ErrorDocument will be ineffective.
     */
    @XmlElement("TransparentMirrorResponseCodes") public var transparentMirrorResponseCodes: String? = null,

    /**
     * This parameter plays the same role as PassQueryString and has a higher priority than PassQueryString. This parameter takes effect only when the value of RedirectType is Mirror. Default value: false.Valid values:
     * *   true
     * *   false
     */
    @XmlElement("MirrorPassQueryString") public var mirrorPassQueryString: Boolean? = null,

    /**
     * Specifies whether to redirect the access to the address specified by Location if the origin returns an HTTP 3xx status code. This parameter takes effect only when the value of RedirectType is Mirror.
     * For example, when a mirroring-based back-to-origin request is initiated, the origin returns 302 and Location is specified.
     * *   If you set MirrorFollowRedirect to true, OSS continues requesting the resource at the address specified by Location. The access can be redirected up to 10 times. If the access is redirected more than 10 times, the mirroring-based back-to-origin request fails.
     * *   If you set MirrorFollowRedirect to false, OSS returns 302 and passes through Location.Default value: true.
     */
    @XmlElement("MirrorFollowRedirect") public var mirrorFollowRedirect: Boolean? = null,

    /**
     * Not save data in web-based back-to-origin.
     */
    @XmlElement("MirrorProxyPass") public var mirrorProxyPass: Boolean? = null,

    /**
     * Whether to allow get image information in mirror-based back-to-origin.
     */
    @XmlElement("MirrorAllowGetImageInfo") public var mirrorAllowGetImageInfo: Boolean? = null,

    /**
     * Whether to allow take video snapshot in mirror-based back-to-origin.
     */
    @XmlElement("MirrorAllowVideoSnapshot") public var mirrorAllowVideoSnapshot: Boolean? = null,

    /**
     * The role name used for mirror-based back-to-origin.
     */
    @XmlElement("MirrorRole") public var mirrorRole: String? = null,

    /**
     * The authentication information for the origin server in mirror-based back-to-origin.
     */
    @XmlElement("MirrorAuth") public var mirrorAuth: MirrorAuth? = null,

    /**
     * Specifies whether to include parameters of the original request in the redirection request when the system runs the redirection rule or mirroring-based back-to-origin rule.
     * For example, if the **PassQueryString** parameter is set to true, the `?a=b&c=d` parameter string is included in a request sent to OSS, and the redirection mode is 302, this parameter is added to the Location header.
     * For example, if the request is `Location:example.com?a=b&c=d` and the redirection type is mirroring-based back-to-origin, the ?a=b\&c=d parameter string is also included in the back-to-origin request.
     * Valid values: true and false (default).
     */
    @XmlElement("PassQueryString") public var passQueryString: Boolean? = null,

    /**
     * The origin URL for mirroring-based back-to-origin. This parameter takes effect only when the value of RedirectType is Mirror.
     * The origin URL must start with **http:// ** or **https:// ** and end with a forward slash (/). OSS adds an object name to the end of the URL to generate a back-to-origin URL. For example, the name of the object to access is my object.
     * If MirrorURL is set to `http://example.com/`, the back-to-origin URL is `http://example.com/myobject`.
     * If MirrorURL is set to `http://example.com/dir1/`, the back-to-origin URL is `http://example.com/dir1/myobject`.
     * This parameter must be specified if RedirectType is set to Mirror.Valid values:
     * *   true
     * *   false
     */
    @XmlElement("MirrorURL") public var mirrorURL: String? = null,

    /**
     * 是否透传SNI
     */
    @XmlElement("MirrorSNI") public var mirrorSNI: Boolean? = null,

    /**
     * Whether to store the user defined metadata in mirror-based back-to-origin.
     */
    @XmlElement("MirrorSaveOssMeta") public var mirrorSaveOssMeta: Boolean? = null,

    /**
     * Used for determining the state of primary-secondary switching. The logic for primary-secondary switching is based on the error code returned by the origin server.
     * If MirrorSwitchAllErrors is set to true, all status codes except the following are considered failures: 200, 206, 301, 302, 303, 307, 404. If it is set to false, only status codes in the 5xx range or timeouts are considered failures.
     */
    @XmlElement("MirrorSwitchAllErrors") public var mirrorSwitchAllErrors: Boolean? = null,

    /**
     * The domain name used for redirection. The domain name must comply with the domain naming rules.
     * For example, if you access an object named test, Protocol is set to https, and Hostname is set to `example.com`, the value of the Location header is `https://example.com/test`.
     */
    @XmlElement("HostName") public var hostName: String? = null,

    /**
     * The HTTP redirect code in the response. This parameter takes effect only when RedirectType is set to External or AliCDN. Valid values: 301, 302, and 307.
     */
    @XmlElement("HttpRedirectCode") public var httpRedirectCode: Long? = null,

    /**
     * Whether to pass slashes to origin.
     */
    @XmlElement("MirrorPassOriginalSlashes") public var mirrorPassOriginalSlashes: Boolean? = null,

    /**
     * The tunnel ID for mirror-based back-to-origin.
     */
    @XmlElement("MirrorTunnelId") public var mirrorTunnelId: String? = null
) {
    public companion object {
        public operator fun invoke(builder: RoutingRuleRedirect.() -> Unit): RoutingRuleRedirect =
            RoutingRuleRedirect().apply(builder)
    }
}
