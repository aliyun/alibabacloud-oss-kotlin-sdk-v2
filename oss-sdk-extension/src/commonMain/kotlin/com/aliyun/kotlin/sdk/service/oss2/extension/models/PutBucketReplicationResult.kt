package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the PutBucketReplication operation.
 */
public class PutBucketReplicationResult(builder: Builder): ResultModel(builder) { 

    /**
     * <no value>
     */
    public val replicationRuleId: String?
        get() = headers["x-oss-replication-rule-id"]
     

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketReplicationResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): PutBucketReplicationResult {
            return PutBucketReplicationResult(this)
        }
    }
}
