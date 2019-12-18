# Idiotlin

Code repository for the live demo "Idiomatic Kotlin Microservices" given at the [FOSDEM 2020](https://fosdem.org/2020/schedule/event/kotlin_idiomatic_microservices/) conference.

## Techstack

* [Ktor](https://ktor.io/) ... Web Framework (Spring Boot)
* [Kodein](https://kodein.org/) ... Dependency Injection (Spring Framework/Guice)
* [Exposed](https://github.com/JetBrains/Exposed) ... Persistence Manager (JPA, Hibernate)
* [Spek](https://www.spekframework.org/) ... Test Framework (JUnit/TestNG)
* [Detekt](https://github.com/arturbosch/detekt) ... Static Code Analysis (Checkstyle)
* Gradle Kotlin-DSL

## How to run

# Gradle commands

* `./gradlew detekt` ... run static code analysis
* `./gradlew dependencyUpdates` ... check if all dependencies are up2date
