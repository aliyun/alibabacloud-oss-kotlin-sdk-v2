package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The information about the region.
 */
public class RegionInfo(builder: Builder) {
    /**
     * The region ID.
     */
    public val region: String? = builder.region

    /**
     * The public endpoint of the region.
     */
    public val internetEndpoint: String? = builder.internetEndpoint

    /**
     * The internal endpoint of the region.
     */
    public val internalEndpoint: String? = builder.internalEndpoint

    /**
     * The acceleration endpoint of the region. The value is always oss-accelerate.aliyuncs.com.
     */
    public val accelerateEndpoint: String? = builder.accelerateEndpoint

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): RegionInfo =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The region ID.
         */
        public var region: String? = null

        /**
         * The public endpoint of the region.
         */
        public var internetEndpoint: String? = null

        /**
         * The internal endpoint of the region.
         */
        public var internalEndpoint: String? = null

        /**
         * The acceleration endpoint of the region. The value is always oss-accelerate.aliyuncs.com.
         */
        public var accelerateEndpoint: String? = null

        public fun build(): RegionInfo {
            return RegionInfo(this)
        }
    }
}
