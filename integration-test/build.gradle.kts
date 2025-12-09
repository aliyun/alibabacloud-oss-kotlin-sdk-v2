plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlinx.serialization)
}
kotlin {
    jvmToolchain(17)
    jvm()
    androidTarget()

    sourceSets {
        commonMain.dependencies {
        }

        commonTest.dependencies {
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.kotlinx.datetime)
            implementation(libs.ktor.client.okhttp)
            implementation(project(":oss-sdk"))
            implementation(project(":oss-sdk-extension"))
            implementation(kotlin("test"))
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