package com.aliyun.kotlin.sdk.service.oss2.agentic.transform

import com.aliyun.kotlin.sdk.service.oss2.OperationOutput
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.AgenticBucketStatus
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.CreateAgenticBucketConfiguration
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.CreateAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.DeleteAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.GetAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListAgenticBucketsRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListBucketSpacesRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.PutAgenticBucketStatusRequest
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SerdeAgenticBucketBasicTest {

    @Test
    fun testFromCreateAgenticBucket() = runTest {
        val request = CreateAgenticBucketRequest {
            bucket = "example-1250000000-cn-hangzhou-ab-apsr"
            createAgenticBucketConfiguration = CreateAgenticBucketConfiguration {
                storageClass = "Standard"
                dataRedundancyType = "LRS"
            }
        }
        val input = SerdeAgenticBucketBasic.fromCreateAgenticBucket(request)

        assertEquals("CreateAgenticBucket", input.opName)
        assertEquals("PUT", input.method)
        assertEquals("example-1250000000-cn-hangzhou-ab-apsr", input.bucket)
        assertEquals("", input.parameters["agenticBucket"])
        assertEquals("application/xml", input.headers["Content-Type"])
        assertNotNull(input.headers["Content-MD5"])
        val body = input.body!!.toByteArray().decodeToString()
        assertTrue(body.contains("<CreateAgenticBucketConfiguration>"))
        assertTrue(body.contains("<StorageClass>Standard</StorageClass>"))
        assertTrue(body.contains("<DataRedundancyType>LRS</DataRedundancyType>"))
    }

    @Test
    fun testFromDeleteAgenticBucket() {
        val request = DeleteAgenticBucketRequest {
            bucket = "example-1250000000-cn-hangzhou-ab-apsr"
        }
        val input = SerdeAgenticBucketBasic.fromDeleteAgenticBucket(request)

        assertEquals("DeleteAgenticBucket", input.opName)
        assertEquals("DELETE", input.method)
        assertEquals("example-1250000000-cn-hangzhou-ab-apsr", input.bucket)
        assertEquals("", input.parameters["agenticBucket"])
        assertNull(input.body)
        assertEquals("1B2M2Y8AsgTpgAmY7PhCfg==", input.headers["Content-MD5"])
    }

    @Test
    fun testFromGetAgenticBucket() {
        val request = GetAgenticBucketRequest {
            bucket = "example-1250000000-cn-hangzhou-ab-apsr"
        }
        val input = SerdeAgenticBucketBasic.fromGetAgenticBucket(request)

        assertEquals("GetAgenticBucket", input.opName)
        assertEquals("GET", input.method)
        assertEquals("", input.parameters["agenticBucket"])
    }

    @Test
    fun testFromListAgenticBuckets() {
        val request = ListAgenticBucketsRequest {
            continuationToken = "token-1"
            maxKeys = 50
        }
        val input = SerdeAgenticBucketBasic.fromListAgenticBuckets(request)

        assertEquals("ListAgenticBuckets", input.opName)
        assertEquals("GET", input.method)
        assertNull(input.bucket)
        assertEquals("", input.parameters["agenticBucket"])
        assertEquals("token-1", input.parameters["continuation-token"])
        assertEquals("50", input.parameters["max-keys"])
    }

    @Test
    fun testFromPutAgenticBucketStatus() = runTest {
        val request = PutAgenticBucketStatusRequest {
            bucket = "example-1250000000-cn-hangzhou-ab-apsr"
            agenticBucketStatus = AgenticBucketStatus { status = "Enabled" }
        }
        val input = SerdeAgenticBucketBasic.fromPutAgenticBucketStatus(request)

        assertEquals("PutAgenticBucketStatus", input.opName)
        assertEquals("PUT", input.method)
        assertEquals("", input.parameters["agenticBucket"])
        assertEquals("", input.parameters["status"])
        val body = input.body!!.toByteArray().decodeToString()
        assertEquals("<AgenticBucketStatus><Status>Enabled</Status></AgenticBucketStatus>", body)
    }

    @Test
    fun testFromListBucketSpaces() {
        val request = ListBucketSpacesRequest {
            bucket = "example-1250000000-cn-hangzhou-ab-apsr"
            prefix = "space-"
            startAfter = "space-000"
            maxKeys = 20
        }
        val input = SerdeAgenticBucketBasic.fromListBucketSpaces(request)

        assertEquals("ListBucketSpaces", input.opName)
        assertEquals("GET", input.method)
        assertEquals("", input.parameters["agenticBucket"])
        assertEquals("", input.parameters["bucketSpace"])
        assertEquals("space-", input.parameters["prefix"])
        assertEquals("space-000", input.parameters["start-after"])
        assertEquals("20", input.parameters["max-keys"])
    }

    @Test
    fun testToGetAgenticBucket() = runTest {
        val xml = """
            <AgenticBucketInfo>
            <Name>example</Name>
            <Owner>1250000000</Owner>
            <Region>cn-hangzhou</Region>
            <StorageClass>Standard</StorageClass>
            <DataRedundancyType>LRS</DataRedundancyType>
            <Status>Enabled</Status>
            <BucketResourceType>AgenticBucket</BucketResourceType>
            <CreateTime>2026-07-10T00:00:00.000Z</CreateTime>
            <ACL>private</ACL>
            <PublicAccessBlock>true</PublicAccessBlock>
            <ServerSideEncryptionRule>
            <SSEAlgorithm>KMS</SSEAlgorithm>
            <KMSMasterKeyID>key-id</KMSMasterKeyID>
            <KMSDataEncryption>SM4</KMSDataEncryption>
            </ServerSideEncryptionRule>
            <Versioning>Enabled</Versioning>
            <BucketPolicy>policy</BucketPolicy>
            </AgenticBucketInfo>
        """.trimIndent()
        val output = OperationOutput {
            status = "OK"
            statusCode = 200
            body = ByteStream.fromString(xml)
        }
        val result = SerdeAgenticBucketBasic.toGetAgenticBucket(output)
        val info = assertNotNull(result.agenticBucketInfo)
        assertEquals("example", info.name)
        assertEquals("1250000000", info.owner)
        assertEquals("cn-hangzhou", info.region)
        assertEquals("Standard", info.storageClass)
        assertEquals("LRS", info.dataRedundancyType)
        assertEquals("Enabled", info.status)
        assertEquals("AgenticBucket", info.bucketResourceType)
        assertEquals("2026-07-10T00:00:00.000Z", info.createTime)
        assertEquals("private", info.acl)
        assertEquals("true", info.publicAccessBlock)
        assertEquals("Enabled", info.versioning)
        assertEquals("policy", info.bucketPolicy)
        assertEquals("KMS", info.serverSideEncryptionRule?.sSEAlgorithm)
        assertEquals("key-id", info.serverSideEncryptionRule?.kMSMasterKeyID)
        assertEquals("SM4", info.serverSideEncryptionRule?.kMSDataEncryption)
    }

    @Test
    fun testToListAgenticBuckets() = runTest {
        val xml = """
            <ListAgenticBucketsResult>
            <Region>cn-hangzhou</Region>
            <Owner>1250000000</Owner>
            <ContinuationToken>token-1</ContinuationToken>
            <NextContinuationToken>token-2</NextContinuationToken>
            <IsTruncated>true</IsTruncated>
            <AgenticBuckets>
            <AgenticBucket>
            <Name>bucket-a</Name>
            <StorageClass>Standard</StorageClass>
            <DataRedundancyType>LRS</DataRedundancyType>
            <CreateTime>2026-07-10T00:00:00.000Z</CreateTime>
            </AgenticBucket>
            <AgenticBucket>
            <Name>bucket-b</Name>
            <StorageClass>IA</StorageClass>
            <DataRedundancyType>ZRS</DataRedundancyType>
            <CreateTime>2026-07-11T00:00:00.000Z</CreateTime>
            </AgenticBucket>
            </AgenticBuckets>
            </ListAgenticBucketsResult>
        """.trimIndent()
        val output = OperationOutput {
            status = "OK"
            statusCode = 200
            body = ByteStream.fromString(xml)
        }
        val result = SerdeAgenticBucketBasic.toListAgenticBuckets(output)
        assertEquals("cn-hangzhou", result.region)
        assertEquals("1250000000", result.owner)
        assertEquals("token-1", result.continuationToken)
        assertEquals("token-2", result.nextContinuationToken)
        assertEquals(true, result.isTruncated)
        assertEquals(2, result.agenticBuckets?.size)
        assertEquals("bucket-a", result.agenticBuckets?.get(0)?.name)
        assertEquals("Standard", result.agenticBuckets?.get(0)?.storageClass)
        assertEquals("bucket-b", result.agenticBuckets?.get(1)?.name)
        assertEquals("ZRS", result.agenticBuckets?.get(1)?.dataRedundancyType)
    }

    @Test
    fun testToListBucketSpaces() = runTest {
        val xml = """
            <ListBucketSpacesResult>
            <Owner>
            <ID>1250000000</ID>
            <DisplayName>display</DisplayName>
            </Owner>
            <Prefix>space-</Prefix>
            <MaxKeys>100</MaxKeys>
            <ContinuationToken>token-1</ContinuationToken>
            <NextContinuationToken>token-2</NextContinuationToken>
            <StartAfter>space-000</StartAfter>
            <IsTruncated>false</IsTruncated>
            <BucketSpaces>
            <BucketSpace>
            <Name>space-a</Name>
            <Location>oss-cn-hangzhou</Location>
            <CreationDate>2026-07-10T00:00:00.000Z</CreationDate>
            <StorageClass>Standard</StorageClass>
            </BucketSpace>
            </BucketSpaces>
            </ListBucketSpacesResult>
        """.trimIndent()
        val output = OperationOutput {
            status = "OK"
            statusCode = 200
            body = ByteStream.fromString(xml)
        }
        val result = SerdeAgenticBucketBasic.toListBucketSpaces(output)
        assertEquals("1250000000", result.owner?.id)
        assertEquals("display", result.owner?.displayName)
        assertEquals("space-", result.prefix)
        assertEquals(100, result.maxKeys)
        assertEquals("token-1", result.continuationToken)
        assertEquals("token-2", result.nextContinuationToken)
        assertEquals("space-000", result.startAfter)
        assertEquals(false, result.isTruncated)
        assertEquals(1, result.bucketSpaces?.size)
        assertEquals("space-a", result.bucketSpaces?.get(0)?.name)
        assertEquals("oss-cn-hangzhou", result.bucketSpaces?.get(0)?.location)
        assertEquals("Standard", result.bucketSpaces?.get(0)?.storageClass)
    }
}
