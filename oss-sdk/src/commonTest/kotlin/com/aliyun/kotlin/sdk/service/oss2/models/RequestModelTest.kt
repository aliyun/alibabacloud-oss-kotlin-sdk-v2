package com.aliyun.kotlin.sdk.service.oss2.models

import kotlin.test.Test
import kotlin.test.assertEquals

class RequestModelTest {

    class TestRequest(builder: Builder) : RequestModel(builder) {
        val hostParm: String? = builder.hostParm

        val headerParm: String?
            get() = headers["x-oss-header-str"]

        val queryParm: String?
            get() = parameters["query-str"]

        companion object {
            operator fun invoke(builder: Builder.() -> Unit): TestRequest =
                Builder().apply(builder).build()
        }

        inline fun copy(block: Builder.() -> Unit = {}): TestRequest = Builder(this).apply(block).build()

        class Builder() : RequestModel.Builder() {
            var hostParm: String? = null

            var headerParm: String?
                set(value) {
                    this.headers["x-oss-header-str"] = requireNotNull(value)
                }
                get() = headers["x-oss-header-str"]

            var queryParm: String?
                set(value) {
                    this.parameters["query-str"] = requireNotNull(value)
                }
                get() = parameters["query-str"]

            fun build(): TestRequest {
                return TestRequest(this)
            }

            constructor(from: TestRequest) : this() {
                this.headers.putAll(from.headers)
                this.parameters.putAll(from.parameters)
                this.hostParm = from.hostParm
            }
        }
    }

    @Test
    fun buildObjectWithDsl() {
        var request = TestRequest {}
        assertEquals(null, request.hostParm)
        assertEquals(null, request.headerParm)
        assertEquals(null, request.queryParm)

        request = TestRequest {
            hostParm = "host-value"
            headerParm = "h-value"
            queryParm = "p-value"
        }

        assertEquals("host-value", request.hostParm)
        assertEquals("h-value", request.headerParm)
        assertEquals("p-value", request.queryParm)
    }

    @Test
    fun buildObjectWithCopy() {
        // empty
        var request = TestRequest {}
        assertEquals(null, request.hostParm)
        assertEquals(null, request.headerParm)
        assertEquals(null, request.queryParm)

        var copy = request.copy {}
        assertEquals(null, copy.hostParm)
        assertEquals(null, copy.headerParm)
        assertEquals(null, copy.queryParm)

        // full
        request = TestRequest {
            hostParm = "host-value"
            headerParm = "h-value"
            queryParm = "p-value"
        }
        assertEquals("host-value", request.hostParm)
        assertEquals("h-value", request.headerParm)
        assertEquals("p-value", request.queryParm)

        copy = request.copy {}
        assertEquals("host-value", copy.hostParm)
        assertEquals("h-value", copy.headerParm)
        assertEquals("p-value", copy.queryParm)

        // full + replace some filed
        copy = request.copy {
            hostParm = "host-value-1"
            headerParm = "h-value-1"
        }
        assertEquals("host-value-1", copy.hostParm)
        assertEquals("h-value-1", copy.headerParm)
        assertEquals("p-value", copy.queryParm)

        // part
        request = TestRequest {
            queryParm = "p-value"
        }
        copy = request.copy {}
        assertEquals(null, copy.hostParm)
        assertEquals(null, copy.headerParm)
        assertEquals("p-value", copy.queryParm)

        copy = request.copy {
            hostParm = "host-value"
            headerParm = "h-value"
        }
        assertEquals("host-value", copy.hostParm)
        assertEquals("h-value", copy.headerParm)
        assertEquals("p-value", copy.queryParm)
    }
}
