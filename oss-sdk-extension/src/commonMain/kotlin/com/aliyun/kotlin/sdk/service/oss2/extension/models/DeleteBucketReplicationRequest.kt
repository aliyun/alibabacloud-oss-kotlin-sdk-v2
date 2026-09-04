package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the DeleteBucketReplication operation.
 */
public class DeleteBucketReplicationRequest(builder: Builder): RequestModel(builder) {
    
    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket
    
    /**
     * The container of the request body.
     */
    public var replicationRules: ReplicationRules? = builder.replicationRules
    

    public inline fun copy(block: Builder.() -> Unit = {}): DeleteBucketReplicationRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DeleteBucketReplicationRequest =
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
        public var replicationRules: ReplicationRules? = null
    
        
        public fun build(): DeleteBucketReplicationRequest {
            return DeleteBucketReplicationRequest(this)
        }

        public constructor(from: DeleteBucketReplicationRequest): this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters) 
            this.bucket = from.bucket 
            this.replicationRules = from.replicationRules 
        }             
    }

}
