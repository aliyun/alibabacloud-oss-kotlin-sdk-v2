@file:OptIn(ExperimentalTime::class)

package com.aliyun.kotlin.sdk.service.oss2.credentials

import com.aliyun.kotlin.sdk.service.oss2.internal.ExpiringValue
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

// default refresh time is 300 Second
private const val DEFAULT_INTERVAL = 300

public class RefreshCredentialsProvider(
    public val provider: CredentialsProvider,
    private val refreshInterval: Duration = DEFAULT_INTERVAL.seconds,
) : CredentialsProvider {

    private val expiringCredential = ExpiringValue<Credentials>(refreshInterval)

    override suspend fun getCredentials(): Credentials {
        return expiringCredential.getValue {
            getCredentialAndExpiration(provider)
        }
    }

    private companion object {
        suspend fun getCredentialAndExpiration(provider: CredentialsProvider): Pair<Credentials, Instant> {
            val cred = provider.getCredentials()
            var expiration: Instant? = null
            cred.expiration?.let {
                expiration = Instant.parseOrNull(it)
            }
            return Pair(cred, expiration ?: Instant.DISTANT_FUTURE)
        }
    }
}
