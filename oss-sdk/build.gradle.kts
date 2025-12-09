import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.dokka)
    alias(libs.plugins.ktlint)
    // alias(libs.plugins.bin.compat.validator)
}

group = "com.aliyun"
version = "0.1.0-dev"

tasks.withType<KotlinCompile> {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_1_8
    }
}

kotlin {
    explicitApi()

    compilerOptions {
        // Required to silence compiler warnings about the beta status of
        // expected and actual classes. See https://kotlinlang.org/docs/multiplatform-expect-actual.html#expected-and-actual-classes
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    androidTarget {
        publishLibraryVariants("release")
    }

    jvm()

    jvmToolchain(17)

    applyDefaultHierarchyTemplate()

    sourceSets {
        all {
            languageSettings {
                progressiveMode = true
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.io.core)
                implementation(libs.kotlinx.datetime)
            }
        }

        val jvmCommonMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation(libs.okhttp)
            }
        }

        val jvmMain by getting {
            dependsOn(jvmCommonMain)
        }

        val androidMain by getting {
            dependsOn(jvmCommonMain)
        }

        /*
        val nonJvmCommonMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.cio)
            }
        }
         */

        commonTest.dependencies {
            implementation(libs.kotlinx.coroutines.test)
            implementation(kotlin("test"))
        }

        jvmTest.dependencies {
            implementation(kotlin("test-junit"))
            implementation(libs.okhttp.mockwebserver)
        }

        androidUnitTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

android {
    compileSdk =
        libs.versions.android.sdk
            .get()
            .toInt()

    defaultConfig {
        minSdk = 21
        multiDexEnabled = true
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    namespace = "com.aliyun.kotlin.sdk.service.oss2"
}

tasks {
    withType<Jar> {
        metaInf.with(
            copySpec {
                from("${project.rootDir}/LICENSE")
            },
        )
    }
}

// Publishing your Kotlin Multiplatform library to Maven Central
// https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-publish-libraries.html
mavenPublishing {
    publishToMavenCentral()
    coordinates(group.toString(), "kotlin-oss-v2", version.toString())

    pom {
        name = "AlibabaCloud OSS SDK for Kotlin Multiplatform"
        description = "The Alibaba Cloud OSS SDK for Kotlin is used to access the Alibaba Cloud Object Storage Service"
        url = "https://github.com/aliyun/alibabacloud-oss-kotlin-sdk-v2"

        licenses {
            license {
                name = "The Apache Software License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }

        developers {
            developer {
                id = "aliyunproducts"
                name = "Aliyun SDK"
                email = "aliyunsdk@aliyun.com"
            }
        }

        scm {
            connection = "scm:git:git://github.com/aliyun/alibabacloud-oss-kotlin-sdk-v2.git"
            developerConnection = "scm:git:git://github.com/aliyun/alibabacloud-oss-kotlin-sdk-v2.git"
            url = "https://github.com/aliyun/alibabacloud-oss-kotlin-sdk-v2"
        }
    }
    if (project.hasProperty("signing.keyId")) signAllPublications()
}

apply(plugin = "org.jlleitschuh.gradle.ktlint")
