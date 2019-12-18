package idiotlin

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

fun applicationKodein() = Kodein {
    bind<Repository>() with singleton { InMemoryRepository() }
//    import(boundaryModule())
}

//private fun boundaryModule(konfig: Konfig) = Kodein.Module(name = "Boundary Module") {
//    bind<Clock>() with singleton { SystemClock }
//}
