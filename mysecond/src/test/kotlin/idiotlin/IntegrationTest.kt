package idiotlin

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.testng.annotations.Test

@Test
class IntegrationTest {

    fun `When get root endpoint Then return 200 ok`() {
        withTestApplication(
            {
                startIdiotlin()
            }
        ) {
            with(handleRequest(HttpMethod.Get, "/")) {
                assertThat(response.status()).isEqualTo(HttpStatusCode.OK)
                assertThat(response.content).isEqualTo("home")
            }
        }
    }
}
