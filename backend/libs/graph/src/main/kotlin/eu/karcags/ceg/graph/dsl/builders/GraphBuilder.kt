package eu.karcags.ceg.graph.dsl.builders

import eu.karcags.ceg.graph.Graph
import eu.karcags.ceg.graph.Rule

class GraphBuilder : AbstractBuilder<Graph>() {

    private var rules: List<Rule> = emptyList()

    fun addRule(rule: Rule) {
        rules = rules + rule
    }

    override fun build() = Graph(rules)
}