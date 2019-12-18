package idiotlin

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

object App {
    @JvmStatic
    fun main(args: Array<String>) {
        val server = embeddedServer(Netty, port = 8080) {
            routing {
                get("/") {
                    call.respondText("Hello Idiotlin!", ContentType.Text.Plain)
                }
            }
        }
        println("starting server ...")
        server.start(wait = true)
    }
}