package eu.karcags.ceg.graphmodel.expressions

import kotlin.reflect.KClass

/**
 * Closed range literal.
 * @property range the wrapped range value
 * @constructor creates a closed range literal from the closed [range] value
 * @param range the wrapped range value
 * @param T the type of the inner range (ranged values)
 */
data class ClosedRangeLiteral<T : Comparable<T>>(private val range: ClosedRange<T>) : Operand {

    override fun toString(): String = "lit(${stringifyRange()})"

    override fun getType(): KClass<*> = range.start::class

    override fun isVariable(): Boolean = false

    override fun simplified(): Operand = this

    /**
     * Stringifies the range with the given [separator]
     * @return the parse text representation with the [separator]
     */
    fun stringifyRange(separator: String = ".."): String = "${range.start}$separator${range.endInclusive}"
}