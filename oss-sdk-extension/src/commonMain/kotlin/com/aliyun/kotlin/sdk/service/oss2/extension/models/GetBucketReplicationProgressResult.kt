package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the GetBucketReplicationProgress operation.
 */
public class GetBucketReplicationProgressResult(builder: Builder): ResultModel(builder) { 

    /**
     * The container that is used to store the progress of data replication tasks.
     */
    public val replicationProgress: ReplicationProgress?
        get() = innerBody as? ReplicationProgress
     

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketReplicationProgressResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): GetBucketReplicationProgressResult {
            return GetBucketReplicationProgressResult(this)
        }
    }
}
