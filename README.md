# Idiotlin

Code repository for the live demo "Idiomatic Kotlin Microservices" given at the [FOSDEM 2020](https://fosdem.org/2020/schedule/event/kotlin_idiomatic_microservices/) conference.

## Techstack

* [Ktor](https://ktor.io/) ... Web Framework (Spring Boot)
* [Kodein](https://kodein.org/) ... Dependency Injection (Spring Framework)
* [kotlin-serialization](https://github.com/Kotlin/kotlinx.serialization) ... JSON serializer (Jackson)
* [Exposed](https://github.com/JetBrains/Exposed) ... Persistence Manager (JPA, Hibernate)
* [Spek](https://www.spekframework.org/) ... Test Framework (JUnit/TestNG)
* [kotlin-logging](https://github.com/MicroUtils/kotlin-logging) ... Logging Facade (Slf4j)
* [Detekt](https://github.com/arturbosch/detekt) ... Static Code Analysis (Checkstyle)
* Gradle Kotlin-DSL

## How to run

* `./gradlew run` ... start local server on port 8080
* `./gradlew test` ... run Spek tests
* `./gradlew detekt` ... run static code analysis
* `./gradlew dependencyUpdates` ... check if all dependencies are up2date
