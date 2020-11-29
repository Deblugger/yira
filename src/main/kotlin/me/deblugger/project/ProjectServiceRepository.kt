package me.deblugger.project

import me.deblugger.configuration.ProjectNotFoundException
import javax.inject.Singleton

@Singleton
class ProjectServiceRepository(
        private val projectRepository: ProjectRepository
) {
    fun getById(id: Long) = projectRepository.getById(id) ?: throw ProjectNotFoundException(id)

    fun findAll() = projectRepository.findAll()

    fun save(projectEntity: ProjectEntity) = projectRepository.save(projectEntity)

    fun update(projectEntity: ProjectEntity) = projectRepository.update(projectEntity)

    fun deleteById(id: Long) = projectRepository.deleteById(id)

    fun getByNameAndOwner(name: String, owner: Long) = projectRepository.findByNameAndOwner(name, owner)
}