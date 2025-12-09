package com.aliyun.kotlin.sdk.service.oss2

public class PresignOptions(builder: Builder) {
    /**
     * The expiration time for the generated presign url
     * The epoch number of seconds from the epoch instant 1970-01-01T00:00:00Z
     */
    public val expirationInEpoch: Long? = builder.expirationInEpoch

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PresignOptions =
            Builder().apply(builder).build()
    }

    /**
     * A builder of PresignOptions.
     */
    public class Builder {
        /**
         * The expiration time for the generated presign url。
         * The epoch number of seconds from the epoch instant 1970-01-01T00:00:00Z
         */
        public var expirationInEpoch: Long? = null

        /**
         * Returns a new instance built from the current state of this builder.
         *
         * @return a new PresignOptions
         */
        public fun build(): PresignOptions {
            return PresignOptions(this)
        }
    }
}
