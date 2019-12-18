package idiotlin

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import mu.KotlinLogging.logger
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

private val log = logger {}

fun startKtor() {

    val server = embeddedServer(factory = Netty, port = 8080) {
        main(applicationKodein(), "jdbc:h2:file:idiotlinDb")
    }
    log.info { "Starting server ..." }
    server.start(wait = true)
}

fun Application.main(kodein: Kodein, databaseUrl: String) {
    connectDb(databaseUrl)

    val service by kodein.instance<Repository>()
    routing {
        get("/") {
            call.respondText(service.all().joinToString { it.name }, ContentType.Text.Plain)
        }
    }
}

private fun connectDb(url: String) {
    Database.connect(url = url, driver = "org.h2.Driver", user = "root", password = "")
    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(EntityTable)
    }
}
