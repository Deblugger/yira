package me.deblugger.project

import me.deblugger.configuration.ProjectNotFoundException
import javax.inject.Singleton

@Singleton
class ProjectService(
        private val projectRepository: ProjectRepository
) {

    fun getAll() = projectRepository.findAll()

    fun getById(id: Long) = projectRepository.getById(id) ?: throw ProjectNotFoundException(id)

    fun createProject(name: String, owner: String, states: List<String>): ProjectEntity {
        val project = ProjectEntity(0, name, owner, states)
        return projectRepository.save(project)
    }

    fun updateProject(id: Long, name: String?, states: List<String>?) {
        val project = getById(id)
        name?.apply { project.name = name }
        states?.apply { project.states = states }

        projectRepository.update(project)
    }

    fun deleteProject(id: Long) = projectRepository.deleteById(id)

}