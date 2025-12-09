package com.aliyun.kotlin.sdk.service.oss2.logging

import java.util.logging.Level
import java.util.logging.Logger

internal class LogAgentJul(override val name: String, level: LogAgentLevel) : LogAgent {
    private val julLogger = Logger.getLogger(name)

    init {
        julLogger.level = level.toJULLevel()
    }

    override fun at(
        level: LogAgentLevel,
        block: LogAgentEventBuilder.() -> Unit
    ) {
        val julLevel = level.toJULLevel()
        if (julLogger.isLoggable(julLevel)) {
            LogAgentEventBuilder().apply(block).run {
                julLogger.log(julLevel, this.message, this.cause)
            }
        }
    }

    private fun LogAgentLevel.toJULLevel(): Level {
        val julLevel: Level =
            when (this) {
                LogAgentLevel.TRACE -> Level.FINEST
                LogAgentLevel.DEBUG -> Level.FINE
                LogAgentLevel.INFO -> Level.INFO
                LogAgentLevel.WARN -> Level.WARNING
                LogAgentLevel.ERROR -> Level.SEVERE
                LogAgentLevel.OFF -> Level.OFF
            }
        return julLevel
    }
}
