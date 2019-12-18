package idiotlin

import io.ktor.application.Application
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.withTestApplication
import org.kodein.di.Copy
import org.kodein.di.Kodein

fun withKtor(
    kodein: Kodein.MainBuilder.(Application) -> Unit = {},
    setup: (Kodein) -> Unit = {},
    testCode: TestApplicationEngine.() -> Unit
) {
    withTestApplication({
        val kodeinInstance = Kodein {
            extend(applicationKodein(), copy = Copy.All)
            kodein(this@withTestApplication)
        }
        setup(kodeinInstance)
        main(kodeinInstance)
    }, testCode)
}
