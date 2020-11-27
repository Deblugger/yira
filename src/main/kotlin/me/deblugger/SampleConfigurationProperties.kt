package me.deblugger

import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("hello.value")
class SampleConfigurationProperties {
    var en: String = "Default en"
    var es: String = "Default es"
}