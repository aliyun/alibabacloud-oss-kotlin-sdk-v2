package com.aliyun.kotlin.sdk.service.oss2.exceptions

public class DeserializationException(
    message: String,
    throwable: Throwable? = null
) : RuntimeException(
    "Deserialization raised an exception: $message",
    throwable
)
