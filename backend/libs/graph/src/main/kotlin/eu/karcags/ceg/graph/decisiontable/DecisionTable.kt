package eu.karcags.ceg.graph.decisiontable

import eu.karcags.ceg.graph.converters.logical.LogicalGraph
import eu.karcags.ceg.graph.converters.logical.definitions.AndDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NotDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.OrDefinition
import kotlinx.serialization.Serializable

/**
 * Represents a decision table. The cause and effects are the rows of the tables and all rule represents the columns.
 * A rule can be converted into one or more sub-rule (column).
 * @property graph the base graph of the table (source)
 * @property columns the created columns of the table
 */
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

    /**
     * Creates a decision table from the logical graph.
     * @param graph the source graph
     */
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

    /**
     * Creates a decision table from an existing table and columns.
     */
    constructor(decisionTable: DecisionTable, columns: List<TableColumn>) {
        this.graph = decisionTable.graph

        this.columns.addAll(columns)
    }

    /**
     * Exports the table content into a tabular representation.
     * @return a constructed object with the column names and the table rows
     */
    fun export(): Export {
        // convert the column value into set of rows (turn the table)
        val rows = (graph.getCauseNodes() + graph.getEffectNodes()).map { rowNode ->
            ExportRow(rowNode.displayName, columns.map { column ->
                column.items.first { it.node == rowNode }.value
            })
        }

        return Export(columns.map { it.name }, rows)
    }

    /**
     * Optimizes the table. It merges the overlapping rows.
     * @return the new optimized decision table
     */
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

    /**
     * Gets the decision table base graph
     * @return the logical graph of the table
     */
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

    /**
     * Export object of the table
     * @property columns the column names of the table
     * @property rows the row definitions of the table
     */
    @Serializable
    data class Export(val columns: List<String>, val rows: List<ExportRow>)

    /**
     * Export row of the table
     * @property displayName the display name of the row (first item)
     * @property items the actual values of the row (for each column)
     */
    @Serializable
    data class ExportRow(val displayName: String, val items: List<TableItemValue?>)
}