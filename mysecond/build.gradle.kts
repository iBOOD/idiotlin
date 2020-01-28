import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
    jcenter()
}

plugins {
    kotlin("jvm") version Versions.kotlin
    kotlin("plugin.serialization") version Versions.kotlin
    id("com.github.ben-manes.versions") version Versions.Plugins.versions
    id("io.gitlab.arturbosch.detekt") version Versions.Plugins.detekt
    application
}

application {
    mainClassName = "idiotlin.App"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    fun ktor(suffix: String) = "io.ktor:ktor-$suffix:${Versions.ktor}"
    implementation(ktor("server-netty"))
    implementation(ktor("serialization"))
    implementation("org.kodein.di:kodein-di-framework-ktor-server-jvm:${Versions.kodein}")
    implementation("org.kodein.di:kodein-di-generic-jvm:${Versions.kodein}")
    implementation("io.github.microutils:kotlin-logging:${Versions.klogging}")
    implementation("ch.qos.logback:logback-classic:${Versions.logback}")
    implementation("org.jetbrains.exposed:exposed:${Versions.exposed}")

    testImplementation("org.testng:testng:${Versions.testng}")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:${Versions.assertk}")
    testImplementation("org.skyscreamer:jsonassert:${Versions.jsonAssert}")
    testImplementation(ktor("server-test-host"))
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xuse-experimental=io.ktor.util.KtorExperimentalAPI")
        }
    }

    withType<Test> {
        useTestNG {}
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
    }
}
