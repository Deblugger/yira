package me.deblugger.project

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*

@Controller("/projects")
class ProjectRestController(
        private val projectService: ProjectService
) {
    @Get
    fun getProjects() = projectService.getAll()

    @Get("/{id}")
    fun getProjectById(id: Long) = projectService.findById(id)

    @Post
    fun createProject(@Body projectCreationRequestBody: ProjectCreationRequestBody): HttpResponse<ProjectEntity> {
        val createdProject = projectService.createProject(projectCreationRequestBody.name, projectCreationRequestBody.owner, projectCreationRequestBody.states)
        return HttpResponse.created(createdProject)
    }

    @Put("/{id}")
    fun updateProject(id: Long, @Body projectUpdateRequestBody: ProjectUpdateRequestBody): HttpResponse<ProjectEntity> {
        val updatedProject = projectService.updateProject(id, projectUpdateRequestBody.name, projectUpdateRequestBody.owner)
        return HttpResponse.ok(updatedProject)
    }

    @Delete("/{id}")
    fun deleteProject(id: Long): HttpResponse<Void>{
        projectService.deleteProject(id)
        return HttpResponse.noContent()
    }

    // TODO: Get by owner
    // TODO: Get by user allowed
    // TODO: Invite user
}