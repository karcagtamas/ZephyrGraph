package eu.karcags.ceg.graphmodel.expressions

data class Literal<T>(val value: T) : Operand {

    override fun toString(): String {
        return "lit($value)"
    }
}