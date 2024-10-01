package eu.karcags.ceg.graphmodel.expressions

data class Variable(val name: String) : Operand {

    override fun toString(): String {
        return "var($name)"
    }
}