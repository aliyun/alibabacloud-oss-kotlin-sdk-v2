package com.aliyun.kotlin.sdk.service.oss2.exceptions

import kotlin.reflect.KClass

public class OperationException(
    public val opName: String,
    cause: Throwable? = null
) : RuntimeException(
    "Operation $opName raised an exception:\n$cause",
    cause
) {

    public fun contains(clz: KClass<out Throwable>): Throwable? {
        var next = this.cause
        while (next != null) {
            if (next::class == clz) {
                break
            }
            next = next.cause
        }
        return next
    }
}
