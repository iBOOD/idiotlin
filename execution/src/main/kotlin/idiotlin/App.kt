package idiotlin

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.serialization.serialization
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

object App {
    @JvmStatic
    fun main(args: Array<String>) {
        connectToDatabase()
        embeddedServer(factory = Netty) {
            ktor()
        }.start(wait = true)
    }
}

fun Application.ktor(kodein: Kodein = kodein()) {
    val service by kodein.instance<Service>()
    install(ContentNegotiation) {
        serialization()
    }
    routing {
        route("") {
            get {
                call.respond(service.readAll())
            }
        }
    }
}

@Serializable
data class Model(val name: String)

interface Service {
    fun readAll(): List<Model>
}

class DummyService(private val repo: ModelRepository) : Service {
    override fun readAll() = repo.readAll()
}

fun kodein() = Kodein {
    bind<Service>() with singleton { DummyService(instance()) }
    bind<ModelRepository>() with singleton { ExposedModelRepository() }
}

interface ModelRepository {
    fun readAll(): List<Model>
}

class ExposedModelRepository : ModelRepository {
    override fun readAll() = transaction {
        ModelTable.selectAll().map {
            Model(name = it[ModelTable.name])
        }
    }
}

object ModelTable : Table() {
    val name = varchar("name", length = 50)
}

fun connectToDatabase(url: String = "jdbc:h2:mem:idiotlinDb;DB_CLOSE_DELAY=-1") =
    Database.Companion.connect(url, "org.h2.Driver").apply {
        transaction {
            SchemaUtils.create(ModelTable)
        }
    }
