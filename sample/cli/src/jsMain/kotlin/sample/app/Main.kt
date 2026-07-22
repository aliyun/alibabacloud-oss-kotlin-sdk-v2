package sample.app

// Kotlin/JS generates `main([])`, so read Node's argv directly (skip node + script path).
private external val process: dynamic

fun main() {
    val argv = process.argv.slice(2) as Array<String>
    runCli(argv)
}
