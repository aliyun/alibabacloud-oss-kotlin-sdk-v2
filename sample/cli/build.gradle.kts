plugins {
    alias(libs.plugins.multiplatform)
}

kotlin {
    jvmToolchain(17)
    jvm()
    js {
        nodejs()
        binaries.executable()
    }

    macosArm64() {
        binaries {
            executable {
                entryPoint = "sample.app.main"
                baseName = "oss-cli"
            }
        }
    }

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.cli)
            implementation(libs.kotlinx.io.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(project(":oss-sdk"))
        }

        jsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
    }
}

tasks.named<Jar>("jvmJar") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = "sample.app.MainKt"
    }
    from(kotlin.jvm().compilations.getByName("main").output)
    from({
        configurations.getByName("jvmRuntimeClasspath").map {
            if (it.isDirectory) it else zipTree(it)
        }
    })
}
