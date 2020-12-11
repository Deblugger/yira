package me.deblugger.user

import me.deblugger.configuration.PasswordEncoderService
import me.deblugger.configuration.UserAlreadyExistsException
import me.deblugger.configuration.UserNotFoundException
import javax.inject.Singleton

@Singleton
class UserRepositoryService (
    private val userRepository: UserRepository,
    private val passwordEncoderService: PasswordEncoderService
) {
    fun createUser(name: String, password: String, email: String): UserEntity {
        if(userRepository.findByNameOrEmail(name, email) != null) {
            throw UserAlreadyExistsException(name, email)
        }

        val encodedPassword = passwordEncoderService.encode(password)
        return userRepository.save(UserEntity(name, encodedPassword, email))
    }

    fun findUserByName(name: String) = userRepository.findByName(name) ?: throw UserNotFoundException(name)

    // Just use in the application start to set test data.
    fun getByUserName(name: String) = userRepository.findByName(name)

}
