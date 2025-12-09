package com.aliyun.kotlin.sdk.service.oss2.exceptions

public class SerializationException(
    message: String,
    throwable: Throwable? = null
) : RuntimeException(
    "Serialization raised an exception: $message",
    throwable
)
