package eu.karcags.ceg.graph.converters.logical

import eu.karcags.ceg.graph.converters.AbstractGraphConverter
import eu.karcags.ceg.graph.converters.logical.definitions.*
import eu.karcags.ceg.graph.converters.logical.refiners.AbstractRefiner
import eu.karcags.ceg.graph.exceptions.GraphConvertException
import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.Rule

class LogicalGraphConverter : AbstractGraphConverter<LogicalGraph>() {

    val refiners = mutableSetOf<AbstractRefiner>()

    override fun convert(entity: Graph): LogicalGraph {
        return convertToStepped(entity).final.graph
    }

    fun convertToStepped(graph: Graph): SteppedLogicalGraph {
        val definitions = graph.rules.map {
            convertRule(it)
        }

        return applyRefiners(LogicalGraph(definitions))
    }

    fun addRefiners(applier: () -> List<AbstractRefiner>): LogicalGraphConverter {
        refiners.addAll(applier())

        return this
    }

    private fun convertRule(rule: Rule): LogicalGraphDefinition {
        return LogicalGraphDefinition(NodeDefinition(rule.effect.id, rule.effect.displayName, null), convertNode(rule.cause))
    }

    private fun convertNode(node: Node): LogicalDefinition {
        return when (node) {
            is Node.Effect -> NodeDefinition(node.id, node.displayName, null)
            is Node.Cause -> NodeDefinition(node.id, node.displayName, node.expression)
            is Node.UnaryAction -> when (node) {
                is Node.UnaryAction.Not -> NotDefinition(convertNode(node.inner))
                else -> throw GraphConvertException("Invalid unary node: ${node.id}")
            }

            is Node.BinaryAction -> when (node) {
                is Node.BinaryAction.Or -> OrDefinition(node.nodes.map { convertNode(it) }.toSet())
                is Node.BinaryAction.And -> AndDefinition(node.nodes.map { convertNode(it) }.toSet())
                else -> throw GraphConvertException("Invalid binary node: ${node.id}")
            }

            else -> throw GraphConvertException("Invalid node: ${node.id}")
        }
    }

    private fun applyRefiners(graph: LogicalGraph): SteppedLogicalGraph {
        return refiners
            .fold(SteppedLogicalGraph(SteppedRefinerItem("init", graph), emptyList())) { g, refiner ->
                SteppedLogicalGraph(
                    SteppedRefinerItem(refiner.key, refiner.refine(g.final.graph)),
                    g.prevSteps + g.final
                )
            }
    }

    data class SteppedLogicalGraph(val final: SteppedRefinerItem, val prevSteps: List<SteppedRefinerItem>)

    data class SteppedRefinerItem(val key: String, val graph: LogicalGraph)
}