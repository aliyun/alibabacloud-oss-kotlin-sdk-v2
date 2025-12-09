package com.aliyun.kotlin.sdk.service.oss2.exceptions

/**
 * Response Error public public class that encapsulates exceptions occurring during response processing
 */
public class ResponseException : RuntimeException {
    public constructor(msg: String?) : super(msg)

    public constructor(msg: String?, cause: Throwable?) : super(msg, cause)
}
