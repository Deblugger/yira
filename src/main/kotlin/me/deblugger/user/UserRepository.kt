package me.deblugger.user

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface UserRepository: JpaRepository<UserEntity, Long> {
    fun findByName(name: String): UserEntity?
    fun findByNameOrEmail(name: String, email: String): UserEntity?
}
