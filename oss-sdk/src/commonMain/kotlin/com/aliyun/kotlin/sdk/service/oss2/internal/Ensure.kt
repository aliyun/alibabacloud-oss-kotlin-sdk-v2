package com.aliyun.kotlin.sdk.service.oss2.internal

/**
 * Utility class for validating data formats used in OSS operations
 */
internal object Ensure {
    /**
     * Regular expression for validating bucket name format
     */
    private val BUCKET_NAMING_PATTERN = Regex("^[a-z0-9][a-z0-9-_]{1,61}[a-z0-9]$").toPattern()

    /**
     * Regular expression for validating region format
     */
    private val REGION_PATTERN = Regex("^[a-z0-9-]+$").toPattern()

    /**
     * Checks whether the given string is a valid region identifier
     *
     * @param value The string to validate
     * @return true if the string matches the region format, false otherwise
     */
    fun isValidRegion(value: String?): Boolean {
        if (value == null) {
            return false
        }
        return REGION_PATTERN.matcher(value).matches()
    }

    /**
     * Validates whether the given string is a properly formatted bucket name
     *
     * @param value The bucket name to validate
     * @return true if the name conforms to the naming rules, false otherwise
     */
    fun isValidateBucketName(value: String?): Boolean {
        if (value == null) {
            return false
        }

        return BUCKET_NAMING_PATTERN.matcher(value).matches()
    }

    /**
     * Validates whether the given string is a valid object key (Object Name)
     *
     * @param value The object key to validate
     * @return true if the length is within limits and not empty, false otherwise
     */
    fun isValidateObjectName(value: String?): Boolean {
        if (value == null || value.isEmpty()) {
            return false
        }
        return value.length < 1024
    }
}
