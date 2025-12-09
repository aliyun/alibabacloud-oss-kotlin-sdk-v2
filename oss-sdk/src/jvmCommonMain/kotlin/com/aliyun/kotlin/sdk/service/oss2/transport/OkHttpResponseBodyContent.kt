package com.aliyun.kotlin.sdk.service.oss2.transport

import com.aliyun.kotlin.sdk.service.oss2.types.Abortable
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import kotlinx.io.RawSource

public class OkHttpResponseBodyContent(
    private val body: okhttp3.ResponseBody,
) : ByteStream.SourceStream(), AutoCloseable, Abortable {

    override val contentLength: Long? = body.contentLength()

    override val isOneShot: Boolean = true

    override fun readFrom(): RawSource {
        return this.body.source().asRawSource()
    }

    public override fun close() {
        this.body.close()
    }

    public override fun abort() {
        this.body.close()
    }

    /**
     * Unwraps this instance by returning the underlying {@link okhttp3.ResponseBody}.
     *
     * @return the underlying {@link okhttp3.ResponseBody}.
     */
    public fun unwrap(): okhttp3.ResponseBody {
        return body
    }
}
