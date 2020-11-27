package me.deblugger.project

import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import me.deblugger.BaseRestIntTest
import me.deblugger.states.StateRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import javax.inject.Inject

// TODO: REST INT TEST
class ProjectRestControllerIntTest: BaseRestIntTest() {

    @Inject
    lateinit var projectRepository: ProjectRepository

    @Inject
    lateinit var stateRepository: StateRepository

    private val projectEntity = ProjectEntity("Test", 1)

    @BeforeEach
    fun setup() {
        projectRepository.deleteAll()
        stateRepository.deleteAll()
    }

    @Test
    fun `should get all projects`() {
        projectRepository.save(projectEntity)
        val allProjects = projectRepository.findAll()
        assertThat(allProjects.size).isEqualTo(1)
        val project = allProjects[0]

        val result = doGet(HttpStatus.OK, "/projects")

        assertThat(result.body().asString()).isEqualToIgnoringWhitespace("""
            [{
                "id":${project.id},
                "name":"${project.name}",
                "owner":${project.owner}
            }]
        """.trimIndent())
    }

    @Test
    fun `should create a project with states`() {
        var allProjects = projectRepository.findAll()
        assertThat(allProjects.size).isEqualTo(0)

        val projectCreationRequestBody = ProjectCreationRequestBody("test", 1, listOf("state1", "state2"))
        val result = doPost(HttpStatus.CREATED, "/projects", projectCreationRequestBody)

        allProjects = projectRepository.findAll()
        assertThat(allProjects.size).isEqualTo(1)

        val savedProject = allProjects[0]
        assertThat(savedProject.name).isEqualTo("test")
        assertThat(savedProject.owner).isEqualTo(1)

        val allStates = stateRepository.findByProjectId(savedProject.id!!)
        with(allStates) {
            assertThat(this.size).isEqualTo(2)
            assertThat(this[0].name).isEqualTo("state1")
            assertThat(this[1].name).isEqualTo("state2")
        }

        assertThat(result.body().asString()).isEqualToIgnoringWhitespace("""
            {
                "name":"${projectCreationRequestBody.name}",
                "owner":${projectCreationRequestBody.owner},
                "id":${savedProject.id}
            }
        """.trimIndent())
    }
}