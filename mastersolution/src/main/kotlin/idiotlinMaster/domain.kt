package idiotlinMaster

import java.util.UUID

data class Model(
    val id: UUID,
    val name: String
) {
    companion object { /* for test extensions */ }
}

data class ModelCreateRequest(
    val name: String
)

interface Service {
    fun getAll(): List<Model>
    fun create(request: ModelCreateRequest): Model
}

class ServiceImpl(
    private val repo: ModelRepository,
    private val uuids: UUIDProvider
) : Service {

    override fun getAll() = repo.getAll().map { it.toModel() }

    override fun create(request: ModelCreateRequest): Model {
        val model = request.toModel()
        repo.create(model.toModelDbo())
        return model
    }

    // extend the DBO representation of our domain model, without it knowing about our existence ;)

    private fun Model.toModelDbo() = ModelDbo(
        id = id,
        name = name
    )

    private fun ModelDbo.toModel() = Model(
        id = id,
        name = name
    )

    private fun ModelCreateRequest.toModel() = Model(
        id = uuids.provide(),
        name = name
    )
}
