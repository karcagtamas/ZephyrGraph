package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.expressions.Variable

/**
 * Variables builder.
 * @property variables the collected variables
 * @constructor creates a variables builder
 */
class VariablesBuilder : AbstractBuilder<List<Variable<*>>>() {

    private val variables = mutableListOf<Variable<*>>()

    /**
     * Adds variable to the collection.
     * @param variable the new variable
     */
    fun add(variable: Variable<*>) {
        variables.add(variable)
    }

    override fun build(): List<Variable<*>> = variables
}