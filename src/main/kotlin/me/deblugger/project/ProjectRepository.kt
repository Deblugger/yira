package me.deblugger.project

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface ProjectRepository: JpaRepository<ProjectEntity, Long> {
    fun getById(id: Long): ProjectEntity? {
        return findById(id).orElse(null)
    }
}