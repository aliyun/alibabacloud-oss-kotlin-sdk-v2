package com.aliyun.kotlin.sdk.service.oss2.types

import kotlinx.io.RawSource
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

public class FileContent(
    public var path: Path,
) : ByteStream.SourceStream() {

    override val contentLength: Long? = SystemFileSystem.metadataOrNull(path)?.size

    override val isOneShot: Boolean = false

    override fun readFrom(): RawSource {
        return SystemFileSystem.source(path)
    }
}
