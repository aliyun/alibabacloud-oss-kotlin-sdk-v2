package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.models.internal.ListVersionsResult

/**
 * The result for the ListObjectVersions operation.
 */
public class ListObjectVersionsResult(builder: Builder) : ResultModel(builder) {

    private val delegate = builder.innerBody as? ListVersionsResult

    /**
     * If not all results are returned for the request, the NextVersionIdMarker parameter is included in the response to indicate the version-id-marker value of the next ListObjectVersions (GetBucketVersions) request.
     */
    public val nextVersionIdMarker: String? = delegate?.nextVersionIdMarker

    /**
     * The bucket name
     */
    public val name: String? = delegate?.name

    /**
     * The prefix contained in the names of the returned objects.
     */
    public val prefix: String? = delegate?.prefix

    /**
     * The version from which the ListObjectVersions (GetBucketVersions) operation starts. This parameter is used together with KeyMarker.
     */
    public val versionIdMarker: String? = delegate?.versionIdMarker

    /**
     * The maximum number of objects that can be returned in the response.
     */
    public val maxKeys: Long? = delegate?.maxKeys

    /**
     * The character that is used to group objects by name. The objects whose names contain the same string from the prefix to the next occurrence of the delimiter are grouped as a single result parameter in CommonPrefixes.
     */
    public val delimiter: String? = delegate?.delimiter

    /**
     * The container that stores delete markers
     */
    public val deleteMarkers: List<DeleteMarkerEntry>? = delegate?.deleteMarkers

    /**
     * Objects whose names contain the same string that ranges from the prefix to the next occurrence of the delimiter are grouped as a single result element
     */
    public val commonPrefixes: List<CommonPrefix>? = delegate?.commonPrefixes

    /**
     * Indicates the object from which the ListObjectVersions (GetBucketVersions) operation starts.
     */
    public val keyMarker: String? = delegate?.keyMarker

    /**
     * Indicates whether the returned results are truncated.
     * - true: indicates that not all results are returned for the request.
     * - false: indicates that all results are returned for the request.
     */
    public val isTruncated: Boolean? = delegate?.isTruncated

    /**
     * The encoding type of the content in the response. If you specify encoding-type in the request, the values of Delimiter, Marker, Prefix, NextMarker, and Key are encoded in the response.
     */
    public val encodingType: String? = delegate?.encodingType

    /**
     * If not all results are returned for the request, the NextKeyMarker parameter is included in the response to indicate the key-marker value of the next ListObjectVersions (GetBucketVersions) request.
     */
    public val nextKeyMarker: String? = delegate?.nextKeyMarker

    /**
     * The container that stores the versions of objects except for delete markers
     */
    public val versions: List<ObjectVersion>? = delegate?.versions

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ListObjectVersionsResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): ListObjectVersionsResult {
            return ListObjectVersionsResult(this)
        }
    }
}
