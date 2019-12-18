package idiotlin

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

fun applicationKodein() = Kodein {
    bind<EntityRepository>() with singleton { ExposedEntityRepository() }
//    bind<EntityService>() with singleton { EntityService(instance()) }
//    import(otherModule())
}

//fun otherModule() = Kodein.Module(name = "Other Module") {
//    bind<Clock>() with singleton { SystemClock }
//}
