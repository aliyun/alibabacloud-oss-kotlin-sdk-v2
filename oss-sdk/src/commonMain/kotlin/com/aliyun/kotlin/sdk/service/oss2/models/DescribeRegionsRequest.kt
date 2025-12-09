package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the DescribeRegions operation.
 */
public class DescribeRegionsRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The region ID of the request.
     */
    public val regions: String?
        get() = parameters["regions"]

    public inline fun copy(block: Builder.() -> Unit = {}): DescribeRegionsRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DescribeRegionsRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The region ID of the request.
         */
        public var regions: String?
            set(value) {
                value?.let { this.parameters["regions"] = it }
            }
            get() = parameters["regions"]

        public fun build(): DescribeRegionsRequest {
            return DescribeRegionsRequest(this)
        }

        public constructor(from: DescribeRegionsRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
        }
    }
}
