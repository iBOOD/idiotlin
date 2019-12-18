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
            id = UUID.randomUUID(),
            name = "dummy entity"
        )
    }
}
