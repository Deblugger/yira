package me.deblugger.project

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.*
import javax.inject.Inject

// TODO: REST INT TEST
@MicronautTest
@Disabled
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProjectRestControllerIntTest {

    @Inject
    lateinit var projectRepository: ProjectRepository

    @Inject
    @field:Client("/project")
    lateinit var client: RxHttpClient

    private val projectEntity = ProjectEntity(0, "Test", 1)

    @BeforeAll
    fun setup() {
        projectRepository.save(projectEntity)
    }

    @Test
    fun `should get all projects`() {
        val allProjects = projectRepository.findAll()
        val request = HttpRequest.GET<Any>("/")

        val result = client.toBlocking().exchange<Any, List<ProjectEntity>>(request, Argument.listOf(ProjectEntity::class.java)).body()
        Assertions.assertEquals(result?.size, allProjects.size)
        Assertions.assertEquals(result?.get(0)?.name, allProjects[0].name)
    }
}