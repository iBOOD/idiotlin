package idiotlin

import java.util.UUID

interface Repository {
    fun all(): List<Entity>
}

data class Entity(
    val id: UUID,
    val name: String
)
