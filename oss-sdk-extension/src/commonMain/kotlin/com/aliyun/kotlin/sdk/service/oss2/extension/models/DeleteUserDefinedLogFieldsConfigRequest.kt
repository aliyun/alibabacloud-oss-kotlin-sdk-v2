package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the DeleteUserDefinedLogFieldsConfig operation.
 */
public class DeleteUserDefinedLogFieldsConfigRequest(builder: Builder) : RequestModel(builder) {

    /**
     *
     */
    public val bucket: String? = builder.bucket

    public inline fun copy(block: Builder.() -> Unit = {}): DeleteUserDefinedLogFieldsConfigRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DeleteUserDefinedLogFieldsConfigRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         *
         */
        public var bucket: String? = null

        public fun build(): DeleteUserDefinedLogFieldsConfigRequest {
            return DeleteUserDefinedLogFieldsConfigRequest(this)
        }

        public constructor(from: DeleteUserDefinedLogFieldsConfigRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
        }
    }
}
