# Idiotlin

Code repository for the live demo "Idiomatic Kotlin Microservices" given at the [FOSDEM 2020](https://fosdem.org/2020/schedule/event/kotlin_idiomatic_microservices/) conference.

## Techstack

* [Ktor](https://ktor.io/) ... Web Framework (Spring Boot)
* [Kodein](https://kodein.org/) ... Dependency Injection (Spring Framework)
* [kotlin-serialization](https://github.com/Kotlin/kotlinx.serialization) ... JSON serializer (Jackson)
* [Exposed](https://github.com/JetBrains/Exposed) ... Persistence Manager (JPA, Hibernate)
* ~~[Spek](https://www.spekframework.org/)~~ [TestNG](https://testng.org) ... Test Framework (JUnit/TestNG)
* [kotlin-logging](https://github.com/MicroUtils/kotlin-logging) ... Logging Facade (Slf4j)
* [Detekt](https://github.com/arturbosch/detekt) ... Static Code Analysis (Checkstyle)
* Gradle Kotlin-DSL

## How to run

* `./gradlew run` ... start local server on port 8080
* `./gradlew test` ... run ~~ Spek~~ TestNG tests
* `./gradlew detekt` ... run static code analysis
* `./gradlew dependencyUpdates` ... check if all dependencies are up2date

# Outlook

* Full CRUD operations
* Exception Handling
* Application Konfiguration (no more properties files)
* `Model.Companion.testInstance()`, `Model.toDto()`
* `kodein.allInstances<Type>()`
* Custom JSON serializer (Jackson/Gson)
* Mockk
* OpenAPI/Swagger + (custom) Kotlin Code Generation
* KScript ;) instead of shell scripts

## EUR 0.02

**Plus**

* Making full use of language features rokks ;)
* Lots of libraries have been rewritten / thin on-top API layer written
* Highly concise and expressive; feels natural and straight forward to write/read

**Minus**

* Sometimes feeling pain of paradigm shift when using Java libraries
* Sometimes missing out some Java tools (proper coverage metrics, sonarqube like experience)
* Sometimes missing well established coding guidelines / best practices

**TBH**

* Personally I'd use TestNG over Spek ;)
