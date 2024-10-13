package eu.karcags.ceg.common.utils.os

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals

class OSDetectorKtTest {

    @Test
    fun determineWindows() {
        val service = mockk<OSDetector>()
        every { service.getOSName() } returns "win"
        every { service.determineOS() } answers { callOriginal() }

        val os = service.determineOS()

        verify { service.getOSName() }
        assertEquals(os, OS.Windows)
    }

    @Test
    fun determineWindows2() {
        val service = mockk<OSDetector>()
        every { service.getOSName() } returns "windows"
        every { service.determineOS() } answers { callOriginal() }

        val os = service.determineOS()

        verify { service.getOSName() }
        assertEquals(os, OS.Windows)
    }
}