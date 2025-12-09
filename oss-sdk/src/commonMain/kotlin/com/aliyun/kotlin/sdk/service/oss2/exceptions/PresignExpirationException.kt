package com.aliyun.kotlin.sdk.service.oss2.exceptions

public class PresignExpirationException() : RuntimeException(
    "Expires should be not greater than 604800 s (seven days)"
)
