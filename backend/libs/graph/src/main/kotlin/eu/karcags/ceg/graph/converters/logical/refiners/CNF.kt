package eu.karcags.ceg.graph.converters.logical.refiners

import eu.karcags.ceg.graph.converters.logical.definitions.AndDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.LogicalDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NotDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.OrDefinition
import kotlin.math.pow

/**
 * CNF refiner. It converts the logical graph into Conjuctive Normal Form.
 * The structure of the logical definitions will be this: ((A OR B) AND (C OR D))
 */
class CNF : AbstractRefiner("cnf") {

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
            .filter { !definition.eval(it) }

        if (perm.isEmpty()) {
            return definition
        }

        val andItems = perm.map {
            val orItems = it.toList()
                .map { if (it.second) NotDefinition(it.first) else it.first }
                .toSet()

            if (orItems.size == 1) {
                return orItems.first()
            }

            OrDefinition(orItems)
        }.toSet()

        if (andItems.size == 1) {
            return andItems.first()
        }

        return AndDefinition(andItems)
    }
}