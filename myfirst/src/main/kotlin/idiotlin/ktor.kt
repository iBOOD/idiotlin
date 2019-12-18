package idiotlin

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.serialization.serialization
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.serialization.Serializable
import mu.KotlinLogging.logger
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

private val log = logger {}

fun startKtor() {
    val ktor = embeddedServer(factory = Netty, port = 8080) {
        main(applicationKodein())
    }
    ktor.start(wait = true)
}

fun Application.main(kodein: Kodein) {
    install(ContentNegotiation) {
        serialization()
    }

    connectToDatabase()
    val repository by kodein.instance<EntityRepository>()
    repository.insert(Entity.dummy)

    routing {
        get("/") {
            // TODO make json
            log.info { "Got request: GET /" }
            call.respond(HttpStatusCode.OK, GetResponse(repository.all().map { it.toEntityDto() }))
        }
    }
}

fun Entity.toEntityDto() = EntityDto(
    id = id.toString(),
    name = name
)

@Serializable
data class GetResponse(
    val entities: List<EntityDto>
)

@Serializable
data class EntityDto(
    val id: String,
    val name: String
)
