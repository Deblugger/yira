package me.deblugger.project

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*

@Controller("projects")
class ProjectRestController(
        private val projectService: ProjectService
) {
    @Get
    fun getAll() = projectService.getAll()

    @Get("/{id}")
    fun getOne(id: Long) = projectService.getById(id)

    @Post
    fun createOne(@Body projectCreationRequestBody: ProjectCreationRequestBody): ProjectEntity {
        return projectService.createProject(projectCreationRequestBody.name, projectCreationRequestBody.owner, projectCreationRequestBody.states)
    }

    @Put("/{id}")
    fun updateProject(id: Long, @Body projectUpdateRequestBody: ProjectUpdateRequestBody): HttpResponse<Void> {
        projectService.updateProject(id, projectUpdateRequestBody.name, projectUpdateRequestBody.states)
        return HttpResponse.noContent()
    }

    @Delete("/{id}")
    fun deleteProject(id: Long): HttpResponse<Void>{
        projectService.deleteProject(id)
        return HttpResponse.noContent()
    }
}