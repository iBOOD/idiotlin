package idiotlin

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.hasSize
import assertk.assertions.isEmpty
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

@Test
class ExposedModelRepositoryTest {

    private val model = Model("test model")
    private lateinit var db: Database

    @BeforeMethod
    fun `init db`() {
        db = connectToDatabase("jdbc:h2:mem:testDb;DB_CLOSE_DELAY=-1")
    }

    @AfterMethod
    fun `reset db`() {
        db.connector.invoke().close()
    }

    fun `When fetch all Then empty`() {
        assertThat(ExposedModelRepository().fetchAll()).isEmpty()
    }

    fun `Given model inserted When fetch all Then return that model`() {
        transaction {
            ModelTable.insert {
                it[name] = model.name
            }
        }

        assertThat(ExposedModelRepository().fetchAll()).all {
            hasSize(1)
            contains(model)
        }
    }
}
