package com.aliyun.kotlin.sdk.service.oss2.types

public enum class FeatureFlagsType(
    private var value: Int,
) {
    /**
     * If the client time is different from server time by more than about 15 minutes,
     * the requests your application makes will be signed with the incorrect time, and the server will reject them.
     * The feature to help to identify this case, and SDK will correct for clock skew.
     */
    CORRECT_CLOCK_SKEW(1 shl 0),

    /**
     * Content-Type is automatically added based on the object name if not specified.
     * This feature takes effect for PutObject, AppendObject and InitiateMultipartUpload
     */
    AUTO_DETECT_MIMETYPE(1 shl 1),

    /**
     * Check data integrity of uploads via the crc64.
     * This feature takes effect for PutObject, AppendObject, UploadPart, Uploader.UploadFrom and Uploader.UploadFile
     */
    ENABLE_CRC64_CHECK_UPLOAD(1 shl 2),

    /**
     * Check data integrity of downloads via the crc64.
     * This feature takes effect for Downloader.DownloadFile
     */
    ENABLE_CRC64_CHECK_DOWNLOAD(1 shl 3);

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

    public companion object {
        public fun combine(vararg flags: FeatureFlagsType): FeatureFlagsType? {
            var flag: FeatureFlagsType? = null
            for (f in flags) {
                flag = flag?.let {
                    it.insert(f)
                    it
                } ?: f
            }
            return flag
        }
    }
}
