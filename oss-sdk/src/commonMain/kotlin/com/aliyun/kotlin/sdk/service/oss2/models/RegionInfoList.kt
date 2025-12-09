package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The information about the regions.
 */
public class RegionInfoList(builder: Builder) {

    /**
     * The information about the regions.
     */
    public val regionInfos: List<RegionInfo>? = builder.regionInfos

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): RegionInfoList =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The information about the regions.
         */
        public var regionInfos: List<RegionInfo>? = null

        public fun build(): RegionInfoList {
            return RegionInfoList(this)
        }
    }
}
