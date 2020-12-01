package me.deblugger.states

import me.deblugger.configuration.StateNotFoundException
import javax.inject.Singleton

@Singleton
class StateServiceRepository(
        private val stateRepository: StateRepository
) {
    fun save(stateEntity: StateEntity) = stateRepository.update(stateEntity)

    fun findByProjectId(projectId: Long) = stateRepository.findByProjectId(projectId)

    fun getById(stateId: Long) = stateRepository.getById(stateId) ?: throw StateNotFoundException(stateId)

    fun update(stateEntity: StateEntity) = stateRepository.update(stateEntity)

    fun deleteById(id: Long) = stateRepository.deleteById(id)
}