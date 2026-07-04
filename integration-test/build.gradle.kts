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

        jsTest.dependencies {
            implementation(libs.ktor.client.js)
        }
    }
}

android {
    namespace = "com.aliyun"
    compileSdk = 36

    defaultConfig {
        minSdk = 21
        multiDexEnabled = true
    }
}