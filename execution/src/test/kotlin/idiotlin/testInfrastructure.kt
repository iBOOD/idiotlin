package idiotlin

import assertk.Assert
import io.ktor.server.testing.TestApplicationResponse
import org.json.JSONArray
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode

fun Assert<TestApplicationResponse>.contentEqualsArrayJson(expectedJson: String) {
    given {
        JSONAssert.assertEquals(expectedJson, JSONArray(it.content), JSONCompareMode.NON_EXTENSIBLE)
    }
}
