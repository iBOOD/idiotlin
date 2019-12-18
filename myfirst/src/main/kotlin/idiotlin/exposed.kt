package idiotlin

import mu.KotlinLogging.logger
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

private val log = logger {}

// CAVE: make the database connection persist as long jvm runs (otherwise closed right after transaction)
private const val DB_URL = "jdbc:h2:mem:idiotlinDb;DB_CLOSE_DELAY=-1"

fun connectToDatabase() {
    log.info { "Connecting to database: $DB_URL" }
    Database.connect(url = DB_URL, driver = "org.h2.Driver", user = "root", password = "")
    transaction {
        SchemaUtils.create(EntityTable)
    }
}

object EntityTable : Table() {
    val id = uuid("id").primaryKey()
    val name = varchar("name", length = 50)
}

class ExposedEntityRepository : EntityRepository {
    private val log = logger {}

    override fun all(): List<Entity> = transaction {
        log.debug { "all()" }
        EntityTable.selectAll().map {
            Entity(
                id = it[EntityTable.id],
                name = it[EntityTable.name]
            )
        }
    }

    override fun insert(entity: Entity): Unit = transaction {
        log.debug { "insert($entity)" }
        EntityTable.insert {
            it[id] = entity.id
            it[name] = entity.name
        }
    }
}
