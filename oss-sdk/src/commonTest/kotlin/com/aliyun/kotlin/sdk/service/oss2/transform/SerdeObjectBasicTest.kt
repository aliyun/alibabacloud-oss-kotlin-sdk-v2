package com.aliyun.kotlin.sdk.service.oss2.transform

import com.aliyun.kotlin.sdk.service.oss2.exceptions.DeserializationException
import com.aliyun.kotlin.sdk.service.oss2.models.Delete
import com.aliyun.kotlin.sdk.service.oss2.models.JobParameters
import com.aliyun.kotlin.sdk.service.oss2.models.ObjectIdentifier
import com.aliyun.kotlin.sdk.service.oss2.models.RestoreRequest
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class SerdeObjectBasicTest {

    @Test
    fun testFromXmlCopyObject() {
        // body is null
        assertFailsWith<DeserializationException> { fromXmlCopyObject(null) }

        // body is unexpected
        assertFailsWith<DeserializationException> { fromXmlCopyObject("<a></a>".toByteArray()) }

        // normal
        val xml = """
            <CopyObjectResult>
            <ETag>"C4CA4238A0B923820DCC509A6F75****"</ETag>
            <LastModified>2019-04-09T03:45:32.000Z</LastModified>
            </CopyObjectResult>
        """.trimIndent()
        val result = fromXmlCopyObject(xml.toByteArray())
        assertEquals("\"C4CA4238A0B923820DCC509A6F75****\"", result.eTag)
        assertEquals("2019-04-09T03:45:32.000Z", result.lastModified)
    }

    @Test
    fun testToXmlRestoreRequest() {
        var restoreRequest = RestoreRequest.Builder().build()
        runBlocking {
            assertEquals("<RestoreRequest></RestoreRequest>", String(toXmlRestoreRequest(restoreRequest).toByteArray()))
        }

        restoreRequest = RestoreRequest.Builder().apply {
            days = 2
            jobParameters = JobParameters.Builder().apply {
                tier = "Standard"
            }.build()
        }.build()
        runBlocking {
            val actual = String(toXmlRestoreRequest(restoreRequest).toByteArray())
            val xml = """
                <RestoreRequest>
                <Days>2</Days>
                <JobParameters>
                <Tier>Standard</Tier>
                </JobParameters>
                </RestoreRequest>
            """.trimIndent().replace("\n", "")
            assertEquals(xml, actual)
        }
    }

    @Test
    fun testToXmlDeleteMultipleObjects() = runTest {
        assertEquals("<Delete></Delete>", String(toXmlDeleteMultipleObjects(Delete {}).toByteArray()))

        val actual = String(
            toXmlDeleteMultipleObjects(
                Delete {
                    quiet = true
                    objects = listOf(
                        ObjectIdentifier {
                            key = "key1"
                            versionId = "versionId1"
                        },
                        ObjectIdentifier {
                            key = "key2"
                            versionId = "versionId2"
                        }
                    )
                }
            ).toByteArray()
        )
        val xml = """
                <Delete>
                <Quiet>true</Quiet>
                <Object>
                <Key>key1</Key>
                <VersionId>versionId1</VersionId>
                </Object>
                <Object>
                <Key>key2</Key>
                <VersionId>versionId2</VersionId>
                </Object>
                </Delete>
        """.trimIndent().replace("\n", "")
        assertEquals(xml, actual)
    }

    @Test
    fun testFromXmlDeleteMultipleObjects() {
        // body is null
        var result = fromXmlDeleteMultipleObjects(null)
        assertNull(result.deletedObjects)
        assertNull(result.encodingType)

        // body is unexpected
        assertFailsWith<DeserializationException> { fromXmlDeleteMultipleObjects("<a></a>".toByteArray()) }

        // normal
        var xml = """
            <DeleteResult>
            <Deleted>
            <Key>a%2fmultipart.data</Key>
            <VersionId>versionId1</VersionId>
            <DeleteMarker>true</DeleteMarker>
            <DeleteMarkerVersionId>CAEQMhiBgIDXiaaB0BYiIGQzYmRkZGUxMTM1ZDRjOTZhNjk4YjRjMTAyZjhl****</DeleteMarkerVersionId>
            </Deleted>
            <Deleted>
            <Key>a%2ftest.jpg</Key>
            <VersionId>versionId2</VersionId>
            <DeleteMarker>false</DeleteMarker>
            <DeleteMarkerVersionId>CAEQMhiBgIDB3aWB0BYiIGUzYTA3YzliMzVmNzRkZGM5NjllYTVlMjYyYWEy****</DeleteMarkerVersionId>
            </Deleted>
            </DeleteResult>
        """.trimIndent()
        result = fromXmlDeleteMultipleObjects(xml.toByteArray())
        assertEquals(2, result.deletedObjects?.size)
        assertEquals("a%2fmultipart.data", result.deletedObjects?.first()?.key)
        assertEquals("versionId1", result.deletedObjects?.first()?.versionId)
        assertEquals(true, result.deletedObjects?.first()?.deleteMarker)
        assertEquals(
            "CAEQMhiBgIDXiaaB0BYiIGQzYmRkZGUxMTM1ZDRjOTZhNjk4YjRjMTAyZjhl****",
            result.deletedObjects?.first()?.deleteMarkerVersionId
        )
        assertEquals("a%2ftest.jpg", result.deletedObjects?.last()?.key)
        assertEquals("versionId2", result.deletedObjects?.last()?.versionId)
        assertEquals(false, result.deletedObjects?.last()?.deleteMarker)
        assertEquals(
            "CAEQMhiBgIDB3aWB0BYiIGUzYTA3YzliMzVmNzRkZGM5NjllYTVlMjYyYWEy****",
            result.deletedObjects?.last()?.deleteMarkerVersionId
        )

        // url encoding
        xml = """
            <DeleteResult>
            <Deleted>
            <Key>a%2fmultipart.data</Key>
            <VersionId>versionId1</VersionId>
            <DeleteMarker>true</DeleteMarker>
            <DeleteMarkerVersionId>CAEQMhiBgIDXiaaB0BYiIGQzYmRkZGUxMTM1ZDRjOTZhNjk4YjRjMTAyZjhl****</DeleteMarkerVersionId>
            </Deleted>
            <Deleted>
            <Key>a%2ftest.jpg</Key>
            <VersionId>versionId2</VersionId>
            <DeleteMarker>false</DeleteMarker>
            <DeleteMarkerVersionId>CAEQMhiBgIDB3aWB0BYiIGUzYTA3YzliMzVmNzRkZGM5NjllYTVlMjYyYWEy****</DeleteMarkerVersionId>
            </Deleted>
            <EncodingType>url</EncodingType>
            </DeleteResult>
        """.trimIndent()
        result = fromXmlDeleteMultipleObjects(xml.toByteArray())
        assertEquals(2, result.deletedObjects?.size)
        assertEquals("a/multipart.data", result.deletedObjects?.first()?.key)
        assertEquals("versionId1", result.deletedObjects?.first()?.versionId)
        assertEquals(true, result.deletedObjects?.first()?.deleteMarker)
        assertEquals(
            "CAEQMhiBgIDXiaaB0BYiIGQzYmRkZGUxMTM1ZDRjOTZhNjk4YjRjMTAyZjhl****",
            result.deletedObjects?.first()?.deleteMarkerVersionId
        )
        assertEquals("a/test.jpg", result.deletedObjects?.last()?.key)
        assertEquals("versionId2", result.deletedObjects?.last()?.versionId)
        assertEquals(false, result.deletedObjects?.last()?.deleteMarker)
        assertEquals(
            "CAEQMhiBgIDB3aWB0BYiIGUzYTA3YzliMzVmNzRkZGM5NjllYTVlMjYyYWEy****",
            result.deletedObjects?.last()?.deleteMarkerVersionId
        )
    }
}
