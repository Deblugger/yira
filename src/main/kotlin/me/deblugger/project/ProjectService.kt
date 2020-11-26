package me.deblugger.project

import me.deblugger.configuration.ProjectNotFoundException
import me.deblugger.states.StateService
import javax.inject.Singleton

@Singleton
class ProjectService(
        private val projectServiceRepository: ProjectServiceRepository,
        private val stateService: StateService
) {

    fun getAll(): List<ProjectResponseBody> {
        return projectServiceRepository.findAll().map { project ->
            val statesName = stateService.getByProjectId(project.id).map { it.name }
            project.toResponseBody(statesName)
        }
    }

    fun createProject(name: String, owner: Long, states: List<String>): ProjectEntity {
        return projectServiceRepository.save(ProjectEntity(0, name, owner)).apply {
            states.forEach {
                stateService.createState(it, this.id)
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