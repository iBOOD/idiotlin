package idiotlinMaster

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import io.mockk.every
import io.mockk.mockk
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

// simply testing the ktor layer (no real logic)
@Test
class KtorTest {

    private val model = Model.any()

    private lateinit var serviceMock: Service

    @BeforeMethod
    fun `init mock`() {
        serviceMock = mockk()
    }

    fun `Given a model is returned When get all Then return 200 and that model`() = withKtor {
        every { serviceMock.getAll() } returns (listOf(model))
        with(handleRequest(HttpMethod.Get, "/")) {
            assertThat(response.status()).isEqualTo(HttpStatusCode.OK)
            assertThat(response.content).isEqualJson(("""[{"id":"${model.id}", "name":"${model.name}"}]"""))
        }
    }

    fun `Given a model is returned When create model Then return 200 and that model`() = withKtor {
        every { serviceMock.create(ModelCreateRequest(model.name)) } returns model
        with(handleRequest(HttpMethod.Post, "/") {
            addHeader("content-type", "application/json")
            setBody("""{"name": "${model.name}"}""")
        }) {
            assertThat(response.status()).isEqualTo(HttpStatusCode.OK)
            assertThat(response.content).isEqualJson(("""{"id":"${model.id}", "name":"${model.name}"}"""))
        }
    }

    private fun withKtor(code: TestApplicationEngine.() -> Unit) {
        withTestApplication({
            ktor(Kodein {
                extend(kodeinConfig())
                bind<Service>(overrides = true) with instance(serviceMock)
            })
        }, code)
    }
}


