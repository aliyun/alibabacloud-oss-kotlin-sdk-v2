package com.aliyun.kotlin.sdk.service.oss2.models

public class SealAppendObjectResult(builder: SealAppendObjectResult.Builder) : ResultModel(builder) {

    /**
     * The time in GMT format when the SealAppendObject operation was first performed on the object. This timestamp does not change even if the operation is performed again.
     */
    public val sealedTime: String?
        get() = headers["x-oss-sealed-time"]

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): SealAppendObjectResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): SealAppendObjectResult {
            return SealAppendObjectResult(this)
        }
    }
}
