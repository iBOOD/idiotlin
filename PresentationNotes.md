# TODO

* [ ] pre-present ad work/meetup
* [ ] workout step-by-step cheatsheet on paper
* [ ] add fullFledged master solution (complete CRUD)
* [ ] ? beamer slides? (could enable/disable code samples for handout)

# Presentation Notes

## Outline

1. [1] Give short overview of topics
1. [1] **Gradle** Kotlin DSL (+detekt)
1. [3] ~~Spek~~ **TestNG** (+ktor infra, assertk)
1. [4] **Ktor** returning plain text (+kotlin logging)
1. [2] **Kodein** (in-memory implementation)
1. [2] **Serializable** (+ JSONassert)
1. [5] **Exposed**

## Step-by-step

### Ktor

1. create IntegrationTest
    * use `withTestApplication`
    * create `Application.startIdiotlin` in test and reference it
    * GET / and assert OK => fail
1. create `App`
    * add main method
    * run it
1. setup ktor
    * use `embeddedServer`
    * move `startIdiotlin` to App
1. finish test
    * run test again => fail
    * implement routing/route/get, run test again => success

### Kodein

1. setup kodein
    1. create interface + in-memory implementation
    1. add `Kodein {}` and bind it
1. wire beans
    1. invoke `kodein()` in main method and pass parameter to startUp method
    1. get instance `by kodein.instance<>()` and hookup list response
    1. for that initialize content negotation (add `@Serializable`, no DTO conversion)
1. JSON response
    1. fix test's expected output as simple string
    1. copy'n'paste `Assert<String?>.isJsonEquals`
    1. fix implementation to return wrapped response (containing list)
    1. adapt test, rerun => success
1. overridable in tests
    1. implement `TestableService`
    1. extend kodein and override instance
    1. rename from `IntegrationTest` to `KtorTest`
1. setup exposed
    1. create `connectToDatabase()`
    1. create Table and Repository; wire in kodein (inject repo in service)
    1. create `ExposedModelRepositoryTest`

## Outlook

* CRUD operations (see GitHub solution)
* Exception Handling
* Custom JSON serializer
* Enhanced Test Setup (override kodein instances)
* Mockk
* Application Konfiguration (no properties files needed)
* Swagger + (custom) Kotlin CodeGen
* kscript ;) instead shell scripts

## EUR 0.02

Plus
* Making full use of language features rokks ;)
* Lots of libraries have been rewritten / thin on-top API layer written
* Highly concise and expressive; feels natural and straight forward to write/read

Minus
* Sometimes feeling pain of paradigm shift when using Java libraries
* Sometimes missing out some Java libraries
* Sometimes missing well established coding guidelines / best practices

TBH
* Personally I'd use TestNG over Spek ;)
