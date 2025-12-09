package com.aliyun.kotlin.sdk.service.oss2.transform

import com.aliyun.kotlin.sdk.service.oss2.exceptions.DeserializationException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SerdeBucketAclTest {

    @Test
    fun testFromXmlAccessControlPolicy() {
        // body is null
        assertFailsWith<DeserializationException> { fromXmlAccessControlPolicy(null) }

        // body is unexpected
        assertFailsWith<DeserializationException> { fromXmlAccessControlPolicy("<a></a>".toByteArray()) }

        // normal
        val xml = """
            <AccessControlPolicy>
            <Owner>
            <ID>1234513715092****</ID>
            <DisplayName>1234513715092****</DisplayName>
            </Owner>
            <AccessControlList>
            <Grant>public-read</Grant>
            </AccessControlList>
            </AccessControlPolicy>
        """.trimIndent()
        val result = fromXmlAccessControlPolicy(xml.toByteArray())
        assertEquals(result.accessControlList?.grant, "public-read")
        assertEquals(result.owner?.id, "1234513715092****")
        assertEquals(result.owner?.displayName, "1234513715092****")
    }
}
