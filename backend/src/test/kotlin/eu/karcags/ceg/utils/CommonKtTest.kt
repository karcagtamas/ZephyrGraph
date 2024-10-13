package eu.karcags.ceg.utils

import io.ktor.http.*
import kotlin.test.Test
import kotlin.test.assertEquals

class CommonKtTest {

    @Test
    fun shouldWrapTheSameData() {
        val data = "Alma"
        val result = data.wrapping()

        assertEquals(data, result.data)
    }

    @Test
    fun shouldWrapWithStatusOKByDefault() {
        val data = "Alma"
        val result = data.wrapping()

        assertEquals(HttpStatusCode.OK.value, result.status)
    }

    @Test
    fun shouldWrapWithStatus500() {
        val data = "Alma"
        val result = data.wrapping(HttpStatusCode.InternalServerError)

        assertEquals(HttpStatusCode.InternalServerError.value, result.status)
    }

    @Test
    fun shouldWrapComplexDataStructure() {
        val data = listOf(1, 2, 3, 4, 5)
        val result = data.wrapping()

        assertEquals(data, result.data)
    }
}