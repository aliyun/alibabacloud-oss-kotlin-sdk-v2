package com.aliyun.kotlin.sdk.service.oss2.logging

internal class LogAgentPrint(
    override val name: String,
    private val level: LogAgentLevel,
) : LogAgent {
    override fun at(level: LogAgentLevel, block: LogAgentEventBuilder.() -> Unit) {
        // Lower levelInt means higher severity; log when the message is at least as severe
        // as the configured threshold.
        if (level.toInt() > this.level.toInt()) return
        val event = LogAgentEventBuilder().apply(block)
        val message = event.message ?: return
        println("[$level] $name - $message")
        event.cause?.let { println(it.stackTraceToString()) }
    }
}
