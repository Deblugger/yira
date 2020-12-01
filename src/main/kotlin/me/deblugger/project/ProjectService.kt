package me.deblugger.project

import me.deblugger.configuration.ProjectAlreadyExistsException
import me.deblugger.states.StateOrderedCreationRequestBody
import me.deblugger.states.StateService
import javax.inject.Singleton

@Singleton
class ProjectService(
        private val projectServiceRepository: ProjectServiceRepository,
        private val stateService: StateService
) {

    fun getAll(): List<ProjectResponseBody> {
        val findAll = projectServiceRepository.findAll()
        return findAll.map { project ->
            val statesName = stateService.getByProjectId(project.id).map { it.name }
            project.toResponseBody(statesName)
        }
    }

    fun createProject(name: String, owner: Long, states: List<StateOrderedCreationRequestBody>): ProjectEntity {
        projectServiceRepository.getByNameAndOwner(name, owner)?.run {
            throw ProjectAlreadyExistsException(this.name, this.owner)
        }

        return projectServiceRepository.save(ProjectEntity(name, owner)).apply {
            states.forEach {
                stateService.createState(it.name, this.id, it.position)
            }
        }

    }

    fun findById(id: Long): ProjectResponseBody {
        return projectServiceRepository.getById(id).let {project ->
            val states = stateService.getByProjectId(project.id).map { it.name }
            project.toResponseBody(states)
        }
    }

    fun updateProject(id: Long, name: String?, owner: Long?) {
        val project = projectServiceRepository.getById(id)
        name?.apply { project.name = this }
        owner?.apply { project.owner = this }

        projectServiceRepository.update(project)
    }

    fun deleteProject(id: Long) = projectServiceRepository.deleteById(id)

}