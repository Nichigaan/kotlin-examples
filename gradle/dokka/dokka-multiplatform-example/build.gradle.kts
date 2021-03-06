import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    kotlin("multiplatform") version "1.4-M3"
    id("org.jetbrains.dokka") version "1.4-mc-1"
}

repositories {
    jcenter()
    mavenCentral()
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    maven("https://dl.bintray.com/kotlin/kotlin-dev")
}

group = "org.test"
version = "1.0-SNAPSHOT"

kotlin {
    jvm() // Create a JVM target with the default name "jvm"
    js()
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-js")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.0-RC2")
            }
        }
    }
}

tasks.withType<DokkaTask> {
    dokkaSourceSets {
        register("commonMain") {
            sourceRoot { path = "src/commonMain/kotlin" }
        }

        register("jsMain") {
            dependsOn("commonMain")
            sourceRoot { path = "src/jsMain/kotlin" }
        }

        register("jvmMain") {
            dependsOn("commonMain")
            sourceRoot { path = "src/jvmMain/kotlin" }
        }

        register("customSourceSet") {
            this.jdkVersion = 9
            this.displayName = "Custom JDK 10 Source Set"
            this.sourceRoot {
                this.path = "src/customJdk10/kotlin"
            }
        }
    }
}
