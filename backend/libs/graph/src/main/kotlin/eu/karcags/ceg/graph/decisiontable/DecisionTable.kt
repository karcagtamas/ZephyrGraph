package eu.karcags.ceg.graph.decisiontable

import eu.karcags.ceg.graph.converters.logical.LogicalGraph
import eu.karcags.ceg.graph.converters.logical.definitions.AndDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NotDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.OrDefinition
import kotlinx.serialization.Serializable

class DecisionTable {
    companion object {
        fun from(graph: LogicalGraph): DecisionTable {
            return DecisionTable(graph)
        }

        fun from(decisionTable: DecisionTable, columns: List<TableColumn>): DecisionTable {
            return DecisionTable(decisionTable, columns)
        }
    }

    private val graph: LogicalGraph
    val columns = mutableListOf<TableColumn>()

    constructor(graph: LogicalGraph) {
        this.graph = graph

        graph.definitions.forEachIndexed { defIndex, (effect, cause) ->
            val ruleNumber = defIndex + 1

            if (cause is NodeDefinition) {
                handleAndCauses(AndDefinition(setOf(cause)), effect, ruleNumber)
            } else if (cause is NotDefinition) {
                handleAndCauses(AndDefinition(setOf(cause)), effect, ruleNumber)
            } else if (cause is AndDefinition) {
                handleAndCauses(cause, effect, ruleNumber)
            } else if (cause is OrDefinition) {
                cause.definitions
                    .filter { it !is OrDefinition }
                    .forEachIndexed { idx, it ->
                        if (it is AndDefinition) {
                            handleAndCauses(it, effect, ruleNumber, idx + 1)
                        } else if (it is NotDefinition || it is NodeDefinition) {
                            handleAndCauses(AndDefinition(setOf(it)), effect, ruleNumber, idx + 1)
                        }
                    }
            }
        }
    }

    constructor(decisionTable: DecisionTable, columns: List<TableColumn>) {
        this.graph = decisionTable.graph

        this.columns.addAll(columns)
    }

    fun export(): Export {
        val rows = (graph.getCauseNodes() + graph.getEffectNodes()).map { rowNode ->
            ExportRow(rowNode.displayName, columns.map { column ->
                column.items.first { it.node == rowNode }.value
            })
        }

        return Export(columns.map { it.name }, rows)
    }

    fun optimize(): DecisionTable {
        val effects = graph.getEffectNodes()

        val result = effects.map { effect ->
            val effectCols = columns.filter { it.effect == effect }

            if (effectCols.size > 1) {
                for (i in effectCols.indices) {
                    for (j in effectCols.size - 1 downTo 1) {
                        val result = collapse(effectCols[i], effectCols[j])

                        if (result != null) {
                            return@map Pair(
                                true,
                                (effectCols
                                    .filterIndexed { idx, it -> idx != i && idx != j } + result)
                                    .mapIndexed { idx, it ->
                                        TableColumn(it.ruleNumber, idx + 1, it.effect, it.items)
                                    }.sortedBy { it.subRuleNumber }
                            )
                        }
                    }
                }
            }

            Pair(false, effectCols)
        }

        if (result.any { it.first }) {
            return from(this, result.map { it.second }.flatten()).optimize()
        }

        return this
    }

    fun graph(): LogicalGraph {
        return graph
    }

    private fun handleAndCauses(definition: AndDefinition, effect: NodeDefinition, ruleNumber: Int, number: Int = 1) {
        val nodes = definition.definitions
            .filterIsInstance<NodeDefinition>()
        val nots = definition.definitions
            .filter { it is NotDefinition && it.inner is NodeDefinition }
            .map { (it as NotDefinition).inner }

        val column = TableColumn(ruleNumber, number, effect)

        graph.getCauseNodes().forEach { cause ->
            val value = when (cause) {
                in nodes -> TableItemValue.True
                in nots -> TableItemValue.False
                else -> TableItemValue.NotUsed
            }

            column.addItem(cause, value)
        }

        graph.getEffectNodes().forEach { eff ->
            column.addItem(eff, if (eff == effect) TableItemValue.True else null, true)
        }

        columns.add(column)
    }

    private fun collapse(a: TableColumn, b: TableColumn): TableColumn? {
        val nodes =
            a.getSetItems { !it.isEffect }.map { it.node }.toSet() + b.getSetItems { !it.isEffect }.map { it.node }
                .toSet()

        for (selected in nodes) {
            val same = nodes.filter { it != selected }
                .all { a.byNode(it).value == b.byNode(it).value }
            val diff = TableItemValue.opposites(a.byNode(selected).value, b.byNode(selected).value)

            if (same && diff) {
                val column = TableColumn(a.ruleNumber, b.subRuleNumber, a.effect)

                a.items.forEach {
                    if (it.node == selected) {
                        column.addItem(selected, TableItemValue.NotUsed)
                    } else {
                        column.addItem(it.node, it.value, it.isEffect)
                    }
                }

                return column
            }
        }

        return null
    }

    @Serializable
    data class Export(val columns: List<String>, val rows: List<ExportRow>)

    @Serializable
    data class ExportRow(val displayName: String, val items: List<TableItemValue?>)
}