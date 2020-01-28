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
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

object App {
    @JvmStatic
    fun main(args: Array<String>) {
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

class DummyService : Service {
    override fun readAll() = listOf(
        Model("a"), Model("b")
    )
}

fun kodein() = Kodein {
    bind<Service>() with singleton { DummyService() }
}
