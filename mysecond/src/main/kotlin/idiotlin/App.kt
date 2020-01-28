package idiotlin

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

object App {
    @JvmStatic
    fun main(args: Array<String>) {
        embeddedServer(factory = Netty, port = 8080) {
            startIdiotlin()
        }.start(wait = true)
    }
}

fun Application.startIdiotlin() {
    routing {
        route("") {
            get {
                call.respondText("home")
            }
        }
    }
}
