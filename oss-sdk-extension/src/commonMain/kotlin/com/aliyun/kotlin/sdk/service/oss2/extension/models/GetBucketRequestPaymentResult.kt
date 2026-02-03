package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the GetBucketRequestPayment operation.
 */
public class GetBucketRequestPaymentResult(builder: Builder): ResultModel(builder) { 

    /**
     * Indicates the container for the payer.
     */
    public val requestPaymentConfiguration: RequestPaymentConfiguration?
        get() = innerBody as? RequestPaymentConfiguration
     

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketRequestPaymentResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): GetBucketRequestPaymentResult {
            return GetBucketRequestPaymentResult(this)
        }
    }
}
