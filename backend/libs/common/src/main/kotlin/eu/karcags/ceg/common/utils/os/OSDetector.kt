package eu.karcags.ceg.common.utils.os

import java.util.*

/**
 * Operating System detector.
 *
 * @constructor creates a utils library for OS detection
 */
class OSDetector {

    /**
     * Determine the type of the operating system from the system name.
     * @return the type of the operating system or null if the system name is unknown
     */
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

    /**
     * Gets the shortname of the current running environment (operating system).
     * @return the name of the operating system
     */
    fun getOSName(): String = System.getProperty("os.name").lowercase(Locale.getDefault())
}

