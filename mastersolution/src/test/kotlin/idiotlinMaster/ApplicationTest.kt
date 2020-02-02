package idiotlinMaster

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.testng.annotations.Test

// full integration test
@Test
class ApplicationTest {

    private val jackson = jacksonObjectMapper()
    private val model = Model.any()

    fun `When get all models Then return 200 and empty list`() = withKtor {
        with(handleRequest(HttpMethod.Get, "/")) {
            assertThat(response.status()).isEqualTo(HttpStatusCode.OK)
            assertThat(response.content).isEqualJson(("""[]"""))
        }
    }

    fun `When create model Then return 200 and new dto`() = withKtor {
        with(handleRequest(HttpMethod.Post, "/") {
            addHeader("content-type", "application/json")
            setBody("""{"name": "${model.name}"}""")
        }) {
            assertThat(response.status()).isEqualTo(HttpStatusCode.OK)
            val dto = jackson.readValue<ModelDto>(response.content ?: "")
            assertThat(dto).isEqualTo(ModelDto(
                id = dto.id,
                name = model.name
            ))
        }
    }

    private fun withKtor(code: TestApplicationEngine.() -> Unit) {
        withDb {
            withTestApplication({
                ktor()
            }, code)
        }
    }
}
