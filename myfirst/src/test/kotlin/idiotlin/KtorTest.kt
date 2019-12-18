package idiotlin

import assertk.Assert
import assertk.assertThat
import assertk.assertions.isEqualTo
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.server.testing.TestApplicationResponse
import io.ktor.server.testing.contentType
import io.ktor.server.testing.handleRequest
import org.json.JSONObject
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object KtorTest : Spek({
    Feature("Ktor") {
        Scenario("Get / returns dummy entity") {
            lateinit var response: TestApplicationResponse
            When("GET /") {
                withKtor {
                    response = handleRequest(HttpMethod.Get, "/").response
                }
            }
            Then("it should return the first item") {
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

fun Assert<TestApplicationResponse>.contentEqualsJson(expectedJson: String) {
    given {
        JSONAssert.assertEquals(expectedJson, JSONObject(it.content), JSONCompareMode.NON_EXTENSIBLE)
    }
}
