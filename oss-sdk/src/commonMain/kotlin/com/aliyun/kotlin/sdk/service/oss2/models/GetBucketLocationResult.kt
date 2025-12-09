package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the GetBucketLocation operation.
 */
public class GetBucketLocationResult(builder: Builder) : ResultModel(builder) {

    /**
     * The region in which the bucket resides.Examples: oss-cn-hangzhou, oss-cn-shanghai, oss-cn-qingdao, oss-cn-beijing, oss-cn-zhangjiakou, oss-cn-hongkong, oss-cn-shenzhen, oss-us-west-1, oss-us-east-1, and oss-ap-southeast-1.
     * For more information about the regions in which buckets reside, see [Regions and endpoints](~~31837~~).
     */
    public val locationConstraint: String?
        get() = innerBody as? String

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketLocationResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): GetBucketLocationResult {
            return GetBucketLocationResult(this)
        }
    }
}
