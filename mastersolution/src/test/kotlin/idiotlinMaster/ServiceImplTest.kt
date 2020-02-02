package idiotlinMaster

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import io.mockk.Runs
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

@Test
class ServiceImplTest {

    private val name = "test name"
    private val modelDbo = ModelDbo.any()
    private lateinit var repo: ModelRepository
    private lateinit var uuids: UUIDProvider

    @BeforeMethod
    fun `init mock`() {
        repo = mockk()
        uuids = mockk()
    }

    fun `Given model exists When get all Then return model`() {
        every { repo.getAll() } returns listOf(modelDbo)

        val actual = service().getAll()

        assertThat(actual).containsExactly(Model(modelDbo.id, modelDbo.name))
    }

    fun `When create Then return model prototype`() {
        every { repo.create(any()) } just Runs
        every { uuids.provide() } returns SOME_ID

        val actual = service().create(ModelCreateRequest(name = name))

        assertThat(actual).isEqualTo(Model(
            id = SOME_ID,
            name = name
        ))
        verify(exactly = 1) { repo.create(ModelDbo(SOME_ID, name)) }
        confirmVerified(repo)
    }

    private fun service() = ServiceImpl(repo, uuids)
}
