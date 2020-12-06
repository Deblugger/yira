package me.deblugger.project

import io.micronaut.http.HttpStatus
import me.deblugger.BaseRestIntTest
import me.deblugger.states.StateRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import me.deblugger.states.StateOrderedCreationRequestBody
import javax.inject.Inject

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
    fun `getProjects - should get all projects`() {
        projectRepository.update(projectEntity)
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
    fun `getProjectById - should get the project`() {
        projectRepository.update(projectEntity)
        val allProjects = projectRepository.findAll()
        assertThat(allProjects.size).isEqualTo(1)
        val project = allProjects[0]

        val result = doGet(HttpStatus.OK, "/projects/{id}", project.id)

        assertThat(result.body().asString()).isEqualToIgnoringWhitespace("""
            {
                "id":${project.id},
                "name":"${project.name}",
                "owner":${project.owner}
            }
        """.trimIndent())
    }

    @Test
    fun `getProjectById - should fail with NOT_FOUND if project does not exists`() {
        val allProjects = projectRepository.findAll()
        assertThat(allProjects.size).isEqualTo(0)

        val result = doGet(HttpStatus.NOT_FOUND, "/projects/{id}", 1)

        assertThat(result.body().asString()).isEqualToIgnoringWhitespace("""
            [
                "Project not found.",
                "Project with id: 1 was not found"
            ]
        """.trimIndent())
    }

    @Test
    fun `createProject - should create a project with states`() {
        var allProjects = projectRepository.findAll()
        assertThat(allProjects.size).isEqualTo(0)

        val state1 = StateOrderedCreationRequestBody("state1", 0)
        val state2 = StateOrderedCreationRequestBody("state2", 1)
        val projectCreationRequestBody = ProjectCreationRequestBody("test", 1, listOf(state1, state2))
        val result = doPost(HttpStatus.CREATED, "/projects", projectCreationRequestBody)

        allProjects = projectRepository.findAll()
        assertThat(allProjects.size).isEqualTo(1)

        val savedProject = allProjects[0]
        assertThat(savedProject.name).isEqualTo("test")
        assertThat(savedProject.owner).isEqualTo(1)

        val allStates = stateRepository.findByProjectId(savedProject.id)
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

    @Test
    fun `createProject - should fail if a project already exists with CONFLICT 409`() {
        projectRepository.update(projectEntity) // TODO: WHY UPDATE YES AND SAVE GIVE ME A DETTACHED ENTITY EXCEPTION??
        val allProjectsBeforePost = projectRepository.findAll()
        assertThat(allProjectsBeforePost.size).isEqualTo(1)

        val projectCreationRequestBody = ProjectCreationRequestBody(projectEntity.name, projectEntity.owner, listOf())

        val result = doPost(HttpStatus.CONFLICT, "/projects", projectCreationRequestBody)

        assertThat(result.body().asString()).isEqualToIgnoringWhitespace("""
            [
                "Project already exists.",
                "A project with name: ${projectCreationRequestBody.name}, and owner: ${projectCreationRequestBody.owner} already exits."
            ]
        """.trimIndent())

        val allProjectsAfterPost = projectRepository.findAll()
        assertThat(allProjectsAfterPost.size).isEqualTo(1)
    }

    @Test
    fun `deleteProject - should delete an existing project`() {
        projectRepository.update(projectEntity)
        val allProjectsBeforeDelete = projectRepository.findAll()
        assertThat(allProjectsBeforeDelete.size).isEqualTo(1)
        val projectToDelete = allProjectsBeforeDelete[0]

        doDelete(HttpStatus.NO_CONTENT, "/projects/{id}", projectToDelete.id)

        val allProjectsAfterDelete = projectRepository.findAll()
        assertThat(allProjectsAfterDelete.size).isEqualTo(0)
    }

    @Test
    fun `deleteProject - should delete an non existing project with no failure`() {
        val allProjectsBeforeDelete = projectRepository.findAll()
        assertThat(allProjectsBeforeDelete.size).isEqualTo(0)

        doDelete(HttpStatus.NO_CONTENT, "/projects/{id}", 1)

        val allProjectsAfterDelete = projectRepository.findAll()
        assertThat(allProjectsAfterDelete.size).isEqualTo(0)
    }

    @Test
    fun `updateProject - should update a project`() {
        projectRepository.update(projectEntity)
        val allProjectsBeforePost = projectRepository.findAll()
        assertThat(allProjectsBeforePost.size).isEqualTo(1)
        val projectToUpdate = allProjectsBeforePost[0]

        val projectUpdateRequestBody = ProjectUpdateRequestBody("UPDATED PROJECT", 999)

        val result = doPut(HttpStatus.OK, "/projects/{id}", projectUpdateRequestBody ,projectToUpdate.id)

        val allProjectsAfterPut = projectRepository.findAll()
        assertThat(allProjectsAfterPut.size).isEqualTo(1)
        val projectUpdated = allProjectsAfterPut[0]

        assertThat(projectUpdated.name).isEqualTo("UPDATED PROJECT")
        assertThat(projectUpdated.owner).isEqualTo(999)
        assertThat(projectUpdated.id).isEqualTo(projectToUpdate.id)

        assertThat(result.body().asString()).isEqualToIgnoringWhitespace("""
        {
            "name":"${projectUpdateRequestBody.name}",
            "owner":${projectUpdateRequestBody.owner},
            "id":${projectToUpdate.id}
        }
        """.trimIndent())
    }

    @Test
    fun `updateProject - should fail if project no exists`() {
        val allProjectsBeforePost = projectRepository.findAll()
        assertThat(allProjectsBeforePost.size).isEqualTo(0)

        val projectUpdateRequestBody = ProjectUpdateRequestBody("UPDATED PROJECT", 999)

        val response = doPut(HttpStatus.NOT_FOUND, "/projects/{id}", projectUpdateRequestBody ,1)

        assertThat(response.body().asString()).isEqualToIgnoringWhitespace("""
            [
                "Project not found.",
                "Project with id: 1 was not found"
            ]
        """.trimIndent())
    }
}