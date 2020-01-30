package idiotlin

import assertk.Assert
import io.ktor.server.testing.TestApplicationResponse
import org.json.JSONObject
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode

fun Assert<TestApplicationResponse>.contentEqualsJson(expectedJson: String) {
    given {
        JSONAssert.assertEquals(expectedJson, JSONObject(it.content), JSONCompareMode.NON_EXTENSIBLE)
    }
}
