package eu.karcags.graph.dsl.builders

import eu.karcags.graph.Graph
import eu.karcags.graph.Rule

class GraphBuilder {
    
    private var rules: List<Rule> = emptyList()
    
    fun addRule(rule: Rule) {
        rules = rules + rule
    }
        
    fun build() = Graph(rules)
}