package idiotlinMaster

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.serialization.serialization
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

fun Application.ktor(kodein: Kodein = kodein()) {
    // configure the JSON marshaller into Ktor
    install(ContentNegotiation) {
        serialization()
    }

    // spring talk: get the "bean" from the "application context"
    val service by kodein.instance<Service>()

    // no more annotations, just real (Kotlin idiomatic) code ❤️
    routing {
        route("") {
            get {
                call.respond(service.readAll())
            }
        }
    }
}
