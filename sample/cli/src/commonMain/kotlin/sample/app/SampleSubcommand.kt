package sample.app

import com.aliyun.kotlin.sdk.service.oss2.models.AbortMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.AbortMultipartUploadRequest.Companion.invoke
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalCli::class)
abstract class SampleSubcommand(
    name: String,
    actionDescription: String
): Subcommand(name, actionDescription) {
    override fun execute() {
        runBlocking {
            try {
                executeCommand()
            } catch (e: Exception) {
                println(e.message ?: e.toString())
            }
        }
    }

    abstract suspend fun executeCommand()
}