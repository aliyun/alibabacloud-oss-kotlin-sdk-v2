package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the PutSymlink operation.
 */
public class PutSymlinkResult(builder: Builder) : ResultModel(builder) {

    /**
     * Version of the object.
     */
    public val versionId: String?
        get() = headers["x-oss-version-id"]

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutSymlinkResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): PutSymlinkResult {
            return PutSymlinkResult(this)
        }
    }
}
