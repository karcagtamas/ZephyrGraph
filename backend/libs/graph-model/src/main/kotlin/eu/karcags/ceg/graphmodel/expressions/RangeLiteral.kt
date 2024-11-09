package eu.karcags.ceg.graphmodel.expressions

import kotlin.reflect.KClass

class RangeLiteral<T : Comparable<T>>(private val range: ClosedRange<T>) : Operand {

    override fun toString(): String = "lit(${range.start}..${range.endInclusive})"

    override fun getType(): KClass<*> = range.start::class

    override fun isVariable(): Boolean = false

    override fun simplified(): Operand = this
}