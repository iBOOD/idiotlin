package idiotlin

import assertk.Assert
import io.ktor.server.testing.TestApplicationResponse
import org.json.JSONArray
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode

fun Assert<String?>.isEqualJsonArray(expectedJson: String) {
    given {
        JSONAssert.assertEquals(expectedJson, JSONArray(it), JSONCompareMode.NON_EXTENSIBLE)
    }
}
