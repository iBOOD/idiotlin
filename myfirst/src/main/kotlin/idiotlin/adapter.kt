package idiotlin

class InMemoryRepository : Repository {
    override fun all() = listOf(Entity("id1"), Entity("id2"))
}
