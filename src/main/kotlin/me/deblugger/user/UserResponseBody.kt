package me.deblugger.user

data class UserResponseBody(val name: String, val email: String)

fun UserEntity.toResponseBody() = UserResponseBody(this.name, this.email)