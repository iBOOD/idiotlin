package idiotlinMaster

import assertk.Assert
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger

val ANY_ID: UUID = UUID.fromString("00000000-0000-0000-0000-000000000000")
val SOME_ID: UUID = UUID.fromString("11111111-2222-3333-1337-000000000001")

class InMemoryModelRepository() : ModelRepository {
    private val models = mutableListOf<ModelDbo>()
    override fun getAll() = models
    override fun create(model: ModelDbo) {
        models += model
    }
}

fun Assert<String?>.isEqualJson(expectedJson: String) {
    given {
        JSONAssert.assertEquals(expectedJson, it, JSONCompareMode.NON_EXTENSIBLE)
    }
}

fun ModelDbo.Companion.any() = ModelDbo(
    id = UUID.randomUUID(),
    name = "any"
)

fun Model.Companion.any() = Model(
    id = UUID.randomUUID(),
    name = "any"
)

private val dbCounter = AtomicInteger()
fun withDb(code: () -> Unit) {
    val db = connectToDatabase("jdbc:h2:mem:testDb${dbCounter.getAndIncrement()};DB_CLOSE_DELAY=-1")
    try {
        code()
    } finally {
        db.connector.invoke().close()
    }
}
