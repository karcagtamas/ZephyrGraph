package eu.karcags.ceg.graph.converters.visual

import eu.karcags.ceg.common.exceptions.GraphException
import eu.karcags.ceg.graph.Definition
import eu.karcags.ceg.graph.Graph
import eu.karcags.ceg.graph.Node
import eu.karcags.ceg.graph.Rule
import eu.karcags.ceg.graph.converters.AbstractConverter

class VisualGraphConverter : AbstractConverter<VisualGraph>() {

    override fun convert(graph: Graph): VisualGraph {
        val result = graph.rules
            .map { convertRule(it) }
            .fold(Pair(emptySet<VisualEdge>(), emptySet<VisualNode>())) { a, b ->
                val edges = a.first + b.first
                val nodes = a.second + b.second
                Pair(edges, nodes)
            }

        return VisualGraph(result.first.toList(), result.second.toList())
    }

    private fun convertRule(rule: Rule): Pair<Set<VisualEdge>, Set<VisualNode>> {
        val causeNodeResult = constructNode(rule.source)
        val effectNodeResult = constructNode(rule.target)
        val edge = VisualEdge(causeNodeResult.node, effectNodeResult.node)

        val edges = causeNodeResult.additionalEdges + effectNodeResult.additionalEdges + edge
        val nodes = causeNodeResult.additionalNodes + effectNodeResult.additionalNodes + setOf(causeNodeResult.node, effectNodeResult.node)

        return Pair(edges, nodes)
    }

    private fun constructNode(node: Node): NodeConstructionResult {
        return when (node) {
            is Node.EffectNode -> NodeConstructionResult.Single(VisualNode(node.id, node.displayName, NodeMeta.EffectMeta(convertDefinition(node.definition), node.description)))

            is Node.CauseNode -> NodeConstructionResult.Single(VisualNode(node.id, node.displayName, NodeMeta.CauseMeta(convertDefinition(node.definition), node.description)))

            is Node.BiActionNode -> {
                val meta = when (node) {
                    is Node.BiActionNode.AndNode -> NodeMeta.ActionMeta(convertDefinition(node.definition), node.description, Action.AND)
                    is Node.BiActionNode.OrNode -> NodeMeta.ActionMeta(convertDefinition(node.definition), node.description, Action.OR)
                    else -> throw GraphException.ConvertException("Action Node type is invalid")
                }

                val current = VisualNode(node.id, node.displayName, meta)
                val leftResult = constructNode(node.left)
                val rightResult = constructNode(node.right)
                val leftEdge = VisualEdge(leftResult.node, current)
                val rightEdge = VisualEdge(rightResult.node, current)

                val edges = leftResult.additionalEdges + rightResult.additionalEdges + setOf(leftEdge, rightEdge)
                val nodes = leftResult.additionalNodes + rightResult.additionalNodes + setOf(leftResult.node, rightResult.node)

                NodeConstructionResult(current, edges, nodes)
            }

            is Node.UnActionNode -> {
                val meta = when (node) {
                    is Node.UnActionNode.NotNode -> NodeMeta.ActionMeta(convertDefinition(node.definition), node.description, Action.NOT)
                    else -> throw GraphException.ConvertException("Action Node type is invalid")
                }

                val current = VisualNode(node.id, node.displayName, meta)
                val innerResult = constructNode(node.inner)
                val edge = VisualEdge(innerResult.node, current)

                NodeConstructionResult(current, innerResult.additionalEdges + edge, innerResult.additionalNodes + innerResult.node)
            }

            else -> throw GraphException.ConvertException("Node type is invalid")
        }
    }

    open class NodeConstructionResult(val node: VisualNode, val additionalEdges: Set<VisualEdge>, val additionalNodes: Set<VisualNode>) {

        class Single(node: VisualNode) : NodeConstructionResult(node, emptySet(), emptySet())
    }

    private fun convertDefinition(definition: Definition?): VisualDefinition? {
        return if (definition != null) VisualDefinition(definition.expression, definition.statement) else null
    }
}