package idiotlinMaster

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

fun kodein() = Kodein {
    // beans are defined in code, making heavy use of Kotlin's nature
    bind<ModelRepository>() with singleton { ExposedModelRepository() }
    bind<Service>() with singleton { ServiceImpl(instance()) }
}
