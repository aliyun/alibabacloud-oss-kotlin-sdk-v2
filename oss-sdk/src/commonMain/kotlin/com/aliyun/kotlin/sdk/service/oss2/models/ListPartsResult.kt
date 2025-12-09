package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.models.internal.ListPartResult

/**
 * The result for the ListParts operation.
 */
public class ListPartsResult(builder: Builder) : ResultModel(builder) {

    private val delegate = builder.innerBody as? ListPartResult

    /**
     * Indicates whether the list of parts returned in the response has been truncated. A value of true indicates that the response does not contain all required results. A value of false indicates that the response contains all required results.
     * Valid values: true and false.
     */
    public val isTruncated: Boolean? = delegate?.isTruncated

    /**
     * The list of all parts.
     */
    public val parts: List<Part>? = delegate?.parts

    /**
     * The name of the bucket.
     */
    public val bucket: String? = delegate?.bucket

    /**
     * The name of the object.
     */
    public val key: String? = delegate?.key

    /**
     * The ID of the upload task.
     */
    public val uploadId: String? = delegate?.uploadId

    /**
     * The position from which the list starts. All parts whose part numbers are greater than the value of this parameter are listed.
     */
    public val partNumberMarker: Long? = delegate?.partNumberMarker

    /**
     * The NextPartNumberMarker value that is used for the PartNumberMarker value in a subsequent request when the response does not contain all required results.
     */
    public val nextPartNumberMarker: Long? = delegate?.nextPartNumberMarker

    /**
     * The maximum number of parts in the response.
     */
    public val maxParts: Long? = delegate?.maxParts

    /**
     * The method used to encode the object name in the response. If encoding-type is specified in the request, values of Key are encoded in the returned result.
     */
    public var encodingType: String? = delegate?.encodingType

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ListPartsResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): ListPartsResult {
            return ListPartsResult(this)
        }
    }
}
