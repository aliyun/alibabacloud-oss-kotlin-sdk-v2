package com.aliyun.kotlin.sdk.service.oss2.exceptions

public class NonRetryableTimeoutException : RuntimeException {
    public constructor(msg: String?) : super(msg)

    public constructor(msg: String?, cause: Throwable?) : super(msg, cause)
}
