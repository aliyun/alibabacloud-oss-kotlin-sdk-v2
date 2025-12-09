package com.aliyun.kotlin.sdk.service.oss2.types

public interface Abortable {
    /**
     * Aborts the execution of the task. Multiple calls to abort or calling abort an already aborted task
     * should return without error.
     */
    public fun abort()
}
