package eu.karcags.ceg.graph.converters.logical

import eu.karcags.ceg.graph.converters.AbstractConverter
import eu.karcags.ceg.graph.converters.logical.definitions.*
import eu.karcags.ceg.graph.converters.logical.refiners.AbstractRefiner
import eu.karcags.ceg.graph.converters.logical.resources.AbstractSignResource
import eu.karcags.ceg.graph.converters.logical.resources.PremadeResources
import eu.karcags.ceg.graph.exceptions.GraphConvertException
import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.Rule

class LogicalGraphConverter(private val resource: AbstractSignResource) : AbstractConverter<LogicalGraph>() {

    val refiners = mutableSetOf<AbstractRefiner>()

    constructor(resource: PremadeResources) : this(resource.getResource())

    override fun convert(graph: Graph): LogicalGraph {
        val definitions = graph.rules.map {
            convertRule(it)
        }

        return applyRefiners(LogicalGraph(definitions))
    }

    fun addRefiners(applier: (AbstractSignResource) -> List<AbstractRefiner>): LogicalGraphConverter {
        refiners.addAll(applier(resource))

        return this
    }

    private fun convertRule(rule: Rule): LogicalDefinition {
        return ImplicateDefinition(convertNode(rule.cause), convertNode(rule.effect), resource.IMPLICATE)
    }

    private fun convertNode(node: Node): LogicalDefinition {
        return when (node) {
            is Node.Effect -> NodeDefinition(node.id, node.displayName)
            is Node.Cause -> NodeDefinition(node.id, node.displayName)
            is Node.UnaryAction -> when (node) {
                is Node.UnaryAction.Not -> NotDefinition(convertNode(node.inner), resource.NOT)
                else -> throw GraphConvertException("Invalid unary node: ${node.id}")
            }

            is Node.BinaryAction -> when (node) {
                is Node.BinaryAction.Or -> OrDefinition(convertNode(node.left), convertNode(node.right), resource.OR)
                is Node.BinaryAction.And -> AndDefinition(convertNode(node.left), convertNode(node.right), resource.AND)
                else -> throw GraphConvertException("Invalid binary node: ${node.id}")
            }

            else -> throw GraphConvertException("Invalid node: ${node.id}")
        }
    }

    private fun applyRefiners(graph: LogicalGraph): LogicalGraph {
        return refiners
            .fold(graph) { g, refiner ->
                refiner.refine(g)
            }
    }
}