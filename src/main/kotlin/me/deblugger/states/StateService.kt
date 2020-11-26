package me.deblugger.states

import me.deblugger.project.ProjectServiceRepository
import javax.inject.Singleton

@Singleton
class StateService (
        private val stateServiceRepository: StateServiceRepository,
        private val projectServiceRepository: ProjectServiceRepository
){

    fun createState(name: String, projectId: Long): StateEntity {
        val state = StateEntity(0, name, projectId)
        return stateServiceRepository.save(state)
    }

    fun getByProjectId(projectId: Long): List<StateEntity> {
        val project = projectServiceRepository.getById(projectId)
        return stateServiceRepository.findByProjectId(project.id)
    }

    fun updateState(stateId: Long, name: String) {
        val state = stateServiceRepository.getById(stateId)
        state.name = name
        stateServiceRepository.update(state)
    }

    fun deleteState(stateId: Long) = stateServiceRepository.deleteById(stateId)
}