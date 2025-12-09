package com.aliyun.kotlin.sdk.service.oss2.exceptions

/**
 * Credential Fetch Error public class
 */
public class CredentialsFetchException(
    throwable: Throwable
) : RuntimeException(
    "Fetch Credentials raised an exception: $throwable",
    throwable
)
