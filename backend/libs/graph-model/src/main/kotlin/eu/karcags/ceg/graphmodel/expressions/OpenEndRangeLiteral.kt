package eu.karcags.ceg.graphmodel.expressions

import kotlin.reflect.KClass

/**
 * Open-end range literal.
 * @property range the wrapped range value
 * @constructor creates an open end range literal from the open-end [range] value
 * @param range the wrapped range value
 * @param T the type of the inner range (ranged values)
 */
data class OpenEndRangeLiteral<T : Comparable<T>>(private val range: OpenEndRange<T>) : Operand {
    override fun toString(): String = "lit(${stringifyRange()})"

    override fun getType(): KClass<*> = range.start::class

    override fun isVariable(): Boolean = false

    override fun simplified(): Operand = this

    /**
     * Stringifies the range with the given [separator]
     * @return the parse text representation with the [separator]
     */
    fun stringifyRange(separator: String = "..<"): String = "${range.start}$separator${range.endExclusive}"
}