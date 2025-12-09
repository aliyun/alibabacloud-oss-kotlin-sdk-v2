package com.aliyun.kotlin.sdk.service.oss2

import com.aliyun.kotlin.sdk.service.oss2.internal.ResponseHandler
import com.aliyun.kotlin.sdk.service.oss2.types.AttributeKey
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.types.MutableAttributes
import com.aliyun.kotlin.sdk.service.oss2.types.StreamObserver
import com.aliyun.kotlin.sdk.service.oss2.types.mutableAttributes
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils

public class OperationMetadataKey {
    public companion object {
        // the epochSeconds number of seconds from the epoch instant 1970-01-01T00:00:00Z
        public val EXPIRATION_EPOCH_SEC: AttributeKey<Long> = AttributeKey<Long>("EXPIRATION_EPOCH_SEC")

        public val SUB_RESOURCE: AttributeKey<List<String>> = AttributeKey<List<String>>("SUB_RESOURCE")

        public val UPLOAD_OBSERVER: AttributeKey<List<StreamObserver>> =
            AttributeKey<List<StreamObserver>>("UPLOAD_OBSERVER")

        public val RESPONSE_HANDLE: AttributeKey<List<ResponseHandler>> =
            AttributeKey<List<ResponseHandler>>("RESPONSE_HANDLE")

        /**
         * If true, the operation should complete as soon as a response is available and headers are read, the content is not read yet.
         * If false, the operation should complete after reading the entire response including the content
         */
        public val RESPONSE_HEADERS_READ: AttributeKey<Boolean> = AttributeKey<Boolean>("RESPONSE_HEADERS_READ")
    }
}

public class OperationInput(builder: Builder) {
    public val opName: String = builder.opName ?: ""
    public val method: String = builder.method ?: ""
    public val headers: MutableMap<String, String> = builder.headers ?: MapUtils.headersMap()
    public val parameters: MutableMap<String, String?> = builder.parameters ?: MapUtils.parametersMap()

    public val body: ByteStream? = builder.body

    public val bucket: String? = builder.bucket
    public val key: String? = builder.key

    public val opMetadata: MutableAttributes = builder.opMetadata ?: mutableAttributes()

    public companion object {
        /**
         * Builds a [OperationInput] instance with the given [builder] function
         *
         * @param builder specifies a function to build a map
         */
        public inline fun build(builder: Builder.() -> Unit): OperationInput = Builder().apply(builder).build()

        public operator fun invoke(builder: Builder.() -> Unit): OperationInput = Builder().apply(builder).build()
    }

    public class Builder() {

        public var opName: String? = null
        public var method: String? = null

        public var headers: MutableMap<String, String>? = null

        public var parameters: MutableMap<String, String?>? = null

        public var bucket: String? = null

        public var key: String? = null

        public var body: ByteStream? = null

        public var opMetadata: MutableAttributes? = null

        public fun build(): OperationInput {
            return OperationInput(this)
        }
    }
}
