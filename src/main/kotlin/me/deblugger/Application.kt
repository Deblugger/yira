package me.deblugger

import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.runtime.Micronaut.*
import io.micronaut.runtime.server.event.ServerStartupEvent
import me.deblugger.user.UserRepositoryService
import javax.inject.Singleton

fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("me.deblugger")
		.start()
}


@Singleton
class ApplicationTestListener(
		private val userRepositoryService : UserRepositoryService,
		private val dataTestConfigurationProperties: DataTestConfigurationProperties
) : ApplicationEventListener<ServerStartupEvent> {
	override fun onApplicationEvent(event: ServerStartupEvent?) {
		if (userRepositoryService.getByUserName(dataTestConfigurationProperties.user) == null) {
			userRepositoryService.createUser(
					dataTestConfigurationProperties.user,
					dataTestConfigurationProperties.password,
					dataTestConfigurationProperties.email,
			)
		}
	}
}

@ConfigurationProperties("data.init.authdata")
class DataTestConfigurationProperties {
	var user: String = "default"
	var password: String = "default"
	var email: String = "default"
}

