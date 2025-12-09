package com.aliyun.kotlin.sdk.service.oss2.logging

import android.util.Log

internal class LogAgentAndroid(override val name: String, private val level: LogAgentLevel) : LogAgent {

    override fun at(level: LogAgentLevel, block: LogAgentEventBuilder.() -> Unit) {
        if (isLoggingEnabledFor(level)) {
            LogAgentEventBuilder().apply(block).run {
                when (level) {
                    LogAgentLevel.TRACE -> Log.v(name, this.message, this.cause)
                    LogAgentLevel.DEBUG -> Log.d(name, this.message, this.cause)
                    LogAgentLevel.INFO -> Log.i(name, this.message, this.cause)
                    LogAgentLevel.WARN -> Log.w(name, this.message, this.cause)
                    LogAgentLevel.ERROR -> Log.e(name, this.message, this.cause)
                    LogAgentLevel.OFF -> Unit
                }
            }
        }
    }

    private fun isLoggingEnabledFor(level: LogAgentLevel): Boolean {
        if (this.level.toInt() < level.toInt()) {
            return false
        }

        return when (level) {
            LogAgentLevel.TRACE -> Log.isLoggable(name, Log.VERBOSE)
            LogAgentLevel.DEBUG -> Log.isLoggable(name, Log.DEBUG)
            LogAgentLevel.INFO -> Log.isLoggable(name, Log.INFO)
            LogAgentLevel.WARN -> Log.isLoggable(name, Log.WARN)
            LogAgentLevel.ERROR -> Log.isLoggable(name, Log.ERROR)
            LogAgentLevel.OFF -> false
        }
    }
}
