package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the GetObjectAcl operation.
 */
public class GetObjectAclResult(builder: Builder) : ResultModel(builder) {

    /**
     * The container that stores the results of the GetObjectACL request.
     */
    public val accessControlPolicy: AccessControlPolicy?
        get() = innerBody as? AccessControlPolicy

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetObjectAclResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): GetObjectAclResult {
            return GetObjectAclResult(this)
        }
    }
}
