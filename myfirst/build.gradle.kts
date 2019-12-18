import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
    jcenter()
}

plugins {
    kotlin("jvm") version Versions.kotlin
    id("com.github.ben-manes.versions") version Versions.Plugins.versions
    id("io.gitlab.arturbosch.detekt") version Versions.Plugins.detekt
}

dependencies {
    fun ktor(suffix: String) = "io.ktor:ktor$suffix:${Versions.ktor}"
    implementation(ktor("-server-netty"))
    implementation(ktor("-serialization"))

    implementation("ch.qos.logback:logback-classic:${Versions.logback}")

    testImplementation(ktor("-server-test-host")) {
        exclude(group = "junit", module = "junit")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-test-junit")
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = Versions.jvm
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xuse-experimental=io.ktor.util.KtorExperimentalAPI")
        }
    }


    withType<Test> {
        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
        }
    }


    withType<Detekt> {
        config.setFrom("src/config/detekt.yml")
        source("src/main/kotlin", "src/test/kotlin")
        reports {
            html.enabled = false
            xml.enabled = false
            txt.enabled = false
        }
    }

    withType<DependencyUpdatesTask> {
        val rejectPatterns = listOf("alpha", "beta", "eap", "rc").map { qualifier ->
            Regex("(?i).*[.-]$qualifier[.\\d-]*")
        }
        resolutionStrategy {
            componentSelection {
                all {
                    if (rejectPatterns.any { it.matches(candidate.version) }) {
                        reject("Release candidate")
                    }
                }
            }
        }
        checkForGradleUpdate = true
        outputFormatter = "json"
        outputDir = "build/reports"
        reportfileName = "dependencyUpdates"
    }
}
