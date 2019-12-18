package idiotlin

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun connectToDatabase() {
    Database.connect(
        // CAVE: make the database connection persist as long jvm runs (otherwise closed right after transaction)
        url = "jdbc:h2:mem:idiotlinDb;DB_CLOSE_DELAY=-1",
        driver = "org.h2.Driver", user = "root", password = ""
    )
    transaction {
        SchemaUtils.create(EntityTable)
    }
}

object EntityTable : Table("entity") {
    val id = uuid("id").primaryKey()
    val name = varchar("name", length = 50)
}

class ExposedEntityRepository : EntityRepository {
    override fun all(): List<Entity> = transaction {
        EntityTable.selectAll().map {
            Entity(
                id = it[EntityTable.id],
                name = it[EntityTable.name]
            )
        }
    }

    override fun insert(entity: Entity): Unit = transaction {
        EntityTable.insert {
            it[id] = entity.id
            it[name] = entity.name
        }
    }
}
