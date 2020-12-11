package me.deblugger.configuration

import io.micronaut.http.HttpStatus

enum class ExceptionResponseInformation(val statusCode: HttpStatus, val errorMessage: String) {
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "Project not found."),
    STATE_NOT_FOUND(HttpStatus.NOT_FOUND, "State not found."),
    PROJECT_ALREADY_EXISTS(HttpStatus.CONFLICT, "Project already exists."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found."),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "User already exists.")
}
