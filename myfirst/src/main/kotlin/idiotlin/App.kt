package idiotlin

import mu.KotlinLogging.logger

object App {
    private val log = logger {}

    @JvmStatic
    fun main(args: Array<String>) {
        log.info { "Starting idiotlin server ..." }
        startKtor()
    }
}
