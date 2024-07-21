package eu.karcags.graph.dsl.builders

import eu.karcags.graph.Graph
import eu.karcags.graph.Rule

class GraphBuilder : AbstractBuilder<Graph>() {

    private var rules: List<Rule> = emptyList()

    fun addRule(rule: Rule) {
        rules = rules + rule
    }

    override fun build() = Graph(rules)
}