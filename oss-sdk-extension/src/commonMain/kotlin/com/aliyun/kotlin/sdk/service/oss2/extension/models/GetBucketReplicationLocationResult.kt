package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the GetBucketReplicationLocation operation.
 */
public class GetBucketReplicationLocationResult(builder: Builder): ResultModel(builder) { 

    /**
     * The container that stores the region in which the destination bucket can be located.
     */
    public val replicationLocation: ReplicationLocation?
        get() = innerBody as? ReplicationLocation
     

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketReplicationLocationResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): GetBucketReplicationLocationResult {
            return GetBucketReplicationLocationResult(this)
        }
    }
}
