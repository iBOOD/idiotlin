package idiotlin

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

object EntityTable : Table() {
    val id = uuid("id").primaryKey()
    val name = varchar("name", length = 50)
}

//fun main() {
//    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
//    transaction {
//        addLogger(StdOutSqlLogger)
//        SchemaUtils.create(EntityTable)
//
//        val entityId = EntityTable.insert {
//            it[id] = UUID.randomUUID()
//            it[name] = "foo"
//        } get EntityTable.id
//
//        println("created entity: $entityId")
//
//        val entityName = EntityTable.select { EntityTable.id eq entityId }.single()[EntityTable.name]
//        println("entity name: $entityName")
//    }
//}
