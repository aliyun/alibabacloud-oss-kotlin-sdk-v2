package com.aliyun.kotlin.sdk.service.oss2.types

import com.aliyun.kotlin.sdk.service.oss2.OperationInput

/**
 * Resolves a logical bucket name (e.g. a prefix) into the actual bucket name used for signing.
 */
public interface BucketNameResolver {
    public fun buildBucketName(input: OperationInput): String
}
