package idiotlinMaster

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.hasSize
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.isInstanceOf
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger


@Test
class ExposedModelRepositoryTest {

    private val model = ModelDbo.any()

    fun `When fetch all Then empty`() = withDb {
        assertThat(ExposedModelRepository().getAll()).isEmpty()
    }

    fun `Given existing model When fetch all Then return that model`() = withDb {
        transaction {
            ModelDboTable.insert {
                it[id] = model.id
                it[name] = model.name
            }
        }

        assertThat(ExposedModelRepository().getAll()).all {
            hasSize(1)
            contains(model)
        }
    }

    fun `Given model inserted When insert with same ID Then fail`() = withDb {
        transaction {
            ModelDboTable.insert {
                it[id] = model.id
                it[name] = "any"
            }

            assertThat {
                ModelDboTable.insert {
                    it[id] = model.id
                    it[name] = "any"
                }
            }.isFailure().isInstanceOf(ExposedSQLException::class)
        }
    }

    fun `When create Then dbo is stores`() = withDb {
        ExposedModelRepository().create(model)

        transaction {
            val query = ModelDboTable.selectAll()
            assertThat(query.count()).isEqualTo(1)
            val row = query.toList().first()
            assertThat(row[ModelDboTable.id]).isEqualTo(model.id)
            assertThat(row[ModelDboTable.name]).isEqualTo(model.name)
        }
    }
}
