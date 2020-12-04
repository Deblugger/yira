package me.deblugger

import io.micronaut.runtime.Micronaut.*
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
	info = Info(
			title = "Yira",
			version = "0.1",
			description = "Fake yira project"
	)
)
// Work around
object Application {
	@JvmStatic
	fun main(args: Array<String>) {
		build()
				.args(*args)
				.packages("me.deblugger")
				.start()
	}
}

