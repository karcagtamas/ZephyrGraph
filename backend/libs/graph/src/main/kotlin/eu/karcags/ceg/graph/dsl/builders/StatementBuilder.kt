package eu.karcags.ceg.graph.dsl.builders

import eu.karcags.ceg.graph.models.Definition

class StatementBuilder : AbstractBuilder<Definition>() {
    var statement: String = ""

    override fun build(): Definition = Definition(null, statement)
}