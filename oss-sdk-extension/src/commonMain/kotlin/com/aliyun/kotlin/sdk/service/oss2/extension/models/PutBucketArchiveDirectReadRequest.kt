package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the PutBucketArchiveDirectRead operation.
 */
public class PutBucketArchiveDirectReadRequest(builder: Builder): RequestModel(builder) {
    
    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket
    
    /**
     * The request body.
     */
    public var archiveDirectReadConfiguration: ArchiveDirectReadConfiguration? = builder.archiveDirectReadConfiguration
    

    public inline fun copy(block: Builder.() -> Unit = {}): PutBucketArchiveDirectReadRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketArchiveDirectReadRequest =
            Builder().apply(builder).build()
    }

    public class Builder(): RequestModel.Builder() {
    
        /**
        * The name of the bucket.
        */
        public var bucket: String? = null
    
        /**
        * The request body.
        */
        public var archiveDirectReadConfiguration: ArchiveDirectReadConfiguration? = null
    
        
        public fun build(): PutBucketArchiveDirectReadRequest {
            return PutBucketArchiveDirectReadRequest(this)
        }

        public constructor(from: PutBucketArchiveDirectReadRequest): this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters) 
            this.bucket = from.bucket 
            this.archiveDirectReadConfiguration = from.archiveDirectReadConfiguration 
        }             
    }

}
