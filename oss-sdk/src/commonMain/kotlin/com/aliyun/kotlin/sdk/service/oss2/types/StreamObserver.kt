package com.aliyun.kotlin.sdk.service.oss2.types

public abstract class StreamObserver {

    /**
     * Called to indicate that the Stream has been closed.
     */
    public open fun closed() {
        // noop
    }

    /**
     * Called to indicate that Stream's read have been called, and are about to invoke data.
     *
     * @param buffer The byte array, which has been passed to the read call, and where data has been stored.
     * @param offset The offset within the byte array, where data has been stored.
     * @param byteCount The number of bytes, which have been stored in the byte array.
     */
    public open fun data(buffer: ByteArray, offset: Int, byteCount: Int) {
        // noop
    }

    /**
     * Called to indicate that an error occurred on the underlying stream.
     *
     * @param cause the exception to throw
     */
    public open fun error(cause: Throwable) {
        throw cause
    }

    /**
     * Called to indicate that EOF has been seen on the underlying stream. This method may be called multiple times,
     * if the reader keeps invoking either of the read methods, and they will consequently keep returning EOF.
     */
    public open fun finished() {
        // noop
    }

    /**
     * Called to indicate that the state of the underlying stream is reset.
     */
    public open fun reset() {
        // noop
    }
}
