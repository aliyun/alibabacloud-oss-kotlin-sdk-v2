package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the PutBucketAcl operation.
 */
public class PutBucketAclResult(builder: Builder) : ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketAclResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): PutBucketAclResult {
            return PutBucketAclResult(this)
        }
    }
}
