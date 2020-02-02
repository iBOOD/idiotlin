package idiotlin

import assertk.Assert
import assertk.assertThat
import assertk.assertions.isEqualTo
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.json.JSONArray
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.testng.annotations.Test

@Test
class Test {

    private val someModel = Model.any()

    fun `When get root path Then return 200 OK`() {
        connectToDatabase("jdbc:h2:mem:idiotlinTestDb;DB_CLOSE_DELAY=-1")
        withTestApplication({
            ktor()
        }) {
            with(handleRequest(HttpMethod.Get, "")) {
                assertThat(response.status()).isEqualTo(HttpStatusCode.OK)
            }
        }
    }

    fun `Given a stubbed service When get root path Then return proper JSON`() {
        withTestApplication({
            ktor(Kodein {
                extend(kodein())
                bind<Service>(overrides = true) with singleton { TestableService(someModel) }
            })
        }) {
            with(handleRequest(HttpMethod.Get, "")) {
                assertThat(response.content).isEqualJsonArray("""[{"name":"${someModel.name}"}]""")
            }
        }
    }
}

class TestableService(private val models: List<Model>) : Service {
    constructor(vararg models: Model) : this(models.toList())
    override fun readAll() = models
}

fun Model.Companion.any() = Model("test")

fun Assert<String?>.isEqualJsonArray(expectedJson: String) {
    given {
        JSONAssert.assertEquals(expectedJson, JSONArray(it), JSONCompareMode.NON_EXTENSIBLE)
    }
}
