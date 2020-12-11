package me.deblugger

import io.micronaut.http.HttpStatus
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import javax.inject.Inject

// TODO: Test Containers to not have to start postgres docker
@MicronautTest(transactional = false, environments = ["inttest"])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseRestIntTest {

    @Inject
    lateinit var server: EmbeddedServer

    @BeforeEach
    fun init() {
        RestAssured.reset()
        RestAssured.requestSpecification = RequestSpecBuilder()
                .setBaseUri(server.url.toString())
                .setContentType(ContentType.JSON)
                .build()
    }

    fun doGet(expectedHttpStatus: HttpStatus, path: String, vararg pathParameters: Any): ExtractableResponse<Response> {
        return RestAssured.given().
                `when`()
                .get(path, *pathParameters)
                .then()
                .statusCode(expectedHttpStatus.code)
                .contentType(ContentType.JSON).extract()
    }

    fun doGetWithHeaders(expectedHttpStatus: HttpStatus, path: String, headers: Map<String, String>, vararg pathParameters: Any): ExtractableResponse<Response> {
        return RestAssured.given().
        `when`()
                .headers(headers)
                .get(path, *pathParameters)
                .then()
                .statusCode(expectedHttpStatus.code)
                .contentType(ContentType.JSON).extract()
    }

    fun doGetWithNoResponse(expectedHttpStatus: HttpStatus, path: String, vararg pathParameters: Any): ExtractableResponse<Response> {
        return RestAssured.given().
        `when`()
                .get(path, *pathParameters)
                .then()
                .statusCode(expectedHttpStatus.code).extract()
    }

    fun doPost(expectedHttpStatus: HttpStatus, path: String, body: Any, vararg pathParameters: Any): ExtractableResponse<Response> {
        return RestAssured.given()
                .body(body)
                .`when`()
                .post(path, *pathParameters)
                .then()
                .statusCode(expectedHttpStatus.code)
                .contentType(ContentType.JSON)
                .extract()
    }

    fun doPut(expectedHttpStatus: HttpStatus, path: String, body: Any, vararg pathParameters: Any): ExtractableResponse<Response> {
        return RestAssured.given()
                .body(body)
                .`when`()
                .put(path, *pathParameters)
                .then()
                .statusCode(expectedHttpStatus.code)
                .extract()
    }

    fun doDelete(expectedHttpStatus: HttpStatus, path: String, vararg pathParameters: Any): ExtractableResponse<Response> {
        return RestAssured.given()
                .`when`()
                .delete(path, *pathParameters)
                .then()
                .statusCode(expectedHttpStatus.code).extract()
    }
}