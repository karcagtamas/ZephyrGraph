package eu.karcags.graph.dsl.builders

import eu.karcags.graph.Definition

class StatementBuilder : AbstractBuilder<Definition>() {
    var statement: String = ""

    override fun build(): Definition = Definition(null, statement)
}