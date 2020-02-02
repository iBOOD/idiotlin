package idiotlinMaster

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.serialization.Serializable
import mu.KotlinLogging.logger

object App {
    private val log = logger {}

    @JvmStatic
    fun main(args: Array<String>) {
        log.info { "Starting up Ktor server ..." }
        connectToDatabase()
        embeddedServer(factory = Netty, port = 8080) {
            ktor()
        }.start(wait = true) // start ktor as a daemon (keep it alive)
    }
}
