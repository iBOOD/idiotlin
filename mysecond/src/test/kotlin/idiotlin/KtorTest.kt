package idiotlin

import assertk.Assert
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.fail
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.json.JSONException
import org.json.JSONObject
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.testng.annotations.Test

class TestableService(
    private val models: List<Model>
) : Service {
    override fun readAll() = models
}

@Test
class KtorTest {

    private val model = Model("test model")

    fun `When get root endpoint Then return 200 ok`() {
        withTestApplication({
            startUp(Kodein {
                extend(kodein(), copy = Copy.All)
                bind<Service>(overrides = true) with instance(TestableService(listOf(model)))
            })
        }) {
            with(handleRequest(HttpMethod.Get, "/")) {
                assertThat(response.status()).isEqualTo(HttpStatusCode.OK)
                assertThat(response.content).isJsonEquals(("""{"models": [{"name":"${model.name}"}]}"""))
            }
        }
    }
}

fun Assert<String?>.isJsonEquals(expectedJson: String, mode: JsonMode = JsonMode.Strict) {
    given {
        assertThat(it).isNotNull()
        val json = try {
            JSONObject(it)
        } catch (e: JSONException) {
            fail("Unparsable JSON: <<<$it>>>>")
        }
        try {
            JSONAssert.assertEquals(expectedJson, json, mode.jsonAssertMode)
        } catch (e: AssertionError) {
            System.err.println("JSON assertion failed for:\n${json.toString(2)}")
            throw e
        }
    }
}

enum class JsonMode(
    val jsonAssertMode: JSONCompareMode
) {
    /** Extensible, and non-strict array ordering. */
    IgnoreUnknown(JSONCompareMode.LENIENT),
    /** Not extensible, and non-strict array ordering. */
    Strict(JSONCompareMode.NON_EXTENSIBLE)
}
