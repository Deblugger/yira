package me.deblugger.user

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("users")
class UserRestController(
        private val userService: UserService
) {

    @Get("/{id}")
    fun getProjectsByUser(id: Long) {
        // TODO: Implement projects rights to show in the front-end navbar all the available project for user
    }

    @Get("/name/{name}")
    fun getUserByUserName(name: String) = userService.findUserByName(name)

    @Post("/register")
    fun createUser(@Body userCreationRequestBody: UserCreationRequestBody): HttpResponse<UserEntity> {
        val user = userService.createUser(userCreationRequestBody.name, userCreationRequestBody.password, userCreationRequestBody.email)
        return HttpResponse.created(user)
    }

    // TODO: Implement user-profile
}