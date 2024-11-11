package eu.karcags.ceg.graphmodel.expressions

import kotlin.reflect.KClass

class OpenEndRangeLiteral<T : Comparable<T>>(private val range: OpenEndRange<T>) : Operand {
    override fun toString(): String = "lit(${stringifyRange()})"

    override fun getType(): KClass<*> = range.start::class

    override fun isVariable(): Boolean = false

    override fun simplified(): Operand = this

    fun stringifyRange(separator: String = ".."): String = "${range.start}$separator${range.endExclusive}"
}