package eu.karcags.ceg.common.utils.os

import java.util.*

class OSDetector {
    fun determineOS(): OS? {
        val uname = getOSName()

        return when {
            "win" in uname -> OS.Windows
            "nix" in uname || "nux" in uname || "aix" in uname -> OS.Linux
            "mac" in uname -> OS.MacOS
            "sunos" in uname -> OS.Solaris
            else -> null
        }
    }

    fun getOSName(): String = System.getProperty("os.name").lowercase(Locale.getDefault())
}

