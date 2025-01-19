package eu.karcags.ceg.common

/**
 * Switches the list one element with another on the given [index].
 * @param index index of the target list element.
 * @param value the new value of the list element on the given [index].
 * @return the modified list
 */
fun <T> List<T>.changeByIndex(index: Int, value: T): List<T> {
    return mapIndexed { idx, it -> if (idx == index) value else it }
}