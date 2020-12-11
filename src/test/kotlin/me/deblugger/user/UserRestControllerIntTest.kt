package me.deblugger.user

import io.micronaut.http.HttpStatus
import me.deblugger.BaseRestIntTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import javax.inject.Inject

class UserRestControllerIntTest : BaseRestIntTest() {

    @Inject
    lateinit var userRepository: UserRepository

    private val username = "test"
    private val password = "pass"
    private val email = "test@email.com"

    @BeforeEach
    @AfterEach
    fun setup() {
        userRepository.deleteAll()
    }

    @Test
    fun `should create user and then get user information`() {
        var allUsers = userRepository.findAll()
        assertThat(allUsers.size).isEqualTo(0)

        val userCreationRequestBody = UserCreationRequestBody(username, password, email)

        val postResult = doPost(HttpStatus.CREATED, "/users/register", userCreationRequestBody)

        assertThat(postResult.body().asString()).contains(username)

        allUsers = userRepository.findAll()
        assertThat(allUsers.size).isEqualTo(1)
        val user = allUsers[0]

        assertThat(user.name).isEqualTo(userCreationRequestBody.name)

        val getResult = doGet(HttpStatus.OK, "/users/name/${user.name}")

        assertThat(getResult.body().asString()).isEqualToIgnoringWhitespace("""
                {
                    "name":"${user.name}",
                    "email":"${user.email}"
                }
        """.trimIndent())
    }

    @Test
    fun `getByUserName - should return a 404 if not exists`() {
        val result = doGet(HttpStatus.NOT_FOUND, "/users/name/NON-EXISTING-USER")

        assertThat(result.body().asString()).isEqualToIgnoringWhitespace("""
                [
                    "User not found.",
                    "User with name: NON-EXISTING-USER was not found"
                ]
        """.trimIndent())
    }

    @Test
    fun `createUser - should return a 409 if already exists`() {
        userRepository.update(UserEntity(username, password, email))
        val userCreationRequestBody = UserCreationRequestBody(username, password, email)

        val result = doPost(HttpStatus.CONFLICT, "/users/register", userCreationRequestBody)

        assertThat(result.body().asString()).isEqualToIgnoringWhitespace("""
                [
                    "User already exists.",
                    "User with name: $username or email: $email already exists"
                ]
        """.trimIndent())
    }
}