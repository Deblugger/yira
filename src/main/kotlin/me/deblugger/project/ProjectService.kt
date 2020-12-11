package me.deblugger.project

import me.deblugger.configuration.ProjectAlreadyExistsException
import me.deblugger.state.StateOrderedCreationRequestBody
import me.deblugger.state.StateService
import javax.inject.Singleton

@Singleton
class ProjectService(
        private val projectRepositoryService: ProjectRepositoryService,
        private val stateService: StateService
) {

    fun getAll(): List<ProjectResponseBody> {
        val findAll = projectRepositoryService.findAll()
        return findAll.map { project ->
            val statesName = stateService.getByProjectId(project.id).map { it.name }
            project.toResponseBody(statesName)
        }
    }

    fun createProject(name: String, owner: Long, states: List<StateOrderedCreationRequestBody>): ProjectEntity {
        projectRepositoryService.getByNameAndOwner(name, owner)?.run {
            throw ProjectAlreadyExistsException(this.name, this.owner)
        }

        return projectRepositoryService.save(ProjectEntity(name, owner)).apply {
            states.forEach {
                stateService.createState(it.name, this.id, it.position)
            }
        }

    }

    fun findById(id: Long): ProjectResponseBody {
        return projectRepositoryService.getById(id).let { project ->
            val states = stateService.getByProjectId(project.id).map { it.name }
            project.toResponseBody(states)
        }
    }

    fun updateProject(id: Long, name: String?, owner: Long?): ProjectEntity {
        val project = projectRepositoryService.getById(id)
        name?.apply { project.name = this }
        owner?.apply { project.owner = this }

        return projectRepositoryService.update(project)
    }

    fun deleteProject(id: Long) = projectRepositoryService.deleteById(id).also {
        stateService.deleteByProjectId(id)
    }

}