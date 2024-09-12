package eu.karcags.ceg.common.utils.os

import java.util.*

fun determineOS(): OS? {
    val uname = System.getProperty("os.name").lowercase(Locale.getDefault())

    return when {
        "win" in uname -> OS.Windows
        "nix" in uname || "nux" in uname || "aix" in uname -> OS.Linux
        "mac" in uname -> OS.MacOS
        "sunos" in uname -> OS.Solaris
        else -> null
    }
}