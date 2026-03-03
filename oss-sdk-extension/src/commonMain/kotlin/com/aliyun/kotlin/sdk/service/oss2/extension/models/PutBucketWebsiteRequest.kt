package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the PutBucketWebsite operation.
 */
public class PutBucketWebsiteRequest(builder: Builder): RequestModel(builder) {
    
    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket
    
    /**
     * The request body schema.
     */
    public var websiteConfiguration: WebsiteConfiguration? = builder.websiteConfiguration
    

    public inline fun copy(block: Builder.() -> Unit = {}): PutBucketWebsiteRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketWebsiteRequest =
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
        public var websiteConfiguration: WebsiteConfiguration? = null
    
        
        public fun build(): PutBucketWebsiteRequest {
            return PutBucketWebsiteRequest(this)
        }

        public constructor(from: PutBucketWebsiteRequest): this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters) 
            this.bucket = from.bucket 
            this.websiteConfiguration = from.websiteConfiguration 
        }             
    }

}
