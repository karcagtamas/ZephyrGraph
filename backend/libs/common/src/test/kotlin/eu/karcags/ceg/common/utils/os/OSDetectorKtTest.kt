package eu.karcags.ceg.common.utils.os

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class OSDetectorKtTest {

    @ParameterizedTest
    @MethodSource("getData")
    fun shouldReturnTheProperOS(name: String, expected: OS?) {
        val service = mockk<OSDetector>()
        every { service.getOSName() } returns name
        every { service.determineOS() } answers { callOriginal() }

        val os = service.determineOS()

        verify { service.getOSName() }
        assertEquals(expected, os)
    }

    companion object {

        @JvmStatic
        fun getData(): List<Arguments> {
            return listOf(
                Arguments.of("win", OS.Windows),
                Arguments.of("windows", OS.Windows),
                Arguments.of("nix", OS.Linux),
                Arguments.of("linux", OS.Linux),
                Arguments.of("nux", OS.Linux),
                Arguments.of("aix", OS.Linux),
                Arguments.of("mac", OS.MacOS),
                Arguments.of("macos", OS.MacOS),
                Arguments.of("sunos", OS.Solaris),
                Arguments.of("alma", null),
            )
        }
    }
}