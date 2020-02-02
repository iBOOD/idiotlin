package idiotlinMaster

import assertk.Assert
import org.json.JSONArray
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode

class TestableService(
    private val models: List<Model>
) : Service {
    override fun readAll() = models
}

fun Assert<String?>.isEqualJsonArray(expectedJson: String) {
    given {
        JSONAssert.assertEquals(expectedJson, JSONArray(it), JSONCompareMode.NON_EXTENSIBLE)
    }
}

fun ModelDbo.Companion.any() = ModelDbo(name = "any")

fun Model.Companion.any() = Model("any")
