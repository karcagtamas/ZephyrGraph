package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.Definition

class StatementBuilder : AbstractBuilder<Definition>() {
    var statement: String = ""

    override fun build(): Definition = Definition(null, statement)
}