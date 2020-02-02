package idiotlinMaster

import java.util.UUID

interface UUIDProvider {
    fun provide(): UUID
}

object RandomUUIDProvider : UUIDProvider {
    override fun provide(): UUID = UUID.randomUUID()
}
