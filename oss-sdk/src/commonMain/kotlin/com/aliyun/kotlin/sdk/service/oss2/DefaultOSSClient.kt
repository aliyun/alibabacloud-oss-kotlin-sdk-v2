package com.aliyun.kotlin.sdk.service.oss2

import com.aliyun.kotlin.sdk.service.oss2.exceptions.InconsistentException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.hash.CRC64Observer
import com.aliyun.kotlin.sdk.service.oss2.internal.ClientImpl
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
import com.aliyun.kotlin.sdk.service.oss2.operations.BucketAcl
import com.aliyun.kotlin.sdk.service.oss2.operations.BucketBasic
import com.aliyun.kotlin.sdk.service.oss2.operations.BucketVersioning
import com.aliyun.kotlin.sdk.service.oss2.operations.ObjectAcl
import com.aliyun.kotlin.sdk.service.oss2.operations.ObjectBasic
import com.aliyun.kotlin.sdk.service.oss2.operations.ObjectMultipart
import com.aliyun.kotlin.sdk.service.oss2.operations.ObjectSymlink
import com.aliyun.kotlin.sdk.service.oss2.operations.ObjectTagging
import com.aliyun.kotlin.sdk.service.oss2.operations.Presigner
import com.aliyun.kotlin.sdk.service.oss2.operations.Region
import com.aliyun.kotlin.sdk.service.oss2.operations.Service
import com.aliyun.kotlin.sdk.service.oss2.progress.ProgressObserver
import com.aliyun.kotlin.sdk.service.oss2.types.FeatureFlagsType
import com.aliyun.kotlin.sdk.service.oss2.types.StreamObserver
import com.aliyun.kotlin.sdk.service.oss2.types.toFlow
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

internal class DefaultOSSClient(
    config: ClientConfiguration,
    optFns: List<(ClientOptions) -> ClientOptions>? = null
) : OSSClient {
    private val clientImpl = ClientImpl(config, optFns ?: listOf())

    override fun close() {
        clientImpl.close()
    }

    override suspend fun invokeOperation(input: OperationInput, options: OperationOptions?): OperationOutput {
        return clientImpl.execute(input, options)
    }

    override suspend fun listBuckets(request: ListBucketsRequest, options: OperationOptions?): ListBucketsResult {
        return Service.listBuckets(this.clientImpl, request, options)
    }

    override suspend fun describeRegions(request: DescribeRegionsRequest, options: OperationOptions?): DescribeRegionsResult {
        return Region.describeRegions(this.clientImpl, request, options)
    }

    override suspend fun getBucketStat(request: GetBucketStatRequest, options: OperationOptions?): GetBucketStatResult {
        return BucketBasic.getBucketStat(this.clientImpl, request, options)
    }

    override suspend fun putBucket(request: PutBucketRequest, options: OperationOptions?): PutBucketResult {
        return BucketBasic.putBucket(this.clientImpl, request, options)
    }

    override suspend fun deleteBucket(request: DeleteBucketRequest, options: OperationOptions?): DeleteBucketResult {
        return BucketBasic.deleteBucket(this.clientImpl, request, options)
    }

    override suspend fun listObjects(request: ListObjectsRequest, options: OperationOptions?): ListObjectsResult {
        return BucketBasic.listObjects(this.clientImpl, request, options)
    }

    override suspend fun listObjectsV2(request: ListObjectsV2Request, options: OperationOptions?): ListObjectsV2Result {
        return BucketBasic.listObjectsV2(this.clientImpl, request, options)
    }

    override suspend fun getBucketInfo(request: GetBucketInfoRequest, options: OperationOptions?): GetBucketInfoResult {
        return BucketBasic.getBucketInfo(this.clientImpl, request, options)
    }

    override suspend fun getBucketLocation(request: GetBucketLocationRequest, options: OperationOptions?): GetBucketLocationResult {
        return BucketBasic.getBucketLocation(this.clientImpl, request, options)
    }

    override suspend fun putBucketVersioning(request: PutBucketVersioningRequest, options: OperationOptions?): PutBucketVersioningResult {
        return BucketVersioning.putBucketVersioning(this.clientImpl, request, options)
    }

    override suspend fun getBucketVersioning(request: GetBucketVersioningRequest, options: OperationOptions?): GetBucketVersioningResult {
        return BucketVersioning.getBucketVersioning(this.clientImpl, request, options)
    }

    override suspend fun listObjectVersions(request: ListObjectVersionsRequest, options: OperationOptions?): ListObjectVersionsResult {
        return BucketVersioning.listObjectVersions(this.clientImpl, request, options)
    }

    override suspend fun putBucketAcl(request: PutBucketAclRequest, options: OperationOptions?): PutBucketAclResult {
        return BucketAcl.putBucketAcl(this.clientImpl, request, options)
    }

    override suspend fun getBucketAcl(request: GetBucketAclRequest, options: OperationOptions?): GetBucketAclResult {
        return BucketAcl.getBucketAcl(this.clientImpl, request, options)
    }

    override suspend fun putObject(request: PutObjectRequest, options: OperationOptions?): PutObjectResult {
        return ObjectBasic.putObject(this.clientImpl, request, options)
    }

    override suspend fun copyObject(request: CopyObjectRequest, options: OperationOptions?): CopyObjectResult {
        return ObjectBasic.copyObject(this.clientImpl, request, options)
    }

    override suspend fun getObject(request: GetObjectRequest, options: OperationOptions?): GetObjectResult {
        return ObjectBasic.getObject(this.clientImpl, request, false, options)
    }

    override suspend fun getObjectAsStream(request: GetObjectRequest, options: OperationOptions?): GetObjectResult {
        return ObjectBasic.getObject(this.clientImpl, request, true, options)
    }

    override suspend fun getObjectToFile(request: GetObjectRequest, path: Path, options: OperationOptions?): GetObjectResult {
        return getObjectAsStream(request, options).let {
            it.use { result ->
                SystemFileSystem.sink(path).buffered().use { sink ->
                    val streamObserver = mutableListOf<StreamObserver>()
                    request.progressListener?.let { progressListener ->
                        streamObserver.add(
                            ProgressObserver(
                                progressListener,
                                result.headers["Content-Length"]?.toLong() ?: -1
                            )
                        )
                    }
                    val crc64Observer = if (this.clientImpl.featureFlags.contains(
                            FeatureFlagsType.ENABLE_CRC64_CHECK_DOWNLOAD
                        )
                    ) {
                        CRC64Observer().also { observer -> streamObserver.add(observer) }
                    } else {
                        null
                    }
                    result.body?.toFlow()?.collect { bytes ->
                        streamObserver.forEach { observer -> observer.data(bytes, 0, bytes.size) }
                        sink.write(bytes)
                    }
                    crc64Observer?.checksum?.let { checksum ->
                        result.headers["x-oss-hash-crc64ecma"]?.let { serverCrc ->
                            val clientCrc = checksum.digestValue.toULong().toString()
                            if (serverCrc != clientCrc) {
                                throw InconsistentException(clientCrc, serverCrc, result.headers)
                            }
                        }
                    }
                }
            }

            // remove body
            GetObjectResult {
                status = it.status
                statusCode = it.statusCode
                headers = it.headers
            }
        }
    }

    override suspend fun appendObject(request: AppendObjectRequest, options: OperationOptions?): AppendObjectResult {
        return ObjectBasic.appendObject(this.clientImpl, request, options)
    }

    override suspend fun sealAppendObject(request: SealAppendObjectRequest, options: OperationOptions?): SealAppendObjectResult {
        return ObjectBasic.sealAppendObject(this.clientImpl, request, options)
    }

    override suspend fun deleteObject(request: DeleteObjectRequest, options: OperationOptions?): DeleteObjectResult {
        return ObjectBasic.deleteObject(this.clientImpl, request, options)
    }

    override suspend fun headObject(request: HeadObjectRequest, options: OperationOptions?): HeadObjectResult {
        return ObjectBasic.headObject(this.clientImpl, request, options)
    }

    override suspend fun getObjectMeta(request: GetObjectMetaRequest, options: OperationOptions?): GetObjectMetaResult {
        return ObjectBasic.getObjectMeta(this.clientImpl, request, options)
    }

    override suspend fun deleteMultipleObjects(request: DeleteMultipleObjectsRequest, options: OperationOptions?): DeleteMultipleObjectsResult {
        return ObjectBasic.deleteMultipleObjects(this.clientImpl, request, options)
    }

    override suspend fun restoreObject(request: RestoreObjectRequest, options: OperationOptions?): RestoreObjectResult {
        return ObjectBasic.restoreObject(this.clientImpl, request, options)
    }

    override suspend fun cleanRestoredObject(request: CleanRestoredObjectRequest, options: OperationOptions?): CleanRestoredObjectResult {
        return ObjectBasic.cleanRestoredObject(this.clientImpl, request, options)
    }

    override suspend fun initiateMultipartUpload(request: InitiateMultipartUploadRequest, options: OperationOptions?): InitiateMultipartUploadResult {
        return ObjectMultipart.initiateMultipartUpload(this.clientImpl, request, options)
    }

    override suspend fun uploadPart(request: UploadPartRequest, options: OperationOptions?): UploadPartResult {
        return ObjectMultipart.uploadPart(this.clientImpl, request, options)
    }

    override suspend fun completeMultipartUpload(request: CompleteMultipartUploadRequest, options: OperationOptions?): CompleteMultipartUploadResult {
        return ObjectMultipart.completeMultipartUpload(this.clientImpl, request, options)
    }

    override suspend fun uploadPartCopy(request: UploadPartCopyRequest, options: OperationOptions?): UploadPartCopyResult {
        return ObjectMultipart.uploadPartCopy(this.clientImpl, request, options)
    }

    override suspend fun abortMultipartUpload(request: AbortMultipartUploadRequest, options: OperationOptions?): AbortMultipartUploadResult {
        return ObjectMultipart.abortMultipartUpload(this.clientImpl, request, options)
    }

    override suspend fun listMultipartUploads(request: ListMultipartUploadsRequest, options: OperationOptions?): ListMultipartUploadsResult {
        return ObjectMultipart.listMultipartUploads(this.clientImpl, request, options)
    }

    override suspend fun listParts(request: ListPartsRequest, options: OperationOptions?): ListPartsResult {
        return ObjectMultipart.listParts(this.clientImpl, request, options)
    }

    override suspend fun putSymlink(request: PutSymlinkRequest, options: OperationOptions?): PutSymlinkResult {
        return ObjectSymlink.putSymlink(this.clientImpl, request, options)
    }

    override suspend fun getSymlink(request: GetSymlinkRequest, options: OperationOptions?): GetSymlinkResult {
        return ObjectSymlink.getSymlink(this.clientImpl, request, options)
    }

    override suspend fun putObjectTagging(request: PutObjectTaggingRequest, options: OperationOptions?): PutObjectTaggingResult {
        return ObjectTagging.putObjectTagging(this.clientImpl, request, options)
    }

    override suspend fun getObjectTagging(request: GetObjectTaggingRequest, options: OperationOptions?): GetObjectTaggingResult {
        return ObjectTagging.getObjectTagging(this.clientImpl, request, options)
    }

    override suspend fun deleteObjectTagging(request: DeleteObjectTaggingRequest, options: OperationOptions?): DeleteObjectTaggingResult {
        return ObjectTagging.deleteObjectTagging(this.clientImpl, request, options)
    }

    override suspend fun putObjectAcl(request: PutObjectAclRequest, options: OperationOptions?): PutObjectAclResult {
        return ObjectAcl.putObjectAcl(this.clientImpl, request, options)
    }

    override suspend fun getObjectAcl(request: GetObjectAclRequest, options: OperationOptions?): GetObjectAclResult {
        return ObjectAcl.getObjectAcl(this.clientImpl, request, options)
    }

    override suspend fun doesBucketExist(bucket: String): Boolean {
        try {
            getBucketAcl(
                GetBucketAclRequest {
                    this.bucket = bucket
                }
            )
            return true
        } catch (e: Exception) {
            val exception = e.cause as? ServiceException
            if ("NoSuchBucket" == exception?.errorCode) {
                return false
            }
            throw e
        }
    }

    override suspend fun doesObjectExist(bucket: String, key: String, versionId: String?): Boolean {
        try {
            getObjectMeta(
                GetObjectMetaRequest {
                    this.bucket = bucket
                    this.key = key
                }
            )
            return true
        } catch (e: Exception) {
            val exception = e.cause as? ServiceException
            if ("NoSuchKey" == exception?.errorCode ||
                (exception?.statusCode == 404 && "BadErrorResponse" == exception.errorCode)
            ) {
                return false
            }
            throw e
        }
    }

    override suspend fun presign(request: GetObjectRequest, options: PresignOptions?): PresignResult {
        return Presigner.getObject(this.clientImpl, request, options)
    }

    override suspend fun presign(request: PutObjectRequest, options: PresignOptions?): PresignResult {
        return Presigner.putObject(this.clientImpl, request, options)
    }

    override suspend fun presign(request: HeadObjectRequest, options: PresignOptions?): PresignResult {
        return Presigner.headObject(this.clientImpl, request, options)
    }

    override suspend fun presign(request: InitiateMultipartUploadRequest, options: PresignOptions?): PresignResult {
        return Presigner.initiateMultipartUpload(this.clientImpl, request, options)
    }

    override suspend fun presign(request: UploadPartRequest, options: PresignOptions?): PresignResult {
        return Presigner.uploadPart(this.clientImpl, request, options)
    }

    override suspend fun presign(request: CompleteMultipartUploadRequest, options: PresignOptions?): PresignResult {
        return Presigner.completeMultipartUpload(this.clientImpl, request, options)
    }

    override suspend fun presign(request: AbortMultipartUploadRequest, options: PresignOptions?): PresignResult {
        return Presigner.abortMultipartUploadRequest(this.clientImpl, request, options)
    }
}
