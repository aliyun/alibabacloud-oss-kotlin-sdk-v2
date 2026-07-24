plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlinx.serialization)
}
kotlin {
    jvmToolchain(17)
    jvm()
    androidTarget()
    js {
        nodejs {
            testTask {
                useMocha {
                    timeout = "60s"
                }
            }
        }
    }

    iosArm64()
    iosSimulatorArm64()
    macosArm64()

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain.dependencies {
        }

        commonTest.dependencies {
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.kotlinx.datetime)
            implementation(libs.ktor.client.core)
            implementation(project(":oss-sdk"))
            implementation(project(":oss-sdk-extension"))
            implementation(kotlin("test"))
        }

        jvmTest.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        androidUnitTest.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        jsTest.dependencies {
            implementation(libs.ktor.client.js)
        }
    }
}

fun getFirstAvailableIPhone(): String {
    val output = providers.exec {
        commandLine("xcrun", "simctl", "list", "devices", "available")
    }.standardOutput.asText.get()

    return output.lineSequence()
        .map { it.trim() }
        .filter { it.startsWith("iPhone") && it.contains("(") }
        .map { it.substringBefore(" (").trim() }
        .firstOrNull()
        ?: "iPhone 17"
}

fun getBootedOrFirstIPhone(): String {
    val booted = providers.exec {
        commandLine("xcrun", "simctl", "list", "devices", "booted")
    }.standardOutput.asText.get()
        .lineSequence()
        .map { it.trim() }
        .filter { it.startsWith("iPhone") && it.contains("(") }
        .map { it.substringBefore(" (").trim() }
        .firstOrNull()

    if (booted != null) return booted

    return getFirstAvailableIPhone()
}


tasks.withType<org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeSimulatorTest>().configureEach {
    standalone.set(false)
    device.set(getBootedOrFirstIPhone())
}

android {
    namespace = "com.aliyun"
    compileSdk = 36

    defaultConfig {
        minSdk = 21
        multiDexEnabled = true
    }
}
