package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the PutBucketReplication operation.
 */
public class PutBucketReplicationRequest(builder: Builder): RequestModel(builder) {
    
    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket
    
    /**
     * The container of the request body.
     */
    public var replicationConfiguration: ReplicationConfiguration? = builder.replicationConfiguration
    

    public inline fun copy(block: Builder.() -> Unit = {}): PutBucketReplicationRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketReplicationRequest =
            Builder().apply(builder).build()
    }

    public class Builder(): RequestModel.Builder() {
    
        /**
        * The name of the bucket.
        */
        public var bucket: String? = null
    
        /**
        * The container of the request body.
        */
        public var replicationConfiguration: ReplicationConfiguration? = null
    
        
        public fun build(): PutBucketReplicationRequest {
            return PutBucketReplicationRequest(this)
        }

        public constructor(from: PutBucketReplicationRequest): this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters) 
            this.bucket = from.bucket 
            this.replicationConfiguration = from.replicationConfiguration 
        }             
    }

}
