package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the GetBucketReplicationProgress operation.
 */
public class GetBucketReplicationProgressRequest(builder: Builder): RequestModel(builder) {
    
    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket
    
    /**
     * The ID of the data replication rule. You can call the GetBucketReplication operation to query the ID.
     */
    public val ruleId: String?
        get() = parameters["rule-id"]
    

    public inline fun copy(block: Builder.() -> Unit = {}): GetBucketReplicationProgressRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketReplicationProgressRequest =
            Builder().apply(builder).build()
    }

    public class Builder(): RequestModel.Builder() {
    
        /**
        * The name of the bucket.
        */
        public var bucket: String? = null
    
        /**
        * The ID of the data replication rule. You can call the GetBucketReplication operation to query the ID.
        */
        public var ruleId: String?
            set(value) {this.parameters["rule-id"] = requireNotNull(value)}
            get() = parameters["rule-id"]
    
        
        public fun build(): GetBucketReplicationProgressRequest {
            return GetBucketReplicationProgressRequest(this)
        }

        public constructor(from: GetBucketReplicationProgressRequest): this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters) 
            this.bucket = from.bucket 
        }             
    }

}
