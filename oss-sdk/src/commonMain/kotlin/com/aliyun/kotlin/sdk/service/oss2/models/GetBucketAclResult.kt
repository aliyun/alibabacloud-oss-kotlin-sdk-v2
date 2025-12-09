package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the GetBucketAcl operation.
 */
public class GetBucketAclResult(builder: Builder) : ResultModel(builder) {

    /**
     * The container that stores the ACL information.
     */
    public val accessControlPolicy: AccessControlPolicy?
        get() = innerBody as? AccessControlPolicy

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketAclResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): GetBucketAclResult {
            return GetBucketAclResult(this)
        }
    }
}
