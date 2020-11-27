package me.deblugger

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.LoggerFactory

@Controller("/example")
class SampleController(
        private val sampleConfigurationProperties: SampleConfigurationProperties
) {

    private val LOG = LoggerFactory.getLogger(SampleController::class.java)

    @Get("/")
    fun hola(): String {
        LOG.info("Hello world called in spanish")
        return sampleConfigurationProperties.es
    }

    @Get("/en")
    fun hello() = sampleConfigurationProperties.en

}
