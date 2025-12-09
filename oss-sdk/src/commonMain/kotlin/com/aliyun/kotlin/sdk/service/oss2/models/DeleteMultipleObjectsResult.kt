package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.models.internal.DeleteResultXml

public class DeleteMultipleObjectsResult(builder: Builder) : ResultModel(builder) {

    private val delegate: DeleteResultXml? = innerBody as? DeleteResultXml

    /**
     * The container that stores information about the deleted objects.
     */
    public val deletedObjects: List<DeletedInfo>? = delegate?.deletedObjects

    /**
     * The encoding type of the object name in the response. If this parameter is specified in the request, the object name is encoded in the response.
     */
    public val encodingType: String? = delegate?.encodingType

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DeleteMultipleObjectsResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): DeleteMultipleObjectsResult {
            return DeleteMultipleObjectsResult(this)
        }
    }
}
