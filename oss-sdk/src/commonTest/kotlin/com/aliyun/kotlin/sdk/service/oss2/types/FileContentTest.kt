package com.aliyun.kotlin.sdk.service.oss2.types

import kotlinx.io.Buffer
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.files.SystemTemporaryDirectory
import kotlinx.io.readByteArray
import kotlinx.io.write
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

/**
 * Exercises [FileContent] and the positioned-read file source behind it across every platform.
 * File-backed cases run through [withTempFile], which writes a temp file and skips the assertions on
 * runtimes without a filesystem (browser js/wasm); Node (js/wasm), the JVM, and future native targets
 * all run them. Pure-validation cases construct [FileContent] directly, needing no real file.
 */
class FileContentTest {

    private val data: ByteArray = ByteArray(10_000) { (it % 251).toByte() }

    private fun withTempFile(bytes: ByteArray, block: (Path) -> Unit) {
        val path = runCatching {
            val dir = Path("$SystemTemporaryDirectory/kotlin-sdk-filecontent-test")
            SystemFileSystem.createDirectories(dir)
            val p = Path("$dir/data-${Random.nextLong()}")
            SystemFileSystem.sink(p).buffered().use { it.write(bytes) }
            p
        }.getOrNull() ?: return
        try {
            block(path)
        } finally {
            SystemFileSystem.delete(path, mustExist = false)
        }
    }

    private fun FileContent.readAll(): ByteArray = readFrom().buffered().use { it.readByteArray() }

    @Test
    fun readsWholeFileWhenNoRange() {
        withTempFile(data) { path ->
            val content = FileContent(path)
            assertEquals(data.size.toLong(), content.contentLength)
            assertContentEquals(data, content.readAll())
        }
    }

    @Test
    fun readsFromOffsetToEnd() {
        withTempFile(data) { path ->
            val offset = 4096L
            val content = FileContent(path, offset)
            assertEquals(data.size - offset, content.contentLength)
            assertContentEquals(data.copyOfRange(offset.toInt(), data.size), content.readAll())
        }
    }

    @Test
    fun readsExactRange() {
        withTempFile(data) { path ->
            val offset = 1234L
            val length = 5000L
            val content = FileContent(path, offset, length)
            assertEquals(length, content.contentLength)
            assertContentEquals(
                data.copyOfRange(offset.toInt(), (offset + length).toInt()),
                content.readAll(),
            )
        }
    }

    @Test
    fun readsShortRange() {
        withTempFile(data) { path ->
            val offset = 500L
            val length = 32L
            val content = FileContent(path, offset, length)
            assertContentEquals(
                data.copyOfRange(offset.toInt(), (offset + length).toInt()),
                content.readAll(),
            )
        }
    }

    @Test
    fun boundsLengthFromStartWithoutSeek() {
        withTempFile(data) { path ->
            val length = 100L
            val content = FileContent(path, 0L, length)
            assertContentEquals(data.copyOfRange(0, length.toInt()), content.readAll())
        }
    }

    @Test
    fun lengthPastEndReadsOnlyAvailableBytes() {
        withTempFile(data) { path ->
            val offset = 9_900L
            val content = FileContent(path, offset, length = 1_000L)
            assertContentEquals(data.copyOfRange(offset.toInt(), data.size), content.readAll())
        }
    }

    @Test
    fun concurrentRangeReadsAreIndependent() {
        withTempFile(data) { path ->
            val a = FileContent(path, 0L, 3_000L)
            val b = FileContent(path, 3_000L, 3_000L)
            val sourceA = a.readFrom().buffered()
            val sourceB = b.readFrom().buffered()
            // Interleave reads from two ranges of the same file; positions must not interfere.
            val firstA = sourceA.readByteArray(1_000)
            val firstB = sourceB.readByteArray(1_000)
            val restA = sourceA.readByteArray()
            val restB = sourceB.readByteArray()
            sourceA.close()
            sourceB.close()
            assertContentEquals(data.copyOfRange(0, 3_000), firstA + restA)
            assertContentEquals(data.copyOfRange(3_000, 6_000), firstB + restB)
        }
    }

    @Test
    fun offsetBeyondEndYieldsEmptyContent() {
        withTempFile(data) { path ->
            val content = FileContent(path, data.size + 100L)
            assertEquals(0L, content.contentLength)
            assertContentEquals(ByteArray(0), content.readAll())
        }
    }

    @Test
    fun singleReadServesRangeBeyondScratchSize() {
        // The ranged reader (offset > 0) chunks through a fixed internal scratch buffer. A single
        // readAtMostTo with a byteCount larger than that scratch must still serve the whole range,
        // not silently truncate to the scratch size.
        val big = ByteArray(300_000) { (it % 251).toByte() }
        withTempFile(big) { path ->
            val offset = 1L
            val content = FileContent(path, offset, big.size - offset)
            val source = content.readFrom()
            val sink = Buffer()
            val read = source.readAtMostTo(sink, big.size.toLong())
            source.close()
            assertEquals(big.size - offset, read)
            assertContentEquals(big.copyOfRange(offset.toInt(), big.size), sink.readByteArray())
        }
    }

    @Test
    fun negativeOffsetIsRejected() {
        assertFailsWith<IllegalArgumentException> { FileContent(Path("unused"), -1L) }
    }

    @Test
    fun negativeLengthIsRejected() {
        assertFailsWith<IllegalArgumentException> { FileContent(Path("unused"), 0L, -1L) }
    }

    @Test
    fun fromFileOverloadsBuildFileContent() {
        val whole = ByteStream.fromFile(Path("unused"))
        assertTrue(whole is FileContent)
        assertEquals(0L, whole.offset)
        assertEquals(null, whole.length)

        val ranged = ByteStream.fromFile(Path("unused"), 10L, 20L)
        assertTrue(ranged is FileContent)
        assertEquals(10L, ranged.offset)
        assertEquals(20L, ranged.length)
    }
}
