package me.deblugger.configuration

import io.micronaut.http.HttpStatus
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import me.deblugger.BaseRestIntTest
import me.deblugger.DataTestConfigurationProperties
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest(transactional = false)
class JwtIntTest: BaseRestIntTest() {

    @Inject
    private lateinit var dataTestConfigurationProperties: DataTestConfigurationProperties

    @Test
    fun `no authorization returns a 401`() {
        doGetWithNoResponse(HttpStatus.UNAUTHORIZED, "/projects")
    }

    @Test
    fun `get authorized`() {
        doGetWithNoResponse(HttpStatus.UNAUTHORIZED, "/projects")

        // TODO: Change by real user in db?
        val credentials = UsernamePasswordCredentials(dataTestConfigurationProperties.user, dataTestConfigurationProperties.password)
        val loginResponse = doPost(HttpStatus.OK, "/login", credentials)

        val token = loginResponse.body().`as`(BearerAccessRefreshToken::class.java)
        assertThat(token.username).isEqualTo(credentials.username)

        doGetWithHeaders(HttpStatus.OK, "/projects", mapOf("Authorization" to "Bearer " + token.accessToken))
    }

    @Test
    fun `non existing user should return 404`() {
        val credentials = UsernamePasswordCredentials("NON-EXISTING", "FAIL")
        doPost(HttpStatus.NOT_FOUND, "/login", credentials)
    }
}