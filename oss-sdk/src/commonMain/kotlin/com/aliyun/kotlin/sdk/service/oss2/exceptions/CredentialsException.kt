package com.aliyun.kotlin.sdk.service.oss2.exceptions

public class CredentialsException(
    message: String? = null,
    throwable: Throwable? = null
) : RuntimeException(message, throwable)
