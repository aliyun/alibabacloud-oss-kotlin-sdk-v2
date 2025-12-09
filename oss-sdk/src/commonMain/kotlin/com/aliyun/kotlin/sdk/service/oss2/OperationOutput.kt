package com.aliyun.kotlin.sdk.service.oss2

import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.types.MutableAttributes
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils

public class OperationOutput(builder: Builder) {
    public val status: String = builder.status
    public val statusCode: Int = builder.statusCode
    public val headers: MutableMap<String, String> = builder.headers ?: MapUtils.headersMap()

    public val body: ByteStream? = builder.body

    public val input: OperationInput? = builder.input
    public val opMetadata: MutableAttributes? = builder.opMetadata

    public companion object {
        /**
         * Builds a [OperationOutput] instance with the given [builder] function
         *
         * @param builder specifies a function to build a map
         */
        public inline fun build(builder: Builder.() -> Unit): OperationOutput =
            Builder().apply(builder).build()

        public operator fun invoke(builder: Builder.() -> Unit): OperationOutput =
            Builder().apply(builder).build()
    }

    public class Builder {
        public var status: String = ""
        public var statusCode: Int = 0
        public var headers: MutableMap<String, String>? = null
        public var body: ByteStream? = null

        public var input: OperationInput? = null
        public var opMetadata: MutableAttributes? = null

        public fun build(): OperationOutput {
            return OperationOutput(this)
        }
    }
}
