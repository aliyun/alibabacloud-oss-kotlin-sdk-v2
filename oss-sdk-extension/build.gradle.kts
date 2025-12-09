import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.dokka)
    alias(libs.plugins.ktlint)
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

        commonMain.dependencies {
            implementation(project(":oss-sdk"))
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
        }

        commonTest.dependencies {
            implementation(libs.kotlinx.coroutines.test)
            implementation(kotlin("test"))
        }
    }
}

android {
    compileSdk = libs.versions.android.sdk.get().toInt()

    defaultConfig {
        minSdk = 21
        multiDexEnabled = true
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    namespace = "com.aliyun.kotlin.sdk.service.oss2.extension"
}

tasks {
    withType<Jar> {
        metaInf.with(
            copySpec {
                from("${project.rootDir}/LICENSE")
            }
        )
    }
}

// Publishing your Kotlin Multiplatform library to Maven Central
// https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-publish-libraries.html
mavenPublishing {
    publishToMavenCentral()
    coordinates(group.toString(), "kotlin-oss-v2-extension", version.toString())

    pom {
        name = "AlibabaCloud OSS SDK Extension for Kotlin Multiplatform"
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
