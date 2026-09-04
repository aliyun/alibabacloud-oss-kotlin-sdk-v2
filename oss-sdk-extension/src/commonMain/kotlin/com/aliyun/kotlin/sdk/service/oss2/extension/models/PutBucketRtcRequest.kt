package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the PutBucketRtc operation.
 */
public class PutBucketRtcRequest(builder: Builder): RequestModel(builder) {
    
    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket
    
    /**
     * The container of the request body.
     */
    public var rtcConfiguration: RtcConfiguration? = builder.rtcConfiguration
    

    public inline fun copy(block: Builder.() -> Unit = {}): PutBucketRtcRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketRtcRequest =
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
        public var rtcConfiguration: RtcConfiguration? = null
    
        
        public fun build(): PutBucketRtcRequest {
            return PutBucketRtcRequest(this)
        }

        public constructor(from: PutBucketRtcRequest): this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters) 
            this.bucket = from.bucket 
            this.rtcConfiguration = from.rtcConfiguration 
        }             
    }

}
