package me.deblugger

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces

@Controller("/example")
class SampleController {

    @Get
    fun hello() = "Hello world"
}