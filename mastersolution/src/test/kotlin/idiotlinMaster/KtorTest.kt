package idiotlinMaster

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.testng.annotations.Test

@Test
class KtorTest {

    private val model = Model.any()

    fun `When get root endpoint Then return 200 OK and JSON`() {
        withTestApplication({
            ktor(Kodein {
                extend(kodein())
                bind<Service>(overrides = true) with instance(TestableService(listOf(model)))
            })
        }) {
            with(handleRequest(HttpMethod.Get, "/")) {
                assertThat(response.status()).isEqualTo(HttpStatusCode.OK)
                assertThat(response.content).isEqualJsonArray(("""[{"name":"${model.name}"}]"""))
            }
        }
    }
}
