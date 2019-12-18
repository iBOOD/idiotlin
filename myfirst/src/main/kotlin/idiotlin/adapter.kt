package idiotlin

import java.util.UUID

class InMemoryRepository : Repository {
    private val entities = mutableListOf(
        Entity(UUID.randomUUID(), "entity 1"),
        Entity(UUID.randomUUID(), "entity 2")
    )
    override fun all() = entities
}
