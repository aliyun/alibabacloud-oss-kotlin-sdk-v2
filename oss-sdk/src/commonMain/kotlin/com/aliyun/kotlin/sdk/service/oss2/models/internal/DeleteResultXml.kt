package com.aliyun.kotlin.sdk.service.oss2.models.internal

import com.aliyun.kotlin.sdk.service.oss2.models.DeletedInfo

internal class DeleteResultXml {

    /**
     * The container that stores information about the deleted objects.
     */
    var deletedObjects: List<DeletedInfo>? = null

    /**
     * The encoding type of the object name in the response. If this parameter is specified in the request, the object name is encoded in the response.
     */
    var encodingType: String? = null

    companion object {
        operator fun invoke(builder: DeleteResultXml.() -> Unit): DeleteResultXml =
            DeleteResultXml().apply(builder)
    }
}
