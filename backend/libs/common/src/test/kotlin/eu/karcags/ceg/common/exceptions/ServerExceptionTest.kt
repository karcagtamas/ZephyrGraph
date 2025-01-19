package eu.karcags.ceg.common.exceptions

import io.ktor.http.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class ServerExceptionTest {

    @Test
    fun shouldCreateASimpleException() {
        val exception = ServerException("error message")

        assertEquals("error message", exception.message)
        assertEquals(HttpStatusCode.InternalServerError, exception.status)
    }

    @Test
    fun shouldCreateAnExceptionWithStatus() {
        val exception = ServerException("error message", HttpStatusCode.BadRequest)

        assertEquals("error message", exception.message)
        assertEquals(HttpStatusCode.BadRequest, exception.status)
    }

    @Test
    fun shouldCreateAnAuthenticationException() {
        val exception = ServerException.Authentication("error message")

        assertEquals("error message", exception.message)
        assertEquals(HttpStatusCode.InternalServerError, exception.status)
    }

    @Test
    fun shouldCreateAnAuthenticationServerException() {
        val exception = ServerException.Authentication("error message")

        assertIs<ServerException>(exception)
    }

    @Test
    fun shouldCreateANotFoundException() {
        val exception = ServerException.NotFound()

        assertEquals("Not found", exception.message)
        assertEquals(HttpStatusCode.NotFound, exception.status)
    }

    @Test
    fun shouldCreateANotFoundServerException() {
        val exception = ServerException.NotFound()

        assertIs<ServerException>(exception)
    }
}