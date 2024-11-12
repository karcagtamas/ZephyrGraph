package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.expressions.Variable

class VariablesBuilder : AbstractBuilder<List<Variable<*>>>() {

    private val variables = mutableListOf<Variable<*>>()

    fun add(variable: Variable<*>) {
        variables.add(variable)
    }

    override fun build(): List<Variable<*>> = variables
}