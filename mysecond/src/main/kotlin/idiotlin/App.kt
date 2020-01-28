package idiotlin

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.serialization.serialization
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.serialization.Serializable
import mu.KotlinLogging.logger
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

private val log = logger {}

object App {
    @JvmStatic
    fun main(args: Array<String>) {
        connectToDatabase()
        embeddedServer(factory = Netty, port = 8080) {
            startUp(kodein())
        }.start(wait = true)
    }
}

fun Application.startUp(kodein: Kodein) {
    install(ContentNegotiation) {
        serialization()
    }

    val service by kodein.instance<Service>()
    routing {
        route("") {
            get {
                call.respond(GetResponse(service.readAll()))
            }
        }
    }
}

@Serializable
data class GetResponse(
    val models: List<Model>
)

@Serializable
data class Model(
    val name: String
)

interface Service {
    fun readAll(): List<Model>
}

class DummyService(
    private val repo: ModelRepository
) : Service {
    override fun readAll() = repo.fetchAll()
}

fun kodein() = Kodein {
    bind<ModelRepository>() with singleton { ExposedModelRepository() }
    bind<Service>() with singleton { DummyService(instance()) }
}


fun connectToDatabase(dbUrl: String = "jdbc:h2:mem:idiotlinDb;DB_CLOSE_DELAY=-1"): Database {
    log.info { "Connecting to database" }
    val db = Database.connect(url = dbUrl, driver = "org.h2.Driver")
    transaction {
        SchemaUtils.create(ModelTable)
    }
    return db
}

object ModelTable : Table() {
    val name = varchar("name", length = 50)
}

interface ModelRepository {
    fun fetchAll(): List<Model>
}

class ExposedModelRepository : ModelRepository {
    override fun fetchAll(): List<Model> = transaction {
        ModelTable.selectAll().map {
            Model(
                name = it[ModelTable.name]
            )
        }
    }
}
