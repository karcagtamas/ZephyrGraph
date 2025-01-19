package eu.karcags.ceg.graph.converters.visual

import eu.karcags.ceg.graph.converters.AbstractGraphConverter
import eu.karcags.ceg.graph.converters.visual.components.NodeMeta
import eu.karcags.ceg.graph.converters.visual.components.VisualEdge
import eu.karcags.ceg.graph.converters.visual.components.VisualNode
import eu.karcags.ceg.graph.exceptions.GraphConvertException
import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.Rule

/**
 * Visual graph converter. Converts a [Graph] into [VisualGraph].
 * @constructor creates a visual graph converter
 */
class VisualGraphConverter : AbstractGraphConverter<VisualGraph>() {

    override fun convert(entity: Graph): VisualGraph {
        val result = entity.rules
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
                    NodeMeta.EffectMeta(node.expression.toString(), node.description)
                )
            )

            is Node.Cause -> NodeConstructionResult.Single(
                VisualNode(
                    node.id,
                    node.displayName,
                    NodeMeta.CauseMeta(node.expression.toString(), node.description)
                )
            )

            is Node.BinaryAction -> {
                val meta = when (node) {
                    is Node.BinaryAction.And -> NodeMeta.ActionMeta(
                        node.expression.toString(),
                        node.description,
                        Action.AND
                    )

                    is Node.BinaryAction.Or -> NodeMeta.ActionMeta(
                        node.expression.toString(),
                        node.description,
                        Action.OR
                    )

                    else -> throw GraphConvertException("The provided (${node::javaClass}) binary action node type is invalid")
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
                    is Node.UnaryAction.Not -> NodeMeta.ActionMeta(
                        node.expression.toString(),
                        node.description,
                        Action.NOT
                    )

                    else -> throw GraphConvertException("The provided (${node::javaClass}) unary action node type is invalid")
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

            else -> throw GraphConvertException("The provided (${node::javaClass}) node type is invalid")
        }
    }

    /**
     * Node construction result.
     * @property node the connected visual node
     * @property additionalEdges additionally created edges
     * @property additionalNodes additionally created nodes
     * @constructor creates a construction result
     * @param node the connected visual node
     * @param additionalEdges additionally created edges
     * @param additionalNodes additionally created nodes
     */
    open class NodeConstructionResult(
        val node: VisualNode,
        val additionalEdges: Set<VisualEdge>,
        val additionalNodes: Set<VisualNode>
    ) {
        /**
         * Single node construction result.
         * @constructor creates a single construction results
         * @param node the connected visual node
         */
        class Single(node: VisualNode) : NodeConstructionResult(node, emptySet(), emptySet())
    }
}