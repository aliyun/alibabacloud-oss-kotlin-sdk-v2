package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the GetBucketVersioning operation.
 */
public class GetBucketVersioningResult(builder: Builder) : ResultModel(builder) {

    /**
     * The container that stores the versioning state of the bucket.
     */
    public val versioningConfiguration: VersioningConfiguration?
        get() = innerBody as? VersioningConfiguration

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketVersioningResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): GetBucketVersioningResult {
            return GetBucketVersioningResult(this)
        }
    }
}
