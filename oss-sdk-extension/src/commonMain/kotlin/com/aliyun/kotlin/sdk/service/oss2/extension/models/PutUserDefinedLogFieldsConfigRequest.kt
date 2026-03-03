package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the PutUserDefinedLogFieldsConfig operation.
 */
public class PutUserDefinedLogFieldsConfigRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The container that stores the specified log configurations.
     */
    public var userDefinedLogFieldsConfiguration: UserDefinedLogFieldsConfiguration? = builder.userDefinedLogFieldsConfiguration

    public inline fun copy(block: Builder.() -> Unit = {}): PutUserDefinedLogFieldsConfigRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutUserDefinedLogFieldsConfigRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The container that stores the specified log configurations.
         */
        public var userDefinedLogFieldsConfiguration: UserDefinedLogFieldsConfiguration? = null

        public fun build(): PutUserDefinedLogFieldsConfigRequest {
            return PutUserDefinedLogFieldsConfigRequest(this)
        }

        public constructor(from: PutUserDefinedLogFieldsConfigRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.userDefinedLogFieldsConfiguration = from.userDefinedLogFieldsConfiguration
        }
    }
}
