package com.aliyun.kotlin.sdk.service.oss2

import com.aliyun.kotlin.sdk.service.oss2.models.AbortMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.AbortMultipartUploadResult
import com.aliyun.kotlin.sdk.service.oss2.models.AppendObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.AppendObjectResult
import com.aliyun.kotlin.sdk.service.oss2.models.CleanRestoredObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.CleanRestoredObjectResult
import com.aliyun.kotlin.sdk.service.oss2.models.CompleteMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.CompleteMultipartUploadResult
import com.aliyun.kotlin.sdk.service.oss2.models.CopyObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.CopyObjectResult
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteBucketResult
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteMultipleObjectsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteMultipleObjectsResult
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteObjectResult
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteObjectTaggingRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteObjectTaggingResult
import com.aliyun.kotlin.sdk.service.oss2.models.DescribeRegionsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DescribeRegionsResult
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketAclRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketAclResult
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketInfoRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketInfoResult
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketLocationRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketLocationResult
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketStatRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketStatResult
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketVersioningRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketVersioningResult
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectAclRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectAclResult
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectMetaRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectMetaResult
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectResult
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectTaggingRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectTaggingResult
import com.aliyun.kotlin.sdk.service.oss2.models.GetSymlinkRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetSymlinkResult
import com.aliyun.kotlin.sdk.service.oss2.models.HeadObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.HeadObjectResult
import com.aliyun.kotlin.sdk.service.oss2.models.InitiateMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.InitiateMultipartUploadResult
import com.aliyun.kotlin.sdk.service.oss2.models.ListBucketsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListBucketsResult
import com.aliyun.kotlin.sdk.service.oss2.models.ListMultipartUploadsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListMultipartUploadsResult
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectVersionsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectVersionsResult
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectsResult
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectsV2Request
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectsV2Result
import com.aliyun.kotlin.sdk.service.oss2.models.ListPartsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListPartsResult
import com.aliyun.kotlin.sdk.service.oss2.models.PresignResult
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketAclRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketAclResult
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketResult
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketVersioningRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketVersioningResult
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectAclRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectAclResult
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectResult
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectTaggingRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectTaggingResult
import com.aliyun.kotlin.sdk.service.oss2.models.PutSymlinkRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutSymlinkResult
import com.aliyun.kotlin.sdk.service.oss2.models.RestoreObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.RestoreObjectResult
import com.aliyun.kotlin.sdk.service.oss2.models.SealAppendObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.SealAppendObjectResult
import com.aliyun.kotlin.sdk.service.oss2.models.UploadPartCopyRequest
import com.aliyun.kotlin.sdk.service.oss2.models.UploadPartCopyResult
import com.aliyun.kotlin.sdk.service.oss2.models.UploadPartRequest
import com.aliyun.kotlin.sdk.service.oss2.models.UploadPartResult
import kotlinx.io.files.Path

public interface OSSClient : AutoCloseable {

    public companion object {

        /**
         * Creates an instance that implements the OSSClient interface.
         *
         * @param config A [ClientConfiguration]
         * @param optFns The options.
         * @return A [OSSClient]
         */
        public fun create(
            config: ClientConfiguration,
            optFns: List<(ClientOptions) -> ClientOptions>? = null
        ): OSSClient {
            return DefaultOSSClient(config, optFns)
        }
    }

    // common api

    /**
     * Invoke operation
     *
     * @param input A [OperationInput] for common operation.
     * @param options The operation options.
     * @return A [OperationOutput] for common operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun invokeOperation(input: OperationInput, options: OperationOptions? = null): OperationOutput {
        throw UnsupportedOperationException()
    }

    // service api

    /**
     * Queries all buckets that are owned by a requester.
     *
     * @param request A [ListBucketsRequest] for ListBuckets operation.
     * @param options The operation options.
     * @return A [ListBucketsResult] for ListBuckets operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun listBuckets(request: ListBucketsRequest, options: OperationOptions? = null): ListBucketsResult {
        throw UnsupportedOperationException()
    }
    // -----------------------------------------------------------------------

    // regions api

    /**
     * Queries the endpoints of all supported regions or the endpoints of a specific region.
     *
     * @param request A [DescribeRegionsRequest] for DescribeRegions operation.
     * @param options The operation options.
     * @return A [DescribeRegionsResult] for DescribeRegions operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun describeRegions(
        request: DescribeRegionsRequest,
        options: OperationOptions? = null
    ): DescribeRegionsResult {
        throw UnsupportedOperationException()
    }
    // -----------------------------------------------------------------------

    // bucket basic api

    /**
     * Creates a bucket.
     *
     * @param request A [PutBucketRequest] for PutBucket operation.
     * @param options The operation options.
     * @return A [PutBucketResult] for PutBucket operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun putBucket(request: PutBucketRequest, options: OperationOptions? = null): PutBucketResult {
        throw UnsupportedOperationException()
    }

    /**
     * Deletes a bucket.
     *
     * @param request A [DeleteBucketRequest] for DeleteBucket operation.
     * @param options The operation options.
     * @return A [DeleteBucketResult] for DeleteBucket operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun deleteBucket(
        request: DeleteBucketRequest,
        options: OperationOptions? = null
    ): DeleteBucketResult {
        throw UnsupportedOperationException()
    }

    /**
     * Queries the information about all objects in a bucket.
     *
     * @param request A [ListObjectsRequest] for ListObjects operation.
     * @param options The operation options.
     * @return A [ListObjectsResult] for ListObjects operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun listObjects(request: ListObjectsRequest, options: OperationOptions? = null): ListObjectsResult {
        throw UnsupportedOperationException()
    }

    /**
     * Queries the information about all objects in a bucket.
     *
     * @param request A [ListObjectsV2Request] for ListObjectsV2 operation.
     * @param options The operation options.
     * @return A [ListObjectsV2Result] for ListObjectsV2 operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun listObjectsV2(
        request: ListObjectsV2Request,
        options: OperationOptions? = null
    ): ListObjectsV2Result {
        throw UnsupportedOperationException()
    }

    /**
     * Queries the information about a bucket. Only the owner of a bucket can query the information about the bucket. You can call this operation from an Object Storage Service (OSS) endpoint.
     *
     * @param request A [GetBucketInfoRequest] for GetBucketInfo operation.
     * @param options The operation options.
     * @return A [GetBucketInfoResult] for GetBucketInfo operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun getBucketInfo(
        request: GetBucketInfoRequest,
        options: OperationOptions? = null
    ): GetBucketInfoResult {
        throw UnsupportedOperationException()
    }

    /**
     * Queries the storage capacity of a bucket and the number of objects that are stored in the bucket.
     *
     * @param request A [GetBucketStatRequest] for GetBucketStat operation.
     * @param options The operation options.
     * @return A [GetBucketStatResult] for GetBucketStat operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun getBucketStat(
        request: GetBucketStatRequest,
        options: OperationOptions? = null
    ): GetBucketStatResult {
        throw UnsupportedOperationException()
    }

    /**
     * Queries the region in which a bucket resides. Only the owner of a bucket can query the region in which the bucket resides.
     *
     * @param request A [GetBucketLocationRequest] for GetBucketLocation operation.
     * @param options The operation options.
     * @return A [GetBucketLocationResult] for GetBucketLocation operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun getBucketLocation(
        request: GetBucketLocationRequest,
        options: OperationOptions? = null
    ): GetBucketLocationResult {
        throw UnsupportedOperationException()
    }
    // -----------------------------------------------------------------------

    // bucket acl api

    /**
     * Configures or modifies the access control list (ACL) for a bucket.
     *
     * @param request A [PutBucketAclRequest] for PutBucketAcl operation.
     * @param options The operation options.
     * @return A [PutBucketAclResult] for PutBucketAcl operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun putBucketAcl(
        request: PutBucketAclRequest,
        options: OperationOptions? = null
    ): PutBucketAclResult {
        throw UnsupportedOperationException()
    }

    /**
     * Queries the access control list (ACL) of a bucket. Only the owner of a bucket can query the ACL of the bucket.
     *
     * @param request A [GetBucketAclRequest] for GetBucketAcl operation.
     * @param options The operation options.
     * @return A [GetBucketAclResult] for GetBucketAcl operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun getBucketAcl(
        request: GetBucketAclRequest,
        options: OperationOptions? = null
    ): GetBucketAclResult {
        throw UnsupportedOperationException()
    }
    // -----------------------------------------------------------------------

    // bucket versioning api

    /**
     * Configures the versioning state for a bucket.
     *
     * @param request A [PutBucketVersioningRequest] for PutBucketVersioning operation.
     * @param options The operation options.
     * @return A [PutBucketVersioningResult] for PutBucketVersioning operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun putBucketVersioning(
        request: PutBucketVersioningRequest,
        options: OperationOptions? = null
    ): PutBucketVersioningResult {
        throw UnsupportedOperationException()
    }

    /**
     * Queries the versioning state of a bucket.
     *
     * @param request A [GetBucketVersioningRequest] for GetBucketVersioning operation.
     * @param options The operation options.
     * @return A [GetBucketVersioningResult] for GetBucketVersioning operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun getBucketVersioning(
        request: GetBucketVersioningRequest,
        options: OperationOptions? = null
    ): GetBucketVersioningResult {
        throw UnsupportedOperationException()
    }

    /**
     * Queries the information about the versions of all objects in a bucket, including the delete markers.
     *
     * @param request A [ListObjectVersionsRequest] for ListObjectVersions operation.
     * @param options The operation options.
     * @return A [ListObjectVersionsResult] for ListObjectVersions operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun listObjectVersions(
        request: ListObjectVersionsRequest,
        options: OperationOptions? = null
    ): ListObjectVersionsResult {
        throw UnsupportedOperationException()
    }
    // -----------------------------------------------------------------------

    // object basic api

    /**
     * You can call this operation to upload an object.
     *
     * @param request A [PutObjectRequest] for PutObject operation.
     * @param options The operation options.
     * @return A [PutObjectResult] for PutObject operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun putObject(request: PutObjectRequest, options: OperationOptions? = null): PutObjectResult {
        throw UnsupportedOperationException()
    }

    /**
     * Copies objects within a bucket or between buckets in the same region.
     *
     * @param request A [CopyObjectRequest] for CopyObject operation.
     * @param options The operation options.
     * @return A [CopyObjectResult] for CopyObject operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun copyObject(request: CopyObjectRequest, options: OperationOptions? = null): CopyObjectResult {
        throw UnsupportedOperationException()
    }

    /**
     * You can call this operation to query an object.
     *
     * This operation loads entire response body into memory.
     * If the response body is very large this may trigger a RuntimeException.
     * You can call [getObjectAsStream} instead.
     *
     * @param request A [GetObjectRequest] for GetObject operation.
     * @param options The operation options.
     * @return A [GetObjectResult] for GetObject operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun getObject(request: GetObjectRequest, options: OperationOptions? = null): GetObjectResult {
        throw UnsupportedOperationException()
    }

    /**
     * Downloads a object into the local file.
     *
     * @param request A [GetObjectRequest] for GetObject operation.
     * @param path    The local file path.
     * @return A [GetObjectResult] for GetObject operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun getObjectToFile(
        request: GetObjectRequest,
        path: Path,
        options: OperationOptions? = null
    ): GetObjectResult {
        throw UnsupportedOperationException()
    }

    /**
     * You can call this operation to query an object as a stream.
     *
     * This operation complete as soon as a response is available and headers are read,
     * but the content is not read yet.
     *
     * @param request A [GetObjectRequest] for GetObject operation.
     * @param options The operation options.
     * @return A [GetObjectResult] for GetObject operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun getObjectAsStream(
        request: GetObjectRequest,
        options: OperationOptions? = null
    ): GetObjectResult {
        throw UnsupportedOperationException()
    }

    /**
     * You can call this operation to upload an object by appending the object to an existing object.
     *
     * @param request A [AppendObjectRequest] for AppendObject operation.
     * @param options The operation options.
     * @return A [AppendObjectResult] for AppendObject operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun appendObject(
        request: AppendObjectRequest,
        options: OperationOptions? = null
    ): AppendObjectResult {
        throw UnsupportedOperationException()
    }

    /**
     * You can call the SealAppendObject operation to stop appending content to an appendable object. After you call this operation, the object becomes non-appendable.
     *
     * @param request A [SealAppendObjectRequest] for SealAppendObject operation.
     * @param options The operation options.
     * @return A [SealAppendObjectResult] for SealAppendObject operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun sealAppendObject(
        request: SealAppendObjectRequest,
        options: OperationOptions? = null
    ): SealAppendObjectResult {
        throw UnsupportedOperationException()
    }

    /**
     * You can call this operation to delete an object.
     *
     * @param request A [DeleteObjectRequest] for DeleteObject operation.
     * @param options The operation options.
     * @return A [DeleteObjectResult] for DeleteObject operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun deleteObject(
        request: DeleteObjectRequest,
        options: OperationOptions? = null
    ): DeleteObjectResult {
        throw UnsupportedOperationException()
    }

    /**
     * You can call this operation to query the metadata of an object.
     *
     * @param request A [HeadObjectRequest] for HeadObject operation.
     * @param options The operation options.
     * @return A [HeadObjectResult] for HeadObject operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun headObject(request: HeadObjectRequest, options: OperationOptions? = null): HeadObjectResult {
        throw UnsupportedOperationException()
    }

    /**
     * You can call this operation to query the metadata of an object, including ETag, Size, and LastModified. The content of the object is not returned.
     *
     * @param request A [GetObjectMetaRequest] for GetObjectMeta operation.
     * @param options The operation options.
     * @return A [GetObjectMetaResult] for GetObjectMeta operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun getObjectMeta(
        request: GetObjectMetaRequest,
        options: OperationOptions? = null
    ): GetObjectMetaResult {
        throw UnsupportedOperationException()
    }

    /**
     * You can call this operation to delete multiple objects from a bucket.
     *
     * @param request A [DeleteMultipleObjectsRequest] for DeleteMultipleObjects operation.
     * @return A [DeleteMultipleObjectsResult] for DeleteMultipleObjects operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun deleteMultipleObjects(
        request: DeleteMultipleObjectsRequest,
        options: OperationOptions? = null
    ): DeleteMultipleObjectsResult {
        throw UnsupportedOperationException()
    }

    /**
     * You can call this operation to restore objects of the Archive and Cold Archive storage classes.
     *
     * @param request A [RestoreObjectRequest] for RestoreObject operation.
     * @param options The operation options.
     * @return A [RestoreObjectResult] for RestoreObject operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun restoreObject(
        request: RestoreObjectRequest,
        options: OperationOptions? = null
    ): RestoreObjectResult {
        throw UnsupportedOperationException()
    }

    /**
     * You can call this operation to clean an object restored from Archive or Cold Archive state. After that, the restored object returns to the frozen state.
     *
     * @param request A [CleanRestoredObjectRequest] for CleanRestoredObject operation.
     * @param options The operation options.
     * @return A [CleanRestoredObjectResult] for CleanRestoredObject operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun cleanRestoredObject(
        request: CleanRestoredObjectRequest,
        options: OperationOptions? = null
    ): CleanRestoredObjectResult {
        throw UnsupportedOperationException()
    }
    // -----------------------------------------------------------------------

    // object multipart

    /**
     * Initiates a multipart upload task.
     *
     * @param request A [InitiateMultipartUploadRequest] for InitiateMultipartUpload operation.
     * @param options The operation options.
     * @return A [InitiateMultipartUploadResult] for InitiateMultipartUpload operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun initiateMultipartUpload(
        request: InitiateMultipartUploadRequest,
        options: OperationOptions? = null
    ): InitiateMultipartUploadResult {
        throw UnsupportedOperationException()
    }

    /**
     * You can call this operation to upload an object by part based on the object name and the upload ID that you specify.
     *
     * @param request A [UploadPartRequest] for UploadPart operation.
     * @param options The operation options.
     * @return A [UploadPartResult] for UploadPart operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun uploadPart(request: UploadPartRequest, options: OperationOptions? = null): UploadPartResult {
        throw UnsupportedOperationException()
    }

    /**
     * You can call this operation to complete the multipart upload task of an object.
     *
     * @param request A [CompleteMultipartUploadRequest] for CompleteMultipartUpload operation.
     * @param options The operation options.
     * @return A [CompleteMultipartUploadResult] for CompleteMultipartUpload operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun completeMultipartUpload(
        request: CompleteMultipartUploadRequest,
        options: OperationOptions? = null
    ): CompleteMultipartUploadResult {
        throw UnsupportedOperationException()
    }

    /**
     * Upload a single shard by copying existing files
     *
     * @param request A [UploadPartCopyRequest] for UploadPartCopy operation.
     * @param options The operation options.
     * @return A [UploadPartCopyResult] for UploadPartCopy operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun uploadPartCopy(
        request: UploadPartCopyRequest,
        options: OperationOptions? = null
    ): UploadPartCopyResult {
        throw UnsupportedOperationException()
    }

    /**
     * You can call this operation to cancel a multipart upload task and delete the parts that are uploaded by the multipart upload task.
     *
     * @param request A [AbortMultipartUploadRequest] for AbortMultipartUpload operation.
     * @param options The operation options.
     * @return A [AbortMultipartUploadResult] for AbortMultipartUpload operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun abortMultipartUpload(
        request: AbortMultipartUploadRequest,
        options: OperationOptions? = null
    ): AbortMultipartUploadResult {
        throw UnsupportedOperationException()
    }

    /**
     * You can call this operation to list all ongoing multipart upload tasks.
     *
     * @param request A [ListMultipartUploadsRequest] for ListMultipartUploads operation.
     * @param options The operation options.
     * @return A [ListMultipartUploadsResult] for ListMultipartUploads operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun listMultipartUploads(
        request: ListMultipartUploadsRequest,
        options: OperationOptions? = null
    ): ListMultipartUploadsResult {
        throw UnsupportedOperationException()
    }

    /**
     * You can call this operation to list all parts that are uploaded by using a specified upload ID.
     *
     * @param request A [ListPartsRequest] for ListParts operation.
     * @param options The operation options.
     * @return A [ListPartsResult] for ListParts operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun listParts(request: ListPartsRequest, options: OperationOptions? = null): ListPartsResult {
        throw UnsupportedOperationException()
    }

    // object acl

    /**
     * You can call this operation to modify the ACL of an object.
     *
     * @param request A [PutObjectAclRequest] for PutObjectAcl operation.
     * @param options The operation options.
     * @return A [PutObjectAclResult] for PutObjectAcl operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun putObjectAcl(
        request: PutObjectAclRequest,
        options: OperationOptions? = null
    ): PutObjectAclResult {
        throw UnsupportedOperationException()
    }

    /**
     * You can call this operation to query the ACL of an object in a bucket.
     *
     * @param request A [GetObjectAclRequest] for GetObjectAcl operation.
     * @param options The operation options.
     * @return A [GetObjectAclResult] for GetObjectAcl operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun getObjectAcl(
        request: GetObjectAclRequest,
        options: OperationOptions? = null
    ): GetObjectAclResult {
        throw UnsupportedOperationException()
    }
    // -----------------------------------------------------------------------

    // object tagging

    /**
     * You can call this operation to add tags to or modify the tags of an object.
     *
     * @param request A [PutObjectTaggingRequest] for PutObjectTagging operation.
     * @param options The operation options.
     * @return A [PutObjectTaggingResult] for PutObjectTagging operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun putObjectTagging(
        request: PutObjectTaggingRequest,
        options: OperationOptions? = null
    ): PutObjectTaggingResult {
        throw UnsupportedOperationException()
    }

    /**
     * You can call this operation to query the tags of an object.
     *
     * @param request A [GetObjectTaggingRequest] for GetObjectTagging operation.
     * @param options The operation options.
     * @return A [GetObjectTaggingResult] for GetObjectTagging operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun getObjectTagging(
        request: GetObjectTaggingRequest,
        options: OperationOptions? = null
    ): GetObjectTaggingResult {
        throw UnsupportedOperationException()
    }

    /**
     * You can call this operation to delete the tags of a specified object.
     *
     * @param request A [DeleteObjectTaggingRequest] for DeleteObjectTagging operation.
     * @param options The operation options.
     * @return A [DeleteObjectTaggingResult] for DeleteObjectTagging operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun deleteObjectTagging(
        request: DeleteObjectTaggingRequest,
        options: OperationOptions? = null
    ): DeleteObjectTaggingResult {
        throw UnsupportedOperationException()
    }
    // -----------------------------------------------------------------------

    // object symbolic link

    /**
     * You can create a symbolic link for a target object.
     *
     * @param request A [PutSymlinkRequest] for PutSymlink operation.
     * @param options The operation options.
     * @return A [PutSymlinkResult] for PutSymlink operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun putSymlink(request: PutSymlinkRequest, options: OperationOptions? = null): PutSymlinkResult {
        throw UnsupportedOperationException()
    }

    /**
     * You can call this operation to query a symbolic link of an object.
     *
     * @param request A [GetSymlinkRequest] for GetSymlink operation.
     * @param options The operation options.
     * @return A [GetSymlinkResult] for GetSymlink operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun getSymlink(request: GetSymlinkRequest, options: OperationOptions? = null): GetSymlinkResult {
        throw UnsupportedOperationException()
    }
    // -----------------------------------------------------------------------

    // extensions api

    /**
     * Use GetBucketAcl to check if the bucket exists.
     *
     * @param bucket The bucket name.
     * @return Returns true if the bucket exists and false if not.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun doesBucketExist(bucket: String): Boolean {
        throw UnsupportedOperationException()
    }

    /**
     * Use GetObjectMeta to check if the object exists.
     *
     * @param bucket The bucket name.
     * @return Returns true if the bucket exists and false if not.
     * @throws RuntimeException If an error occurs
     * Throw an exception when encountering a NoSuchBucket error.
     */
    public suspend fun doesObjectExist(bucket: String, key: String, versionId: String? = null): Boolean {
        throw UnsupportedOperationException()
    }
    // -----------------------------------------------------------------------

    // pre-signer

    /**
     * Generates the pre-signed URL for GetObject operation.
     *
     * @param request A [GetObjectRequest] for GetObject operation.
     * @param options The presign operation options.
     * @return A [PresignResult] for GetObject operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun presign(request: GetObjectRequest, options: PresignOptions? = null): PresignResult {
        throw UnsupportedOperationException()
    }

    /**
     * Generates the pre-signed URL for PutObject operation.
     *
     * @param request A [PutObjectRequest] for PutObject operation.
     * @param options The presign operation options.
     * @return A [PresignResult] for PutObject operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun presign(request: PutObjectRequest, options: PresignOptions? = null): PresignResult {
        throw UnsupportedOperationException()
    }

    /**
     * Generates the pre-signed URL for HeadObject operation.
     *
     * @param request A [HeadObjectRequest] for HeadObject operation.
     * @param options The presign operation options.
     * @return A [PresignResult] for HeadObject operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun presign(request: HeadObjectRequest, options: PresignOptions? = null): PresignResult {
        throw UnsupportedOperationException()
    }

    /**
     * Generates the pre-signed URL for InitiateMultipartUpload operation.
     *
     * @param request A [InitiateMultipartUploadRequest] for InitiateMultipartUpload operation.
     * @param options The presign operation options.
     * @return A [PresignResult] for InitiateMultipartUpload operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun presign(
        request: InitiateMultipartUploadRequest,
        options: PresignOptions? = null
    ): PresignResult {
        throw UnsupportedOperationException()
    }

    /**
     * Generates the pre-signed URL for UploadPart operation.
     *
     * @param request A [UploadPartRequest] for UploadPart operation.
     * @param options The presign operation options.
     * @return A [PresignResult] for UploadPart operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun presign(request: UploadPartRequest, options: PresignOptions? = null): PresignResult {
        throw UnsupportedOperationException()
    }

    /**
     * Generates the pre-signed URL for CompleteMultipartUpload operation.
     *
     * @param request A [CompleteMultipartUploadRequest] for CompleteMultipartUpload operation.
     * @param options The presign operation options.
     * @return A [PresignResult] for CompleteMultipartUpload operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun presign(
        request: CompleteMultipartUploadRequest,
        options: PresignOptions? = null
    ): PresignResult {
        throw UnsupportedOperationException()
    }

    /**
     * Generates the pre-signed URL for AbortMultipartUpload operation.
     *
     * @param request A [AbortMultipartUploadRequest] for AbortMultipartUpload operation.
     * @param options The presign operation options.
     * @return A [PresignResult] for AbortMultipartUpload operation.
     * @throws RuntimeException If an error occurs
     */
    public suspend fun presign(request: AbortMultipartUploadRequest, options: PresignOptions? = null): PresignResult {
        throw UnsupportedOperationException()
    }
    // -----------------------------------------------------------------------
}
