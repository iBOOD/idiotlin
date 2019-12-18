package idiotlin

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.ktor.http.HttpMethod
import io.ktor.server.testing.TestApplicationCall
import io.ktor.server.testing.contentType
import io.ktor.server.testing.handleRequest
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object KtorTest : Spek({
    Feature("Ktor") {
        Scenario("Root site returns hello greeting") {
            lateinit var call: TestApplicationCall
            When("GET /") {
                withKtor {
                    call = handleRequest(HttpMethod.Get, "/")
                }
            }
            Then("it should return the first item") {
                assertThat(call.response.contentType().toString()).isEqualTo("text/plain; charset=UTF-8")
                assertThat(call.response.content).isEqualTo("id1, id2")
            }
        }
    }
})
