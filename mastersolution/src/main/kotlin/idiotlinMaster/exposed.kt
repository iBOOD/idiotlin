package idiotlinMaster

import mu.KotlinLogging.logger
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

private val log = logger {}

fun connectToDatabase(
    dbUrl: String = "jdbc:h2:mem:idiotlinDb;DB_CLOSE_DELAY=-1"
): Database {
    log.info { "Connecting to database: $dbUrl" }
    // the `connect` invocation will implicitly set a global static mutable state :-/
    val db = Database.connect(url = dbUrl, driver = "org.h2.Driver")
    transaction(db) {
        SchemaUtils.create(ModelDboTable)
    }
    return db
}

// definition of the database schema
object ModelDboTable : Table("ModelDbo") {
    val id = uuid("id").primaryKey()
    val name = varchar("name", length = 50)
}

interface ModelRepository {
    fun getAll(): List<ModelDbo>
    fun create(model: ModelDbo)
}

class ExposedModelRepository : ModelRepository {

    private val log = logger {}

    override fun getAll() = transaction {
        log.debug { "get all" }
        ModelDboTable.selectAll().map {
            // manual mapping of rows to our DBO representation
            ModelDbo(
                id = it[ModelDboTable.id],
                name = it[ModelDboTable.name]
            )
        }
    }

    override fun create(model: ModelDbo) {
        log.info { "create: $model" }
        transaction {
            ModelDboTable.insert {
                it[id] = model.id
                it[name] = model.name
            }
        }
    }
}

data class ModelDbo(
    val id: UUID,
    val name: String
) {
    companion object { /* for test extensions only */ }
}
