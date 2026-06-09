package com.aliyun.kotlin.sdk.service.oss2.types

public class FeatureFlagsType private constructor(
    private var value: Int,
) {

    public fun getValue(): Int {
        return value
    }

    public fun contains(flag: FeatureFlagsType): Boolean {
        return value and flag.value != 0
    }

    public fun insert(flag: FeatureFlagsType) {
        this.value = value or flag.value
    }

    public fun remove(flag: FeatureFlagsType) {
        this.value = value and flag.value.inv()
    }

    public fun copy(): FeatureFlagsType {
        return FeatureFlagsType(value)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return other is FeatureFlagsType && value == other.value
    }

    override fun hashCode(): Int = value

    override fun toString(): String = "FeatureFlagsType(value=$value)"

    public companion object {
        /**
         * If the client time is different from server time by more than about 15 minutes,
         * the requests your application makes will be signed with the incorrect time, and the server will reject them.
         * The feature to help to identify this case, and SDK will correct for clock skew.
         */
        public val CORRECT_CLOCK_SKEW: FeatureFlagsType = FeatureFlagsType(1 shl 0)

        /**
         * Content-Type is automatically added based on the object name if not specified.
         * This feature takes effect for PutObject, AppendObject and InitiateMultipartUpload
         */
        public val AUTO_DETECT_MIMETYPE: FeatureFlagsType = FeatureFlagsType(1 shl 1)

        /**
         * Check data integrity of uploads via the crc64.
         * This feature takes effect for PutObject, AppendObject, UploadPart, Uploader.UploadFrom and Uploader.UploadFile
         */
        public val ENABLE_CRC64_CHECK_UPLOAD: FeatureFlagsType = FeatureFlagsType(1 shl 2)

        /**
         * Check data integrity of downloads via the crc64.
         * This feature takes effect for Downloader.DownloadFile
         */
        public val ENABLE_CRC64_CHECK_DOWNLOAD: FeatureFlagsType = FeatureFlagsType(1 shl 3)

        public fun combine(vararg flags: FeatureFlagsType): FeatureFlagsType {
            var v = 0
            for (f in flags) {
                v = v or f.value
            }
            return FeatureFlagsType(v)
        }
    }
}
