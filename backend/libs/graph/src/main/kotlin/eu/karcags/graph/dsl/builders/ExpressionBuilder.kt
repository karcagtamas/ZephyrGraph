package eu.karcags.graph.dsl.builders

import eu.karcags.graph.Definition

class ExpressionBuilder : AbstractBuilder<Definition>() {
    var expression: String = ""

    override fun build(): Definition = Definition(expression, null)
}