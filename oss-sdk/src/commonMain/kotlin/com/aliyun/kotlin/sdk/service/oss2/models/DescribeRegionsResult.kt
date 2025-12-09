package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the DescribeRegions operation.
 */
public class DescribeRegionsResult(builder: Builder) : ResultModel(builder) {

    /**
     * The information about the regions.
     */
    public val regionInfoList: RegionInfoList?
        get() = innerBody as? RegionInfoList

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DescribeRegionsResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): DescribeRegionsResult {
            return DescribeRegionsResult(this)
        }
    }
}
