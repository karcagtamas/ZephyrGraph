package eu.karcags.ceg.graph.converters.logical.refiners

import eu.karcags.ceg.graph.converters.logical.definitions.*
import eu.karcags.ceg.graph.exceptions.GraphConvertException

class Optimizer : AbstractRefiner("optimizer") {

    override fun refine(definition: LogicalDefinition): LogicalDefinition {
        val optimized = optimize(definition)

        if (optimized == definition) {
            return definition
        }

        return refine(optimized)
    }

    private fun optimize(definition: LogicalDefinition): LogicalDefinition {
        return when (definition) {
            is OrDefinition -> optimizeOr(definition)
            else -> definition
        }
    }

    private fun optimizeOr(orDefinition: OrDefinition): LogicalDefinition {
        val ands = orDefinition.definitions.filterIsInstance<AndDefinition>()
        val nodes = findNodes(ands.first())

        nodes.forEach { selectedNode ->
            ands.forEachIndexed { index, andDef ->
                ands.filterIndexed { i, _ -> i > index }.forEach { otherAndDef ->
                    if (compare(andDef, otherAndDef, selectedNode)) {
                        return OrDefinition(ands.filter { it != andDef && it != otherAndDef }.toSet() + AndDefinition(andDef.definitions.filter { it != selectedNode && it != selectedNode.not() }.toSet()))
                    }
                }
            }
        }

        return orDefinition
    }

    private fun findNodes(andDefinition: AndDefinition): Set<NodeDefinition> {
        return andDefinition.definitions.map {
            when (it) {
                is NodeDefinition -> it
                is NotDefinition -> when (it.inner) {
                    is NodeDefinition -> it.inner
                    else -> throw GraphConvertException("Invalid pre-state of optimizer")
                }

                else -> throw GraphConvertException("Invalid pre-state of optimizer")
            }
        }.toSet()
    }

    private fun compare(definition: AndDefinition, other: AndDefinition, node: NodeDefinition): Boolean {
        val predicate: (LogicalDefinition) -> Boolean = { def ->
            def != node && def != node.not()
        }
        val same1 = definition.definitions
            .filter { predicate(it) }
            .all { it in other.definitions }
        val same2 = other.definitions
            .filter { predicate(it) }
            .all { it in definition.definitions }

        return same1 && same2
    }
}