package eu.karcags.ceg.graph.converters.logical

import eu.karcags.ceg.common.exceptions.GraphException
import eu.karcags.ceg.graph.Graph
import eu.karcags.ceg.graph.Node
import eu.karcags.ceg.graph.Rule
import eu.karcags.ceg.graph.converters.AbstractConverter
import eu.karcags.ceg.graph.converters.logical.resources.AbstractSignResource
import eu.karcags.ceg.graph.converters.logical.resources.Resources

class LogicalGraphConverter(private val resource: AbstractSignResource) : AbstractConverter<LogicalGraph>() {

    constructor(resource: Resources) : this(resource.getResource())

    override fun convert(graph: Graph): LogicalGraph {
        val definitions = graph.rules.map {
            convertRule(it)
        }

        return LogicalGraph(definitions)
    }

    private fun convertRule(rule: Rule): LogicalDefinition {
        return BinaryLogicalDefinition.Implicate(convertNode(rule.source), convertNode(rule.target), resource.IMPLICATE)
    }

    private fun convertNode(node: Node): LogicalDefinition {
        return when (node) {
            is Node.EffectNode -> NodeDefinition(node.id, node.displayName)
            is Node.CauseNode -> NodeDefinition(node.id, node.displayName)
            is Node.UnActionNode -> when (node) {
                is Node.UnActionNode.NotNode -> UnaryLogicalDefinition.Not(convertNode(node.inner), resource.NOT)
                else -> throw GraphException.ConvertException("Invalid unary node: ${node.id}")
            }

            is Node.BiActionNode -> when (node) {
                is Node.BiActionNode.OrNode -> BinaryLogicalDefinition.Or(convertNode(node.left), convertNode(node.right), resource.OR)
                is Node.BiActionNode.AndNode -> BinaryLogicalDefinition.And(convertNode(node.left), convertNode(node.right), resource.AND)
                else -> throw GraphException.ConvertException("Invalid binary node: ${node.id}")
            }

            else -> throw GraphException.ConvertException("Invalid node: ${node.id}")
        }
    }
}