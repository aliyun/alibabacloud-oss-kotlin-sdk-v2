@file:OptIn(ExperimentalTime::class)
@file:Suppress("UNCHECKED_CAST")

package com.aliyun.kotlin.sdk.service.oss2.internal

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

internal class ExpiringValue<T>(val threshold: Duration) {

    private sealed class State
    private class NoValue : State()
    private class WaitingOnValue<T>(val task: Deferred<T>) : State()
    private class WithValue<T>(val value: T, val expires: Instant) : State()
    private class WithValueAndWaiting<T>(val value: T, val expires: Instant, val task: Deferred<T>) : State()

    private class Error(val cause: Throwable) : State()

    private var state: State = NoValue()

    constructor() : this(2.seconds) {
    }

    suspend fun getValue(getExpiringValue: suspend () -> Pair<T, Instant>): T {
        var task: Deferred<T>? = null
        var result: T? = null

        when (val x = state) {
            is NoValue -> {
                task = getValueTask(getExpiringValue)
                state = WaitingOnValue(task)
                task.start()
            }
            is WaitingOnValue<*> -> {
                task = x.task as Deferred<T>
            }
            is WithValue<*> -> {
                val now = Clock.System.now()
                if (x.expires <= now) {
                    // value has expired, create new task to update value and
                    // return the result of that task
                    task = getValueTask(getExpiringValue)
                    state = WaitingOnValue(task)
                    task.start()
                } else if (x.expires - now < threshold) {
                    // value is about to expire, create new task to update value and
                    // return current value
                    task = getValueTask(getExpiringValue)
                    state = WithValueAndWaiting(x.value, x.expires, task)
                    task.start()
                    result = x.value as T
                } else {
                    result = x.value as T
                }
            }
            is WithValueAndWaiting<*> -> {
                val now = Clock.System.now()
                if (x.expires <= now) {
                    // value has expired, create new task to update value and
                    // return the result of that task
                    task = x.task as Deferred<T>
                } else {
                    result = x.value as T
                }
            }
            is Error -> {
                task = getValueTask(getExpiringValue)
                state = WaitingOnValue(task)
                task.start()
            }
        }

        if (result != null) {
            return result
        }

        try {
            return requireNotNull(task).await()
        } catch (e: Exception) {
            // reset to init state
            this.state = Error(e)
            throw e
        }
    }

    // / Create task that will return a new version of the value and a date it will expire
    private fun getValueTask(getExpiringValue: suspend () -> Pair<T, Instant>): Deferred<T> {
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        return scope.async(EmptyCoroutineContext, CoroutineStart.LAZY) {
            updateExpiringValue(getExpiringValue())
        }
    }

    private fun updateExpiringValue(result: Pair<T, Instant>): T {
        this.state = WithValue(result.first, result.second)
        return result.first
    }

    fun cancel() {
        when (val x = state) {
            is WaitingOnValue<*> -> {
                x.task.cancel()
            }
            is NoValue, is WithValue<*>, is Error -> {}
            is WithValueAndWaiting<*> -> {
                x.task.cancel()
            }
        }
    }
}
