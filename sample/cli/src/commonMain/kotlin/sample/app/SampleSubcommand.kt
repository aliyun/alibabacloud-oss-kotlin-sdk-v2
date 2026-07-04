package sample.app

import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

@OptIn(ExperimentalCli::class)
abstract class SampleSubcommand(
    name: String,
    actionDescription: String
): Subcommand(name, actionDescription) {
    override fun execute() {
        runCommand {
            try {
                executeCommand()
            } catch (e: Exception) {
                println(e.message ?: e.toString())
            }
        }
    }

    abstract suspend fun executeCommand()
}

expect fun runCommand(block: suspend () -> Unit)