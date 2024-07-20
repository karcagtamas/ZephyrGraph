package eu.karcags.graph.visual

import eu.karcags.graph.Graph

class GraphConverter {

    fun convert(graph: Graph): VisualGraph {
        for (rule in graph.rules) {

            val effectNode = constructNode(rule.target)
        }

        return VisualGraph(emptyList(), emptyList())
    }

    private fun determineParts() {}

    private fun constructNode(node: eu.karcags.graph.Node): Node {
        val meta = when (node) {
            is eu.karcags.graph.Node.EffectNode -> NodeMeta.EffectMeta()

            is eu.karcags.graph.Node.CauseNode -> NodeMeta.CauseMeta()

            is eu.karcags.graph.Node.ActionNode.AndNode -> NodeMeta.ActionMeta(Action.AND)

            is eu.karcags.graph.Node.ActionNode.OrNode -> NodeMeta.ActionMeta(Action.OR)

            else -> throw IllegalArgumentException()
        }

        return Node(node.id, node.displayName, meta)
    }
}