package com.aliyun.oss.verify.app

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSProcessInfo
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.Foundation.NSURLSession
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.dataTaskWithRequest
import kotlin.coroutines.resume

@OptIn(ExperimentalForeignApi::class)
internal actual fun getPlatformEnv(name: String): String? {
    return NSProcessInfo.processInfo.environment[name] as? String
}

@OptIn(BetaInteropApi::class)
internal actual suspend fun sendRequest(url: String): String? {
    return suspendCancellableCoroutine { continuation ->
        val request = NSURLRequest(uRL = NSURL(string = url))
        val task = NSURLSession.sharedSession.dataTaskWithRequest(request) { data, _, error ->
            if (error != null) {
                print("Error: $error")
                continuation.resume(null)
                return@dataTaskWithRequest
            }
            val result = data?.let {
                NSString.create(data = it, encoding = NSUTF8StringEncoding).toString()
            }
            continuation.resume(result)
        }
        task.resume()
    }
}
