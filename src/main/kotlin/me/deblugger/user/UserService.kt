package me.deblugger.user

import javax.inject.Singleton

@Singleton
class UserService (
        private val userRepositoryService: UserRepositoryService
) {

    fun createUser(name: String, password: String, email: String): UserEntity = userRepositoryService.createUser(name, password, email)

    fun findUserByName(name: String) = userRepositoryService.findUserByName(name).toResponseBody()
}