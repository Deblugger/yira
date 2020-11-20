package me.deblugger.configuration

import me.deblugger.configuration.ExceptionResponseInformation.PROJECT_NOT_FOUND

open class ResponseException(val exceptionResponseInformation: ExceptionResponseInformation, vararg val detailedMessages: String) : RuntimeException(exceptionResponseInformation.errorMessage)

class ProjectNotFoundException(val id: Long): ResponseException(PROJECT_NOT_FOUND, "Project with id: $id was not found")