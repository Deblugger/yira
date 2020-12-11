package me.deblugger.state

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface StateRepository: JpaRepository<StateEntity, Long> {
    fun getById(id: Long): StateEntity? {
        return findById(id).orElse(null)
    }

    fun findByProjectId(projectId: Long): List<StateEntity>

    fun deleteByProjectId(projectId: Long): Long
}