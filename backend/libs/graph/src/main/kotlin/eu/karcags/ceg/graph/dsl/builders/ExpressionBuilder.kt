package eu.karcags.ceg.graph.dsl.builders

import eu.karcags.ceg.graph.models.Definition

class ExpressionBuilder : AbstractBuilder<Definition>() {
    var expression: String = ""

    override fun build(): Definition = Definition(expression, null)
}