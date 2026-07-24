plugins { alias(libs.plugins.multiplatform) }

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
                entryPoint = "com.aliyun.oss.verify.app.main"
                baseName = "package-verify"
            }
        }
    }

    applyDefaultHierarchyTemplate()
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.io.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.oss.core)
            implementation(libs.oss.extension)
        }
        jsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
    }
}

tasks.register<JavaExec>("run") {
    group = "application"
    description = "Run the package-verify JVM application"
    val compilation = kotlin.jvm().compilations.getByName("main")
    classpath(
        compilation.output.allOutputs,
        compilation.runtimeDependencyFiles
    )
    mainClass.set("com.aliyun.oss.verify.app.MainKt")
}
