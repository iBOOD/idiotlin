package idiotlinMaster

import mu.KotlinLogging.logger
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

private val log = logger {}

fun connectToDatabase(
    dbUrl: String = "jdbc:h2:mem:idiotlinDb;DB_CLOSE_DELAY=-1"
): Database {
    log.info { "Connecting to database: $dbUrl" }
    // the `connect` invocation will implicitly set a global static mutable state :-/
    val db = Database.connect(url = dbUrl, driver = "org.h2.Driver")
    transaction {
        SchemaUtils.create(ModelDboTable)
    }
    return db
}

object ModelDboTable : Table() {
    // definition of the database schema
    val name = varchar("name", length = 50)
}

interface ModelRepository {
    fun fetchAll(): List<ModelDbo>
}

class ExposedModelRepository : ModelRepository {
    override fun fetchAll() = transaction {
        ModelDboTable.selectAll().map {
            // manual mapping of rows to our DBO representation
            ModelDbo(name = it[ModelDboTable.name])
        }
    }
}

data class ModelDbo(val name: String) {
    companion object { /* for test extensions only */ }
}
