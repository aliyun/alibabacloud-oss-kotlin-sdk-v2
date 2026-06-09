package com.aliyun.kotlin.sdk.service.oss2.types

import kotlin.test.*

class FeatureFlagsTypeTest {

    @Test
    fun testContains() {
        val flags = FeatureFlagsType.combine(
            FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD,
            FeatureFlagsType.AUTO_DETECT_MIMETYPE
        )
        assertTrue(flags.contains(FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD))
        assertTrue(flags.contains(FeatureFlagsType.AUTO_DETECT_MIMETYPE))
        assertFalse(flags.contains(FeatureFlagsType.CORRECT_CLOCK_SKEW))
        assertFalse(flags.contains(FeatureFlagsType.ENABLE_CRC64_CHECK_DOWNLOAD))
    }

    @Test
    fun testInsert() {
        val flags = FeatureFlagsType.combine(FeatureFlagsType.CORRECT_CLOCK_SKEW)
        assertFalse(flags.contains(FeatureFlagsType.AUTO_DETECT_MIMETYPE))
        flags.insert(FeatureFlagsType.AUTO_DETECT_MIMETYPE)
        assertTrue(flags.contains(FeatureFlagsType.AUTO_DETECT_MIMETYPE))
        assertTrue(flags.contains(FeatureFlagsType.CORRECT_CLOCK_SKEW))
    }

    @Test
    fun testRemove() {
        val flags = FeatureFlagsType.combine(
            FeatureFlagsType.CORRECT_CLOCK_SKEW,
            FeatureFlagsType.AUTO_DETECT_MIMETYPE
        )
        assertTrue(flags.contains(FeatureFlagsType.AUTO_DETECT_MIMETYPE))
        flags.remove(FeatureFlagsType.AUTO_DETECT_MIMETYPE)
        assertFalse(flags.contains(FeatureFlagsType.AUTO_DETECT_MIMETYPE))
        assertTrue(flags.contains(FeatureFlagsType.CORRECT_CLOCK_SKEW))
    }

    @Test
    fun testCombineCreatesNewInstance() {
        val flags = FeatureFlagsType.combine(
            FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD,
            FeatureFlagsType.ENABLE_CRC64_CHECK_DOWNLOAD
        )
        assertNotSame(FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD, flags)
        assertNotSame(FeatureFlagsType.ENABLE_CRC64_CHECK_DOWNLOAD, flags)
    }

    @Test
    fun testCombineDoesNotMutateConstants() {
        val originalValue = FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD.getValue()
        FeatureFlagsType.combine(
            FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD,
            FeatureFlagsType.ENABLE_CRC64_CHECK_DOWNLOAD,
            FeatureFlagsType.AUTO_DETECT_MIMETYPE,
            FeatureFlagsType.CORRECT_CLOCK_SKEW
        )
        assertEquals(originalValue, FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD.getValue())
    }

    @Test
    fun testCopyCreatesIndependentInstance() {
        val flags = FeatureFlagsType.combine(
            FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD,
            FeatureFlagsType.AUTO_DETECT_MIMETYPE
        )
        val copied = flags.copy()
        assertEquals(flags, copied)
        assertNotSame(flags, copied)

        copied.remove(FeatureFlagsType.AUTO_DETECT_MIMETYPE)
        assertTrue(flags.contains(FeatureFlagsType.AUTO_DETECT_MIMETYPE))
        assertFalse(copied.contains(FeatureFlagsType.AUTO_DETECT_MIMETYPE))
    }

    @Test
    fun testMultipleClientIsolation() {
        val defaults = FeatureFlagsType.combine(
            FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD,
            FeatureFlagsType.ENABLE_CRC64_CHECK_DOWNLOAD,
            FeatureFlagsType.AUTO_DETECT_MIMETYPE,
            FeatureFlagsType.CORRECT_CLOCK_SKEW
        )

        val client1Flags = defaults.copy()
        client1Flags.remove(FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD)

        val client2Flags = defaults.copy()

        assertFalse(client1Flags.contains(FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD))
        assertTrue(client2Flags.contains(FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD))
        assertTrue(defaults.contains(FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD))
    }

    @Test
    fun testEquals() {
        val a = FeatureFlagsType.combine(
            FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD,
            FeatureFlagsType.AUTO_DETECT_MIMETYPE
        )
        val b = FeatureFlagsType.combine(
            FeatureFlagsType.AUTO_DETECT_MIMETYPE,
            FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD
        )
        assertEquals(a, b)
        assertEquals(a.hashCode(), b.hashCode())
    }

    @Test
    fun testNotEquals() {
        val a = FeatureFlagsType.combine(FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD)
        val b = FeatureFlagsType.combine(FeatureFlagsType.AUTO_DETECT_MIMETYPE)
        assertNotEquals(a, b)
    }
}
