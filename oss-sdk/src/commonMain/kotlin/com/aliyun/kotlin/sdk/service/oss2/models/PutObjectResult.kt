package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the PutObject operation.
 */
public class PutObjectResult(builder: Builder) : ResultModel(builder) {

    /**
     * The 64-bit CRC value of the object.
     * This value is calculated based on the ECMA-182 standard.
     */
    public val hashCrc64ecma: Long?
        get() = headers["x-oss-hash-crc64ecma"]?.toLong()

    /**
     * Version of the object.
     */
    public val versionId: String?
        get() = headers["x-oss-version-id"]

    /**
     * The ETag that is generated when an object is created.
     */
    public val eTag: String?
        get() = headers["ETag"]

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutObjectResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): PutObjectResult {
            return PutObjectResult(this)
        }
    }
}
