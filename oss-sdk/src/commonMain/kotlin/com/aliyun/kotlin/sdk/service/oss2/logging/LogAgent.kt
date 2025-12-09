package com.aliyun.kotlin.sdk.service.oss2.logging

private fun (() -> Any?).toStringSafe(): String {
    return try {
        invoke().toString()
    } catch (e: Exception) {
        "toStringSafe throw Exception, $e"
    }
}

public class LogAgentEventBuilder {
    /**
     * The message to be logged. `message` can be used with any string interpolation literal.
     */
    public var message: String? = null

    /**
     * Throwable associated with log message.
     */
    public var cause: Throwable? = null

    /*
    /**
     * One-off metadata to attach to this log message.
     */
    // public var metadata: Map<String, String>? = null

    /**
     * The source this log messages originates from.
     * Defaults to the module emitting the log message.
     */
    // public var source: String? = null

    /**
     * The file this log message originates from (there's usually no need to pass it explicitly as it
     * defaults to `#fileID`.
     */
    // public var file: String? = null

    /**
     * The function this log message originates from (there's usually no need to pass it explicitly as
     * it defaults to `#function`).
     */
    // public var function: String? = null

    /**
     * The line this log message originates from (there's usually no need to pass it explicitly as it
     *  defaults to `#line`).
     */
    // public var line: Int? = null
     */
}

public interface LogAgent {

    /**
     * Return the name of this `Logger` instance.
     *
     * @return name of this logger instance
     */
    public val name: String

    /** Lazy add a log message at the TRACE level. */
    public fun trace(message: () -> Any?): Unit =
        at(LogAgentLevel.TRACE) { this.message = message.toStringSafe() }

    /** Lazy add a log message at the DEBUG level. */
    public fun debug(message: () -> Any?): Unit =
        at(LogAgentLevel.DEBUG) { this.message = message.toStringSafe() }

    /** Lazy add a log message at the INFO level. */
    public fun info(message: () -> Any?): Unit =
        at(LogAgentLevel.INFO) { this.message = message.toStringSafe() }

    /** Lazy add a log message at the WARN level. */
    public fun warn(message: () -> Any?): Unit =
        at(LogAgentLevel.WARN) { this.message = message.toStringSafe() }

    /** Lazy add a log message at the ERROR level. */
    public fun error(message: () -> Any?): Unit =
        at(LogAgentLevel.ERROR) { this.message = message.toStringSafe() }

    /** Lazy add a log message if level enabled */
    public fun at(level: LogAgentLevel, block: LogAgentEventBuilder.() -> Unit)

    public companion object {
        public fun default(level: LogAgentLevel): LogAgent {
            return LogAgentFactory.logger("com.aliyun.kotlin.sdk.service.oss2", level)
        }
    }
}
