package eu.karcags.ceg.graph.converters.logical.refiners

import eu.karcags.ceg.graph.converters.logical.definitions.AndDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.LogicalDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NotDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.OrDefinition
import kotlin.math.pow

class DNF() : AbstractRefiner("dnf") {

    override fun refine(definition: LogicalDefinition): LogicalDefinition {
        val nodes = collectNodes(definition).toList()

        val perm = List(2.0.pow(nodes.size).toInt()) { it }
            .map { it.toString(2) }
            .map { it.padStart(nodes.size, '0') }
            .map { it.split("").filter { it.isNotEmpty() }.map { it.toInt() } }
            .map {
                nodes.mapIndexed { idx, node ->
                    Pair(node, it[idx] != 0)
                }
            }
            .map {
                it.fold(emptyMap<LogicalDefinition, Boolean>()) { m, pair -> m.plus(pair) }
            }
            .filter { definition.eval(it) }

        if (perm.isEmpty()) {
            return definition
        }

        val orItems = perm.map {
            val andItems = it.toList()
                .map { if (it.second) it.first else NotDefinition(it.first) }
                .toSet()

            if (andItems.size == 1) {
                return andItems.first()
            }

            AndDefinition(andItems)
        }.toSet()

        if (orItems.size == 1) {
            return orItems.first()
        }

        return OrDefinition(orItems)
    }
}