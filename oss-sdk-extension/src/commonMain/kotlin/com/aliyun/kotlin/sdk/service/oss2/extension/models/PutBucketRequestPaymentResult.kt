package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the PutBucketRequestPayment operation.
 */
public class PutBucketRequestPaymentResult(builder: Builder): ResultModel(builder) { 

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketRequestPaymentResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): PutBucketRequestPaymentResult {
            return PutBucketRequestPaymentResult(this)
        }
    }
}
