package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the AppendObject operation.
 */
public class AppendObjectResult(builder: Builder) : ResultModel(builder) {

    /**
     * The position that must be provided in the next request.
     */
    public val nextAppendPosition: Long?
        get() = headers["x-oss-next-append-position"]?.toLong()

    /**
     * The 64-bit CRC value of the object.
     */
    public val hashCrc64ecma: String?
        get() = headers["x-oss-hash-crc64ecma"]

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): AppendObjectResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): AppendObjectResult {
            return AppendObjectResult(this)
        }
    }
}
