package eu.karcags.ceg.common

fun <T> List<T>.changeIndex(index: Int, value: T): List<T> {
    return mapIndexed { idx, it -> if (idx == index) value else it }
}