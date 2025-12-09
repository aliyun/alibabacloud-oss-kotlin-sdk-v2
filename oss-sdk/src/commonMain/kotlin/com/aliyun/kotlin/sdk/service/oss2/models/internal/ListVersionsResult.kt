package com.aliyun.kotlin.sdk.service.oss2.models.internal

import com.aliyun.kotlin.sdk.service.oss2.models.CommonPrefix
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteMarkerEntry
import com.aliyun.kotlin.sdk.service.oss2.models.ObjectVersion

/**
 * The container that stores the results of the ListObjectVersions (GetBucketVersions) request.
 */
internal class ListVersionsResult {
    /**
     * If not all results are returned for the request, the NextVersionIdMarker parameter is included in the response to indicate the version-id-marker value of the next ListObjectVersions (GetBucketVersions) request.
     */
    var nextVersionIdMarker: String? = null

    /**
     * The bucket name
     */
    var name: String? = null

    /**
     * The prefix contained in the names of the returned objects.
     */
    var prefix: String? = null

    /**
     * The version from which the ListObjectVersions (GetBucketVersions) operation starts. This parameter is used together with KeyMarker.
     */
    var versionIdMarker: String? = null

    /**
     * The maximum number of objects that can be returned in the response.
     */
    var maxKeys: Long? = null

    /**
     * The character that is used to group objects by name. The objects whose names contain the same string from the prefix to the next occurrence of the delimiter are grouped as a single result parameter in CommonPrefixes.
     */
    var delimiter: String? = null

    /**
     * The container that stores delete markers
     */
    var deleteMarkers: List<DeleteMarkerEntry>? = null

    /**
     * Objects whose names contain the same string that ranges from the prefix to the next occurrence of the delimiter are grouped as a single result element
     */
    var commonPrefixes: List<CommonPrefix>? = null

    /**
     * Indicates the object from which the ListObjectVersions (GetBucketVersions) operation starts.
     */
    var keyMarker: String? = null

    /**
     * Indicates whether the returned results are truncated.- true: indicates that not all results are returned for the request.- false: indicates that all results are returned for the request.
     */
    var isTruncated: Boolean? = null

    /**
     * The encoding type of the content in the response. If you specify encoding-type in the request, the values of Delimiter, Marker, Prefix, NextMarker, and Key are encoded in the response.
     */
    var encodingType: String? = null

    /**
     * If not all results are returned for the request, the NextKeyMarker parameter is included in the response to indicate the key-marker value of the next ListObjectVersions (GetBucketVersions) request.
     */
    var nextKeyMarker: String? = null

    /**
     * The container that stores the versions of objects except for delete markers
     */
    var versions: List<ObjectVersion>? = null

    companion object {
        operator fun invoke(builder: ListVersionsResult.() -> Unit): ListVersionsResult =
            ListVersionsResult().apply(builder)
    }
}
