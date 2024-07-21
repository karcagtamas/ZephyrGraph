package eu.karcags.graph.visual

import eu.karcags.common.exceptions.GraphException
import eu.karcags.graph.Graph
import eu.karcags.graph.Rule

class GraphConverter {

    fun convert(graph: Graph): VisualGraph {
        val result = graph.rules
            .map { convertRule(it) }
            .fold(Pair(emptySet<Edge>(), emptySet<Node>())) { a, b ->
                val edges = a.first + b.first
                val nodes = a.second + b.second
                Pair(edges, nodes)
            }

        return VisualGraph(result.first.toList(), result.second.toList())
    }

    private fun convertRule(rule: Rule): Pair<Set<Edge>, Set<Node>> {
        val causeNodeResult = constructNode(rule.source)
        val effectNodeResult = constructNode(rule.target)
        val edge = Edge(causeNodeResult.node, effectNodeResult.node)

        val edges = causeNodeResult.additionalEdges + effectNodeResult.additionalEdges + setOf(edge)
        val nodes = causeNodeResult.additionalNodes + effectNodeResult.additionalNodes + setOf(causeNodeResult.node, effectNodeResult.node)

        return Pair(edges, nodes)
    }

    private fun constructNode(node: eu.karcags.graph.Node): NodeConstructionResult {
        return when (node) {
            is eu.karcags.graph.Node.EffectNode -> NodeConstructionResult.Single(Node(node.id, node.displayName, NodeMeta.EffectMeta()))

            is eu.karcags.graph.Node.CauseNode -> NodeConstructionResult.Single(Node(node.id, node.displayName, NodeMeta.CauseMeta()))

            is eu.karcags.graph.Node.ActionNode -> {
                val meta = when (node) {
                    is eu.karcags.graph.Node.ActionNode.AndNode -> NodeMeta.ActionMeta(Action.AND)
                    is eu.karcags.graph.Node.ActionNode.OrNode -> NodeMeta.ActionMeta(Action.OR)
                    else -> throw GraphException.ConvertException("Action Node type is invalid")
                }

                val current = Node(node.id, node.displayName, meta)
                val leftResult = constructNode(node.left)
                val rightResult = constructNode(node.right)
                val leftEdge = Edge(leftResult.node, current)
                val rightEdge = Edge(rightResult.node, current)

                val edges = leftResult.additionalEdges + rightResult.additionalEdges + setOf(leftEdge, rightEdge)
                val nodes = leftResult.additionalNodes + rightResult.additionalNodes + setOf(leftResult.node, rightResult.node)

                NodeConstructionResult(current, edges, nodes)
            }

            else -> throw GraphException.ConvertException("Node type is invalid")
        }
    }

    open class NodeConstructionResult(val node: Node, val additionalEdges: Set<Edge>, val additionalNodes: Set<Node>) {

        class Single(node: Node) : NodeConstructionResult(node, emptySet(), emptySet())
    }
}