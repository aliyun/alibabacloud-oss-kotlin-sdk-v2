package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.agentic.AgenticBucketClient
import com.aliyun.kotlin.sdk.service.oss2.agentic.BucketSpaceClient
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.AgenticBucketStatus
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.DeleteAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.GetAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListAgenticBucketsRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListBucketSpacesRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.PutAgenticBucketStatusRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.paginator.listAgenticBucketsPaginator
import com.aliyun.kotlin.sdk.service.oss2.agentic.paginator.listBucketSpacesPaginator
import com.aliyun.kotlin.sdk.service.oss2.credentials.StaticCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectsV2Request
import com.aliyun.kotlin.sdk.service.oss2.paginator.listObjectsV2Paginator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.random.Random

/**
 * Shared helpers for the agentic integration tests: client factories, name builders, and the
 * prefix based reaper that bounds the backlog left by the two-phase agentic bucket lifecycle.
 *
 * The service requires `PutAgenticBucketStatus(Disabled)` before `DeleteAgenticBucket`, and the
 * bucket only becomes deletable roughly 24 hours later. A test run therefore cannot delete the
 * buckets it creates; it only marks them Disabled and reclaims the ones left behind by earlier
 * runs whose readiness window has elapsed.
 */
open class AgenticTestBase : TestBase() {

    companion object {
        /**
         * The `ab` marker in the prefix is what the reaper filters on, and `kt` keeps it distinct
         * from the prefixes of the other language SDKs. The prefix is kept short on purpose: the
         * resolved name `{bucket}-{accountId}-{region}-ab-apsr` becomes a host label and must stay
         * within 63 characters.
         */
        const val AGENTIC_BUCKET_NAME_PREFIX: String = "kt-sdk-test-ab-"

        /** The tail the service appends to an agentic bucket name. */
        const val AGENTIC_BUCKET_SUFFIX: String = "ab-apsr"

        /** The tail the service appends to a bucket space name. */
        const val BUCKET_SPACE_SUFFIX: String = "bs-apsr"

        private const val LETTERS: String = "abcdefghijklmnopqrstuvwxyz"
    }

    /** The agentic clients resolve physical names from this account id. */
    val testAccountId: String get() = OSS_TEST_RAM_UID

    /** Agentic tests need an account id and a region on top of the usual credentials. */
    val agenticConfigured: Boolean
        get() = OSS_TEST_RAM_UID.isNotEmpty() && OSS_TEST_REGION.isNotEmpty()

    fun agenticTestConfig(
        validCredentials: Boolean = true,
        pathStyle: Boolean = false,
    ): ClientConfiguration = ClientConfiguration.loadDefault().apply {
        region = OSS_TEST_REGION
        endpoint = OSS_TEST_ENDPOINT
        accountId = OSS_TEST_RAM_UID
        credentialsProvider = if (validCredentials) {
            StaticCredentialsProvider(OSS_TEST_ACCESS_KEY_ID, OSS_TEST_ACCESS_KEY_SECRET)
        } else {
            StaticCredentialsProvider("invalid-ak", "invalid-sk")
        }
        if (pathStyle) {
            usePathStyle = true
        }
    }

    val agenticClient: AgenticBucketClient by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        AgenticBucketClient(agenticTestConfig())
    }

    val invalidAkAgenticClient: AgenticBucketClient by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        AgenticBucketClient(agenticTestConfig(validCredentials = false))
    }

    val bucketSpaceClient: OSSClient by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        BucketSpaceClient.create(agenticTestConfig())
    }

    // Path-style variants: the resolved full name goes into the request path instead of the
    // leftmost host label. Used by the misc path-style scenario.

    val pathStyleAgenticClient: AgenticBucketClient by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        AgenticBucketClient(agenticTestConfig(pathStyle = true))
    }

    val pathStyleBucketSpaceClient: OSSClient by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        BucketSpaceClient.create(agenticTestConfig(pathStyle = true))
    }

    fun randStr(n: Int): String = buildString(n) {
        repeat(n) { append(LETTERS[Random.nextInt(LETTERS.length)]) }
    }

    fun randomAgenticBucketName(): String = AGENTIC_BUCKET_NAME_PREFIX + randStr(6)

    /**
     * Resolves a short name to the server side full name `{bucket}-{accountId}-{region}-{suffix}`.
     */
    fun buildFullName(bucket: String, suffix: String): String =
        "$bucket-$testAccountId-$OSS_TEST_REGION-$suffix"

    /**
     * Strips the resolved tail so a listed physical name can be handed back to a client that
     * re-expands short names.
     */
    fun toShortName(name: String, suffix: String): String =
        name.removeSuffix("-$testAccountId-$OSS_TEST_REGION-$suffix")

    /** Real time sleep; [delay] alone is skipped by the virtual clock of `runTest`. */
    suspend fun sleepSeconds(seconds: Int) {
        withContext(Dispatchers.Default) { delay(seconds * 1000L) }
    }

    /** Joins the messages of the whole causal chain, for assertions on deferred argument errors. */
    fun messageChain(error: Throwable): String {
        val messages = mutableListOf<String>()
        var current: Throwable? = error
        var depth = 0
        while (current != null && depth < 16) {
            current.message?.let { messages.add(it) }
            current = current.cause
            depth++
        }
        return messages.joinToString(" | ")
    }

    /** Returns the [ServiceException] anywhere in the causal chain, or null. */
    fun serviceErrorOf(error: Throwable): ServiceException? = ServiceException.asCause(error)

    /** Reports whether the server refused path-style (second level domain) addressing. */
    fun isSecondLevelDomainForbidden(error: Throwable): Boolean =
        serviceErrorOf(error)?.errorCode == "SecondLevelDomainForbidden"

    /**
     * `kotlin.test` has no portable skip, so a scenario that cannot run logs the reason and the
     * call site returns early instead of failing. Returns true so it can also be used as a guard.
     */
    fun skip(reason: String): Boolean {
        println("[agentic-skip] $reason")
        return true
    }

    /**
     * The shared scenario teardown: disable this run's bucket, then reap buckets left disabled by
     * previous runs. A bucket that stays Enabled can never be reclaimed, so this must run even
     * when the scenario itself failed. Best-effort: every error is swallowed.
     */
    suspend fun disableAndReap(name: String) {
        runCatching {
            agenticClient.putAgenticBucketStatus(
                PutAgenticBucketStatusRequest {
                    bucket = name
                    agenticBucketStatus = AgenticBucketStatus { status = "Disabled" }
                },
            )
        }
        reapDisabledAgenticBuckets()
    }

    /**
     * Deletes leftover buckets from previous runs that carry our prefix and are already Disabled
     * (Enabled ones may belong to a concurrent run), emptying their bucket spaces first.
     * Best-effort: every error is swallowed.
     */
    suspend fun reapDisabledAgenticBuckets() {
        runCatching {
            agenticClient.listAgenticBucketsPaginator(ListAgenticBucketsRequest {}).collect { page ->
                page.agenticBuckets?.forEach { summary ->
                    val listedName = summary.name ?: return@forEach
                    if (!listedName.startsWith(AGENTIC_BUCKET_NAME_PREFIX)) {
                        return@forEach
                    }
                    val shortName = toShortName(listedName, AGENTIC_BUCKET_SUFFIX)
                    // The list summary carries no status, so fetch it; only reclaim Disabled ones.
                    val status = runCatching {
                        agenticClient.getAgenticBucket(
                            GetAgenticBucketRequest { this.bucket = shortName },
                        ).agenticBucketInfo?.status
                    }.getOrNull()
                    if (status != "Disabled") {
                        return@forEach
                    }
                    // A non-empty bucket space blocks the delete, so drain the spaces first.
                    reapBucketSpaces(shortName)
                    runCatching {
                        agenticClient.deleteAgenticBucket(
                            DeleteAgenticBucketRequest { this.bucket = shortName },
                        )
                    }
                }
            }
        }
    }

    /**
     * Empties and deletes every bucket space of a Disabled agentic bucket.
     * Best-effort: every error is swallowed.
     */
    suspend fun reapBucketSpaces(name: String) {
        runCatching {
            agenticClient.listBucketSpacesPaginator(
                ListBucketSpacesRequest { bucket = name },
            ).collect { page ->
                page.bucketSpaces?.forEach { space ->
                    val listedName = space.name ?: return@forEach
                    val spaceName = toShortName(listedName, BUCKET_SPACE_SUFFIX)
                    runCatching {
                        bucketSpaceClient.listObjectsV2Paginator(
                            ListObjectsV2Request { this.bucket = spaceName },
                        ).collect { objects ->
                            objects.contents?.forEach { obj ->
                                runCatching {
                                    bucketSpaceClient.deleteObject(
                                        DeleteObjectRequest {
                                            this.bucket = spaceName
                                            this.key = obj.key
                                        },
                                    )
                                }
                            }
                        }
                    }
                    runCatching {
                        bucketSpaceClient.deleteBucket(DeleteBucketRequest { this.bucket = spaceName })
                    }
                }
            }
        }
    }

    /** Deletes a bucket space created by a scenario. Best-effort. */
    suspend fun deleteBucketSpaceQuietly(name: String) {
        runCatching {
            bucketSpaceClient.deleteBucket(DeleteBucketRequest { bucket = name })
        }
    }
}
