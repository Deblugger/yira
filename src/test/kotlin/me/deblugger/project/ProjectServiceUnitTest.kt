package me.deblugger.project

import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
//import io.mockk.every
//import io.mockk.mockk
import me.deblugger.states.StateEntity
import me.deblugger.states.StateService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import javax.inject.Inject

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MicronautTest(transactional = false)
@Disabled
class ProjectServiceUnitTest {

    /*@Inject
    lateinit var cut: ProjectService

    @get:MockBean(ProjectServiceRepository::class)
    val projectServiceRepositoryMock = mockk<ProjectServiceRepository>()

    @get:MockBean(StateService::class)
    val stateServiceMock = mockk<StateService>()

    val projectIdA = 1L
    val projectIdB = 2L
    val stateAName = "StateA"
    val stateBName = "StateB"
    val projectA = ProjectEntity("ProjectA", 1)
    val projectB = ProjectEntity("ProjectB", 1)
    val stateA = StateEntity(stateAName, projectIdA)
    val stateB = StateEntity(stateBName, projectIdB)

    @BeforeAll
    fun setup() {
        projectA.id = projectIdA
        projectB.id = projectIdB
    }

    @Test
    fun `getAll - should get all projects with states`() {
        every { projectServiceRepositoryMock.findAll() } returns listOf(projectA, projectB)
        every { stateServiceMock.getByProjectId(projectA.id) } returns listOf(stateA)
        every { stateServiceMock.getByProjectId(projectB.id) } returns listOf(stateB)

        val result = cut.getAll()

        assertThat(result.size).isEqualTo(2)
        assertThat(result[0].id).isEqualTo(projectIdA)
        assertThat(result[1].id).isEqualTo(projectIdB)
        assertThat(result[0].states.size).isEqualTo(1)
        assertThat(result[0].states[0]).isEqualTo(stateAName)
        assertThat(result[1].states.size).isEqualTo(1)
        assertThat(result[1].states[0]).isEqualTo(stateBName)
    }*/
}