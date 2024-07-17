package eu.karcags.graph.dsl.builders

import eu.karcags.graph.Graph
import eu.karcags.graph.Rule

class GraphBuilder {
    
    private val rules: List<Rule> = emptyList()
    
    fun addRule(edge: Rule) {
        rules.plus(edge)
    }
        
    fun build() = Graph(rules)
}