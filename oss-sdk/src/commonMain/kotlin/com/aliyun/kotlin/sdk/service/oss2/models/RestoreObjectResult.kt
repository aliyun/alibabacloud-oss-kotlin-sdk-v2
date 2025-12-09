package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the RestoreObject operation.
 */
public class RestoreObjectResult(builder: Builder) : ResultModel(builder) {

    /**
     * The restoration priority.
     * This header is displayed only for the Cold Archive or Deep Cold Archive object in the restored state.
     */
    public val objectRestorePriority: String?
        get() = headers["x-oss-object-restore-priority"]

    /**
     * Version of the object.
     */
    public val versionId: String?
        get() = headers["x-oss-version-id"]

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): RestoreObjectResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): RestoreObjectResult {
            return RestoreObjectResult(this)
        }
    }
}
