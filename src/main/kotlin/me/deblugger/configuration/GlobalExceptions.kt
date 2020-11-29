package me.deblugger.configuration

import me.deblugger.configuration.ExceptionResponseInformation.*

open class ResponseException(val exceptionResponseInformation: ExceptionResponseInformation, vararg val detailedMessages: String) : RuntimeException(exceptionResponseInformation.errorMessage)

class ProjectNotFoundException(val id: Long): ResponseException(PROJECT_NOT_FOUND, "Project with id: $id was not found")
class StateNotFoundException(val id: Long): ResponseException(STATE_NOT_FOUND, "State with id: $id was not found")
class ProjectAlreadyExistsException(val name: String, val owner: Long): ResponseException(PROJECT_ALREADY_EXISTS, "A project with name: $name, and owner: $owner already exits.")