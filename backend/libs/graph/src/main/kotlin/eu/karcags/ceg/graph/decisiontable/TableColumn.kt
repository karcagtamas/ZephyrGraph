package eu.karcags.ceg.graph.decisiontable

import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition
import java.util.UUID

/**
 * Represents a column of the decision table.
 * @property ruleNumber the number of the rule
 * @property subRuleNumber the number of the sub-rule
 * @property effect the connected effect
 * @property id the unique id of the column
 * @property name the constructed name of the column (from [ruleNumber] and [subRuleNumber])
 * @property items the items of the column
 * @param ruleNumber the number of the rule
 * @param subRuleNumber the number of the sub-rule
 * @param effect the connected effect
 */
class TableColumn(val ruleNumber: Int, val subRuleNumber: Int, val effect: NodeDefinition) {

    /**
     * Creates a table column with initialized items
     * @param ruleNumber the number of the rule
     * @param subRuleNumber the number of the sub-rule
     * @param effect the connected effect
     * @param items the items of the column
     */
    constructor(ruleNumber: Int, subRuleNumber: Int, effect: NodeDefinition, items: List<TableItem>) : this(
        ruleNumber,
        subRuleNumber,
        effect
    ) {
        this.items.addAll(items)
    }

    val id: String = UUID.randomUUID().toString()
    val name: String = "R$ruleNumber-$subRuleNumber"

    val items = mutableListOf<TableItem>()

    /**
     * Adds a new item to the column.
     * @param node the node of the column item
     * @param value the optional value of the new column item
     * @param isEffect the connected [node] is effect or not
     */
    fun addItem(node: NodeDefinition, value: TableItemValue?, isEffect: Boolean = false) {
        items.add(TableItem(node, value, isEffect))
    }

    /**
     * Gets all the set items.
     * @return all items from the column where the value is set
     */
    fun getSetItems(): List<TableItem> {
        return getSetItems { true }
    }

    /**
     * Gets all the set items what mets the given conditions
     * @param predicate the predicate for the filter
     * @return all items from the column where the value is set and mets the given condition ([predicate])
     */
    fun getSetItems(predicate: (TableItem) -> Boolean): List<TableItem> {
        return items.filter { it.value != null && it.value.isSet() }.filter { predicate(it) }
    }

    /**
     * Gets table item by the connected node
     * @param node the node
     * @return the found table item
     */
    fun byNode(node: NodeDefinition): TableItem {
        return items.first { it.node == node }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (javaClass != other?.javaClass) return false

        return id == (other as TableColumn).id
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + effect.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + items.hashCode()
        return result
    }
}