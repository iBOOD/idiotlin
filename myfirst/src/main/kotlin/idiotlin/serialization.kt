package idiotlin

import kotlinx.serialization.Serializable

fun Entity.toEntityDto() = EntityDto(
    id = id.toString(),
    name = name
)

@Serializable
data class GetResponse(
    val entities: List<EntityDto>
)

@Serializable
data class EntityDto(
    val id: String,
    val name: String
)
