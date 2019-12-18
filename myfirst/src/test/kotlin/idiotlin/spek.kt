package idiotlin

import assertk.Assert
import assertk.assertThat
import assertk.assertions.isEqualTo
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.TestApplicationResponse
import io.ktor.server.testing.contentType
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.json.JSONObject
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object KtorSpec : Spek({
    Feature("Ktor Endpoint") {
        Scenario("Root endpoint returns preconfigured dummy entity") {
            lateinit var response: TestApplicationResponse

            When("GET /") {
                withKtor {
                    response = handleRequest(HttpMethod.Get, "/").response
                }
            }

            Then("return 200 OK and JSON dummy entity") {
                assertThat(response.status()).isEqualTo(HttpStatusCode.OK)
                assertThat(response.contentType().withoutParameters()).isEqualTo(ContentType.Application.Json)
                assertThat(response).contentEqualsJson("""{
                    "entities": [
                        {
                            "id": "${Entity.dummy.id}",
                            "name": "${Entity.dummy.name}"
                        }
                    ]
                }""")
            }
        }
    }
})

fun withKtor(engineCode: TestApplicationEngine.() -> Unit) {
    withTestApplication({
        main(Kodein { extend(applicationKodein(), copy = Copy.All) })
    }, engineCode)
}

fun Assert<TestApplicationResponse>.contentEqualsJson(expectedJson: String) {
    given {
        JSONAssert.assertEquals(expectedJson, JSONObject(it.content), JSONCompareMode.NON_EXTENSIBLE)
    }
}
