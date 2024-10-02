package eu.karcags.ceg.graph.decisiontable

import eu.karcags.ceg.graph.converters.logical.LogicalGraph
import eu.karcags.ceg.graph.converters.logical.definitions.AndDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NotDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.OrDefinition
import kotlinx.serialization.Serializable

@Serializable
class DecisionTable {
    companion object {
        fun from(graph: LogicalGraph): DecisionTable {
            val decisionTable = DecisionTable(graph)

            // Rules are the column
            // Rows are the causes and the effects

            return decisionTable
        }
    }

    private val graph: LogicalGraph
    val rows = mutableListOf<TableRow>()
    val columns = mutableListOf<String>()

    constructor(graph: LogicalGraph) {
        this.graph = graph

        // Add cause nodes as rows
        graph.getCauseNodes().forEach {
            rows.add(TableRow(it))
        }

        // Add effect nodes as rows
        graph.getEffectNodes().forEach {
            rows.add(TableRow(it, true))
        }

        graph.definitions.forEachIndexed { ridx, (effect, cause) ->
            val ruleNumber = ridx + 1
            if (cause is NodeDefinition) {
                handleAndCauses(AndDefinition(setOf(cause)), effect, ruleNumber)
            } else if (cause is NotDefinition) {
                handleAndCauses(AndDefinition(setOf(cause)), effect, ruleNumber)
            } else if (cause is AndDefinition) {
                handleAndCauses(cause, effect, ruleNumber)
            } else if (cause is OrDefinition) {
                cause.definitions
                    .filter { it is AndDefinition }
                    .forEachIndexed { idx, it ->
                        if (it is AndDefinition) {
                            handleAndCauses(it, effect, ruleNumber, idx + 1)
                        }
                    }
            }
        }
    }

    private fun handleAndCauses(definition: AndDefinition, effect: NodeDefinition, ruleNumber: Int, number: Int = 1) {
        val nodes = definition.definitions
            .filter { it is NodeDefinition }
        val nots = definition.definitions
            .filter { it is NotDefinition && it.inner is NodeDefinition }

        if (nodes.isEmpty() && nots.isEmpty()) {
            return
        }

        rows.forEach { row ->
            if (row.node == effect) {
                row.addItem(TableItem.True)
            } else if (row.node in nodes) {
                row.addItem(TableItem.True)
            } else if (row.node in nots) {
                row.addItem(TableItem.False)
            } else {
                row.addItem(TableItem.NotUsed)
            }
        }
        addColumn(ruleNumber, number)
    }

    private fun addColumn(ruleNumber: Int, subRuleNumber: Int) {
        columns.add("R$ruleNumber-$subRuleNumber")
    }
}