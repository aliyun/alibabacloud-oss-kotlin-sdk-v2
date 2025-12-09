package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the PutObjectAcl operation.
 */
public class PutObjectAclResult(builder: Builder) : ResultModel(builder) {

    /**
     * The version ID of the object.
     */
    public val versionId: String?
        get() = headers["x-oss-version-id"]

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutObjectAclResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): PutObjectAclResult {
            return PutObjectAclResult(this)
        }
    }
}
