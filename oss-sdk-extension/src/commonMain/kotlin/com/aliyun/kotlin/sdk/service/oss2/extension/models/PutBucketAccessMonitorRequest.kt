package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the PutBucketAccessMonitor operation.
 */
public class PutBucketAccessMonitorRequest(builder: Builder): RequestModel(builder) {
    
    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket
    
    /**
     * The request body schema.
     */
    public var accessMonitorConfiguration: AccessMonitorConfiguration? = builder.accessMonitorConfiguration
    

    public inline fun copy(block: Builder.() -> Unit = {}): PutBucketAccessMonitorRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketAccessMonitorRequest =
            Builder().apply(builder).build()
    }

    public class Builder(): RequestModel.Builder() {
    
        /**
        * The name of the bucket.
        */
        public var bucket: String? = null
    
        /**
        * The request body schema.
        */
        public var accessMonitorConfiguration: AccessMonitorConfiguration? = null
    
        
        public fun build(): PutBucketAccessMonitorRequest {
            return PutBucketAccessMonitorRequest(this)
        }

        public constructor(from: PutBucketAccessMonitorRequest): this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters) 
            this.bucket = from.bucket 
            this.accessMonitorConfiguration = from.accessMonitorConfiguration 
        }             
    }

}
