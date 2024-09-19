package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.Rule

class GraphBuilder : AbstractBuilder<Graph>() {

    private var rules: List<Rule> = emptyList()

    fun addRule(rule: Rule) {
        rules = rules + rule
    }

    override fun build() = Graph(rules)
}