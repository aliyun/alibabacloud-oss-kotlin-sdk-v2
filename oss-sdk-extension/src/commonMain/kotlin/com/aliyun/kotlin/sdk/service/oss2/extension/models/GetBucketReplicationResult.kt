package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the GetBucketReplication operation.
 */
public class GetBucketReplicationResult(builder: Builder): ResultModel(builder) { 

    /**
     * The container that stores data replication configurations.
     */
    public val replicationConfiguration: ReplicationConfiguration?
        get() = innerBody as? ReplicationConfiguration
     

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketReplicationResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): GetBucketReplicationResult {
            return GetBucketReplicationResult(this)
        }
    }
}
