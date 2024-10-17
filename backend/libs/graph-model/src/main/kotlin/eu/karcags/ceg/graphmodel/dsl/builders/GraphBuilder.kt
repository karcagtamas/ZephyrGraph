package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.Rule
import eu.karcags.ceg.graphmodel.exceptions.GraphException

class GraphBuilder : AbstractBuilder<Graph>() {

    private var rules: List<Rule> = emptyList()

    private var nodes = mutableSetOf<Node.Cause>()

    fun addRule(rule: Rule) {
        rules = rules + rule
    }

    fun nextRuleId(): Int {
        return rules.size + 1
    }

    fun addNode(node: Node.Cause) {
        nodes.add(node)
    }

    fun getGraphNodes(): Set<Node.Cause> {
        return nodes.toSet()
    }

    override fun build() = Graph(rules)

    override fun validate(): Boolean {
        val causeNodes = rules
            .map { collectCauseNodes(it.cause) }
            .flatten()
            .toSet() + nodes

        if (causeNodes.size != causeNodes.map { it.displayName }.toSet().size) {
            throw GraphException.ValidateException("All cause name (display name) must be unique in the graph.")
        }

        return true
    }

    private fun collectCauseNodes(node: Node): List<Node.Cause> {
        return when (node) {
            is Node.Cause -> listOf(node)
            is Node.UnaryAction.Not -> collectCauseNodes(node.inner)
            is Node.BinaryAction.Or -> node.nodes.map { collectCauseNodes(it) }.flatten()
            is Node.BinaryAction.And -> node.nodes.map { collectCauseNodes(it) }.flatten()
            else -> emptyList()
        }
    }
}