package com.aliyun.kotlin.sdk.service.oss2.types

import com.aliyun.kotlin.sdk.service.oss2.OperationInput

/**
 * Builds the full request URL (scheme://host/path, without query) for an operation.
 */
public interface EndpointProvider {
    public fun buildURL(input: OperationInput): String
}
