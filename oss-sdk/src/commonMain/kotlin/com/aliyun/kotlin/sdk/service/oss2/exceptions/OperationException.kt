package com.aliyun.kotlin.sdk.service.oss2.exceptions

public class OperationException(
    public val opName: String,
    cause: Throwable? = null
) : RuntimeException(
    "Operation $opName raised an exception:\n$cause",
    cause
) {

    public fun <T> contains(clz: Class<T?>?): Throwable? {
        var next = this.cause
        do {
            if (next == null) {
                break
            }
            if (next.javaClass == clz) {
                break
            }
            next = next.cause
        } while (true)
        return next
    }
}
