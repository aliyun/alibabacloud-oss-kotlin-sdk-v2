package com.aliyun.kotlin.sdk.service.oss2.transform

import com.aliyun.kotlin.sdk.service.oss2.exceptions.DeserializationException
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SerdeObjectAclTest {

    @Test
    fun testFromXmlObjectAccessControlPolicy() = runTest {
        // body is null
        assertFailsWith<DeserializationException> { fromXmlObjectAccessControlPolicy(null) }

        // body is unexpected
        assertFailsWith<DeserializationException> { fromXmlObjectAccessControlPolicy("<a></a>".toByteArray()) }

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
        val result = fromXmlObjectAccessControlPolicy(xml.toByteArray())
        assertEquals(result.accessControlList?.grant, "public-read")
        assertEquals(result.owner?.id, "1234513715092****")
        assertEquals(result.owner?.displayName, "1234513715092****")
    }
}
