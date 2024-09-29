package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.Rule
import eu.karcags.ceg.graphmodel.exceptions.GraphException

class GraphBuilder : AbstractBuilder<Graph>() {

    private var rules: List<Rule> = emptyList()

    fun addRule(rule: Rule) {
        rules = rules + rule
    }

    fun nextRuleId(): Int {
        return rules.size + 1
    }

    override fun build() = Graph(rules)

    override fun validate(): Boolean {
        val causeNodes = rules
            .map { collectCauseNodes(it.cause) }
            .flatten()
            .map { it.displayName }

        if (causeNodes.size != causeNodes.toSet().size) {
            throw GraphException.ValidateException("Cause node display names must be unique.")
        }

        return true
    }

    private fun collectCauseNodes(node: Node): List<Node.Cause> {
        return when (node) {
            is Node.Cause -> listOf(node)
            is Node.UnaryAction.Not -> collectCauseNodes(node.inner)
            is Node.BinaryAction.Or -> collectCauseNodes(node.left) + collectCauseNodes(node.right)
            is Node.BinaryAction.And -> collectCauseNodes(node.left) + collectCauseNodes(node.right)
            else -> emptyList()
        }
    }
}