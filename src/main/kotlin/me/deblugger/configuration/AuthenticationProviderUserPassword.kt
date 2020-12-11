package me.deblugger.configuration

import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import me.deblugger.user.UserRepositoryService
import me.deblugger.user.UserService
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class AuthenticationProviderUserPassword(
        private val userRepositoryService: UserRepositoryService,
        private val passwordEncoderService: PasswordEncoderService
): AuthenticationProvider {

    private val LOG = LoggerFactory.getLogger(AuthenticationProviderUserPassword::class.java)

    override fun authenticate(httpRequest: HttpRequest<*>?, authenticationRequest: AuthenticationRequest<*, *>?): Publisher<AuthenticationResponse> {
        return Flowable.create({emitter: FlowableEmitter<AuthenticationResponse> ->
            val identity = authenticationRequest?.identity
            val password = authenticationRequest?.secret
            LOG.info("User {$identity} tries to login..")

            val userInfo = userRepositoryService.findUserByName(identity as String)
            if(passwordEncoderService.matches(password as String, userInfo.password)) {
                emitter.onNext(UserDetails(identity, listOf()))
                emitter.onComplete()
            } else {
                emitter.onError(AuthenticationException(AuthenticationFailed()))
            }
        }, BackpressureStrategy.ERROR)
    }
}