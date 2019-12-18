package idiotlin

import java.util.UUID

interface EntityRepository {
    fun all(): List<Entity>
    fun insert(entity: Entity)
}

data class Entity(
    val id: UUID,
    val name: String
) {
    companion object {
        val dummy = Entity(
            id = UUID.fromString("00000000-0000-0000-0000-000000000000"),
            name = "dummy entity"
        )
    }
}
