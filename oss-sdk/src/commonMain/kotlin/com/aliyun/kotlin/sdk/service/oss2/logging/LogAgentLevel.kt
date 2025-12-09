package com.aliyun.kotlin.sdk.service.oss2.logging

public enum class LogAgentLevel(private val levelInt: Int, private val levelStr: String) {
    TRACE(5, "TRACE"),
    DEBUG(4, "DEBUG"),
    INFO(3, "INFO"),
    WARN(2, "WARN"),
    ERROR(1, "ERROR"),
    OFF(0, "OFF"),
    ;

    public fun toInt(): Int {
        return levelInt
    }

    /** Returns the string representation of this Level. */
    override fun toString(): String {
        return levelStr
    }
}
