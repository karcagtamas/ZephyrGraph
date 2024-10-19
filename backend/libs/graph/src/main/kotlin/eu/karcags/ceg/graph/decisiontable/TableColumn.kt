package eu.karcags.ceg.graph.decisiontable

import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition
import java.util.UUID

class TableColumn(val ruleNumber: Int, val subRuleNumber: Int, val effect: NodeDefinition) {

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

    fun addItem(node: NodeDefinition, value: TableItemValue?, isEffect: Boolean = false) {
        items.add(TableItem(node, value, isEffect))
    }

    fun getSetItems(): List<TableItem> {
        return getSetItems { true }
    }

    fun getSetItems(predicate: (TableItem) -> Boolean): List<TableItem> {
        return items.filter { it.value != null && it.value.isSet() }.filter { predicate(it) }
    }

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