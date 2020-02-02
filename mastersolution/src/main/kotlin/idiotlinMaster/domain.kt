package idiotlinMaster

import kotlinx.serialization.Serializable

@Serializable // use "real" kotlin stuff, no jackson/gson
data class Model(
    val name: String
)

interface Service {
    fun readAll(): List<Model>
}

class ServiceImpl(
    private val repo: ModelRepository
) : Service {
    override fun readAll() = repo.fetchAll().map { it.toModel() }

    // extend the DBO representation of our domain model, without it knowing about our existence ;)
    private fun ModelDbo.toModel() = Model(name = name)
}
