package com.aliyun.kotlin.sdk.service.oss2.exceptions

/**
 * Request Error public class that encapsulates exceptions occurring during request execution
 */
public class RequestException : RuntimeException {
    public constructor(msg: String?) : super(msg)

    public constructor(msg: String?, cause: Throwable?) : super(msg, cause)
}
