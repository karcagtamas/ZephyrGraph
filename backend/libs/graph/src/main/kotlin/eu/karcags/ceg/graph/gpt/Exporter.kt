package eu.karcags.ceg.graph.gpt

import eu.karcags.ceg.graph.decisiontable.DecisionTable
import eu.karcags.ceg.graph.decisiontable.TableColumn
import eu.karcags.ceg.graph.decisiontable.TableItemValue
import eu.karcags.ceg.graphmodel.expressions.*

class Exporter(private val decisionTable: DecisionTable) {

    fun export(): String {

        val variables = collectVariables().map { GPTVariable(it) }

        val rows = decisionTable.columns.map { col ->
            variables.joinToString(";") { variable -> getRowDefinition(col, variable) }
        }

        return (listOf(variables.joinToString(";") { it.toString() }) + rows).joinToString("\n")
    }

    private fun collectVariables(): Set<Variable<*>> {
        return decisionTable.graph().definitions.map {
            it.cause.expressions().mapNotNull { expression -> expression.getVariable() }
        }.flatten().toSet()
    }

    private fun getRowDefinition(column: TableColumn, variable: GPTVariable): String {
        val def = column.items
            .asSequence()
            .filter { !it.isEffect }
            .filter { it.value != null && it.value != TableItemValue.NotUsed }
            .map { it.node.expressions() }
            .flatten()
            .filter { it.left is Variable<*> }
            .filter { it.left == variable.variable }
            .firstOrNull()

        if (def == null) {
            return "*"
        }

        return stringifyLogicalExpression(def)
    }

    private fun stringifyLogicalExpression(definition: LogicalExpression): String {
        val literal = stringifyLogicalExpressionValue(definition.right)
        return when (definition.operator) {
            is Operator.IsTrue -> "true"
            is Operator.IsFalse -> "false"
            is Operator.Equal -> "="
            is Operator.InInterval -> literal
            is Operator.NotInInterval -> literal
            else -> "${definition.operator}$literal"
        }
    }

    private fun stringifyLogicalExpressionValue(literal: Operand): String {
        if (literal is Literal<*>) {
            return literal.value.toString()
        }

        if (literal is ClosedRangeLiteral<*>) {
            return "[${literal.stringifyRange(",")}]"
        }

        if (literal is OpenEndRangeLiteral<*>) {
            return "[${literal.stringifyRange(",")})"
        }

        return ""
    }
}