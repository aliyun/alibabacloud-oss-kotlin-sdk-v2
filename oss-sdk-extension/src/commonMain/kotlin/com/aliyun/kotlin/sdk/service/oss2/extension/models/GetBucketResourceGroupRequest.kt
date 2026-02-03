package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the GetBucketResourceGroup operation.
 */
public class GetBucketResourceGroupRequest(builder: Builder): RequestModel(builder) {
    
    /**
     * The name of the bucket that you want to query.
     */
    public val bucket: String? = builder.bucket
    

    public inline fun copy(block: Builder.() -> Unit = {}): GetBucketResourceGroupRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketResourceGroupRequest =
            Builder().apply(builder).build()
    }

    public class Builder(): RequestModel.Builder() {
    
        /**
        * The name of the bucket that you want to query.
        */
        public var bucket: String? = null
    
        
        public fun build(): GetBucketResourceGroupRequest {
            return GetBucketResourceGroupRequest(this)
        }

        public constructor(from: GetBucketResourceGroupRequest): this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters) 
            this.bucket = from.bucket 
        }             
    }

}
