package me.deblugger.state

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("states")
class StateRestController(
        private val stateService: StateService
) {

    @Get("/project/{id}")
    fun getByProjectId(id: Long): List<StateEntity> {
        return stateService.getByProjectId(id)
    }

    @Put("/{id}")
    fun updateState(id: Long, @Body stateUpdateRequestBody: StateUpdateRequestBody): HttpResponse<Void> {
        stateService.updateState(id, stateUpdateRequestBody.name)
        return HttpResponse.noContent()
    }

    @Delete("/{id}")
    fun deleteState(id: Long): HttpResponse<Void> {
        stateService.deleteState(id)
        return HttpResponse.noContent()
    }

    @Post
    fun createState(@Body stateCreationRequestBody: StateCreationRequestBody): StateEntity {
        return stateService.createState(stateCreationRequestBody.name, stateCreationRequestBody.projectId, stateCreationRequestBody.position)
    }
}