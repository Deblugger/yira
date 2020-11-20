package me.deblugger.configuration

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponseFactory
import io.micronaut.http.server.exceptions.ExceptionHandler
import javax.inject.Singleton

@Singleton
class ResponseExceptionHandler: ExceptionHandler<ResponseException, HttpResponse<List<String?>>> {
    override fun handle(request: HttpRequest<*>?, exception: ResponseException): HttpResponse<List<String?>> {
        val exceptionMessage = listOf(exception.exceptionResponseInformation.errorMessage,* exception.detailedMessages)
        return HttpResponseFactory.INSTANCE.status(exception.exceptionResponseInformation.statusCode, exceptionMessage)
    }
}