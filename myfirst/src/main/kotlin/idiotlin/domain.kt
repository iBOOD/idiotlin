package idiotlin

interface Repository {
    fun all(): List<Entity>
}

data class Entity(
    val id: String
)
