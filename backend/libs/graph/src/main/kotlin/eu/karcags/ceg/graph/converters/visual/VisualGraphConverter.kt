package eu.karcags.ceg.graph.converters.visual

import eu.karcags.ceg.graph.converters.AbstractConverter
import eu.karcags.ceg.graph.converters.visual.components.NodeMeta
import eu.karcags.ceg.graph.converters.visual.components.VisualEdge
import eu.karcags.ceg.graph.converters.visual.components.VisualNode
import eu.karcags.ceg.graph.exceptions.GraphConvertException
import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.Rule

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
        val causeNodeResult = constructNode(rule.cause)
        val effectNodeResult = constructNode(rule.effect)
        val edge = VisualEdge(causeNodeResult.node, effectNodeResult.node)

        val edges = causeNodeResult.additionalEdges + effectNodeResult.additionalEdges + edge
        val nodes = causeNodeResult.additionalNodes + effectNodeResult.additionalNodes + setOf(
            causeNodeResult.node,
            effectNodeResult.node
        )

        return Pair(edges, nodes)
    }

    private fun constructNode(node: Node): NodeConstructionResult {
        return when (node) {
            is Node.Effect -> NodeConstructionResult.Single(
                VisualNode(
                    node.id,
                    node.displayName,
                    NodeMeta.EffectMeta(node.expression, node.description)
                )
            )

            is Node.Cause -> NodeConstructionResult.Single(
                VisualNode(
                    node.id,
                    node.displayName,
                    NodeMeta.CauseMeta(node.expression, node.description)
                )
            )

            is Node.BinaryAction -> {
                val meta = when (node) {
                    is Node.BinaryAction.And -> NodeMeta.ActionMeta(node.expression, node.description, Action.AND)
                    is Node.BinaryAction.Or -> NodeMeta.ActionMeta(node.expression, node.description, Action.OR)
                    else -> throw GraphConvertException("Action Node type is invalid")
                }

                val current = VisualNode(node.id, node.displayName, meta)
                val results = node.nodes.map { constructNode(it) }
                val resultEdges = results.map { VisualEdge(it.node, current) }

                val edges = results.map { it.additionalEdges }.flatten() + resultEdges
                val nodes = results.map { it.additionalNodes }.flatten() + results.map { it.node }

                NodeConstructionResult(current, edges.toSet(), nodes.toSet())
            }

            is Node.UnaryAction -> {
                val meta = when (node) {
                    is Node.UnaryAction.Not -> NodeMeta.ActionMeta(node.expression, node.description, Action.NOT)
                    else -> throw GraphConvertException("Action Node type is invalid")
                }

                val current = VisualNode(node.id, node.displayName, meta)
                val innerResult = constructNode(node.inner)
                val edge = VisualEdge(innerResult.node, current)

                NodeConstructionResult(
                    current,
                    innerResult.additionalEdges + edge,
                    innerResult.additionalNodes + innerResult.node
                )
            }

            else -> throw GraphConvertException("Node type is invalid")
        }
    }

    open class NodeConstructionResult(
        val node: VisualNode,
        val additionalEdges: Set<VisualEdge>,
        val additionalNodes: Set<VisualNode>
    ) {

        class Single(node: VisualNode) : NodeConstructionResult(node, emptySet(), emptySet())
    }
}