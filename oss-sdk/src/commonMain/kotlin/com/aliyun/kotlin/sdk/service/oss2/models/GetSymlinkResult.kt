package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the GetSymlink operation.
 */
public class GetSymlinkResult(builder: Builder) : ResultModel(builder) {

    /**
     * Indicates the target object that the symbol link directs to.
     */
    public val symlinkTarget: String?
        get() = headers["x-oss-symlink-target"]

    /**
     * Version of the object.
     */
    public val versionId: String?
        get() = headers["x-oss-version-id"]

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetSymlinkResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): GetSymlinkResult {
            return GetSymlinkResult(this)
        }
    }
}
