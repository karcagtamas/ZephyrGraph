package eu.karcags.ceg.graph.dsl.builders

import eu.karcags.ceg.graph.Definition

class StatementBuilder : AbstractBuilder<Definition>() {
    var statement: String = ""

    override fun build(): Definition = Definition(null, statement)
}