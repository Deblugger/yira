package me.deblugger.state

import me.deblugger.project.ProjectRepositoryService
import javax.inject.Singleton

@Singleton
class StateService (
        private val stateRepositoryService: StateRepositoryService,
        private val projectRepositoryService: ProjectRepositoryService
){

    fun createState(name: String, projectId: Long, position: Long): StateEntity {
        val state = StateEntity(name, projectId, position)
        return stateRepositoryService.save(state)
    }

    fun getByProjectId(projectId: Long): List<StateEntity> {
        val project = projectRepositoryService.getById(projectId)
        return stateRepositoryService.findByProjectId(project.id)
    }

    fun updateState(stateId: Long, name: String) {
        val state = stateRepositoryService.getById(stateId)
        state.name = name
        stateRepositoryService.update(state)
    }

    fun deleteState(stateId: Long) = stateRepositoryService.deleteById(stateId)

    fun deleteByProjectId(projectId: Long) = stateRepositoryService.deleteByProjectId(projectId)
}