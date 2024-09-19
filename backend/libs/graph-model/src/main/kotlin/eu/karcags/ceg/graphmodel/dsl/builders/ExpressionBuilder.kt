package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.Definition

class ExpressionBuilder : AbstractBuilder<Definition>() {
    var expression: String = ""

    override fun build(): Definition = Definition(expression, null)
}