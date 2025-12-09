package com.aliyun.kotlin.sdk.service.oss2

import com.aliyun.kotlin.sdk.service.oss2.types.AuthMethodType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class OperationOptionsTest {

    @Test
    fun buildOperationInputWithEmptyValues() {
        val opt = OperationOptions.build {}
        assertNull(opt.retryMaxAttempts)
        assertNull(opt.readWriteTimeout)
        assertNull(opt.authMethod)

        val defOpt = OperationOptions.Default
        assertNull(defOpt.retryMaxAttempts)
        assertNull(defOpt.readWriteTimeout)
        assertNull(defOpt.authMethod)
    }

    @Test
    fun buildOperationInputWithValues() {
        val opt = OperationOptions.build {
            retryMaxAttempts = 3
            readWriteTimeout = 20.toDuration(DurationUnit.SECONDS)
            authMethod = AuthMethodType.Query
        }

        assertEquals(3, opt.retryMaxAttempts)
        assertEquals(20.toDuration(DurationUnit.SECONDS), opt.readWriteTimeout)
        assertEquals(AuthMethodType.Query, opt.authMethod)
    }
}
