package com.aliyun.kotlin.sdk.service.oss2.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MimeUtilsTests {

    @Test
    fun testGetMimiType() {
        val filePath1 = "/dir/dir/file.png"
        val filePath2 = "/dir/file"
        val filePath3 = "/dir/dir/"

        var mimeType = MimeUtils.getMimetype(filePath1)
        assertEquals(mimeType, "image/png")

        mimeType = MimeUtils.getMimetype(filePath2)
        assertNull(mimeType)

        mimeType = MimeUtils.getMimetype(filePath3)
        assertNull(mimeType)

        mimeType = MimeUtils.getMimetype(filePath3, "mimetype/test")
        assertEquals("mimetype/test", mimeType)

        val fileName1 = "file.png"
        val fileName2 = "file"
        val fileName3 = "file."
        val fileName4 = "file/"
        val fileName5 = "file\\"
        val fileName6 = "file:"

        mimeType = MimeUtils.getMimetype(fileName1)
        assertEquals(mimeType, "image/png")

        mimeType = MimeUtils.getMimetype(fileName2)
        assertNull(mimeType)

        mimeType = MimeUtils.getMimetype(fileName3)
        assertNull(mimeType)

        mimeType = MimeUtils.getMimetype(fileName4)
        assertNull(mimeType)

        mimeType = MimeUtils.getMimetype(fileName5)
        assertNull(mimeType)

        mimeType = MimeUtils.getMimetype(fileName6)
        assertNull(mimeType)
    }

    @Test
    fun testGetUserMimetype() {
        MimeUtils.addMimeType(
            mapOf(
                ".png" to "user/png"
            )
        )
        var mimeType = MimeUtils.getMimetype("file.png")
        assertEquals("user/png", mimeType)

        MimeUtils.clearMimeType()
        mimeType = MimeUtils.getMimetype("file.png")
        assertEquals("image/png", mimeType)
    }
}
