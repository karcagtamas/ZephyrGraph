package eu.karcags.ceg.graph.converters.visual

import eu.karcags.ceg.common.exceptions.GraphException
import eu.karcags.ceg.graph.Definition
import eu.karcags.ceg.graph.Graph
import eu.karcags.ceg.graph.Rule
import eu.karcags.ceg.graph.converters.AbstractConverter

class VisualGraphConverter : AbstractConverter<VisualGraph>() {

    override fun convert(graph: Graph): VisualGraph {
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

        val edges = causeNodeResult.additionalEdges + effectNodeResult.additionalEdges + edge
        val nodes = causeNodeResult.additionalNodes + effectNodeResult.additionalNodes + setOf(causeNodeResult.node, effectNodeResult.node)

        return Pair(edges, nodes)
    }

    private fun constructNode(node: eu.karcags.ceg.graph.Node): NodeConstructionResult {
        return when (node) {
            is eu.karcags.ceg.graph.Node.EffectNode -> NodeConstructionResult.Single(Node(node.id, node.displayName, NodeMeta.EffectMeta(convertDefinition(node.definition), node.description)))

            is eu.karcags.ceg.graph.Node.CauseNode -> NodeConstructionResult.Single(Node(node.id, node.displayName, NodeMeta.CauseMeta(convertDefinition(node.definition), node.description)))

            is eu.karcags.ceg.graph.Node.BiActionNode -> {
                val meta = when (node) {
                    is eu.karcags.ceg.graph.Node.BiActionNode.AndNode -> NodeMeta.ActionMeta(convertDefinition(node.definition), node.description, Action.AND)
                    is eu.karcags.ceg.graph.Node.BiActionNode.OrNode -> NodeMeta.ActionMeta(convertDefinition(node.definition), node.description, Action.OR)
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

            is eu.karcags.ceg.graph.Node.UnActionNode -> {
                val meta = when (node) {
                    is eu.karcags.ceg.graph.Node.UnActionNode.NotNode -> NodeMeta.ActionMeta(convertDefinition(node.definition), node.description, Action.NOT)
                    else -> throw GraphException.ConvertException("Action Node type is invalid")
                }

                val current = Node(node.id, node.displayName, meta)
                val innerResult = constructNode(node.inner)
                val edge = Edge(innerResult.node, current)

                NodeConstructionResult(current, innerResult.additionalEdges + edge, innerResult.additionalNodes + innerResult.node)
            }

            else -> throw GraphException.ConvertException("Node type is invalid")
        }
    }

    open class NodeConstructionResult(val node: Node, val additionalEdges: Set<Edge>, val additionalNodes: Set<Node>) {

        class Single(node: Node) : NodeConstructionResult(node, emptySet(), emptySet())
    }

    private fun convertDefinition(definition: Definition?): eu.karcags.ceg.graph.converters.visual.Definition? {
        return if (definition != null) eu.karcags.ceg.graph.converters.visual.Definition(definition.expression, definition.statement) else null
    }
}