package eu.karcags.ceg.graph.converters.logical

import kotlinx.serialization.Serializable

@Serializable
class LogicalGraph(val definitions: List<LogicalDefinition>) {

    override fun toString(): String {
        return definitions.joinToString("\n")
    }
}

@Serializable
open class LogicalDefinition

@Serializable
class NodeDefinition(val id: String, val displayName: String) : LogicalDefinition() {

    override fun toString(): String {
        return "$displayName ($id)"
    }
}

@Serializable
open class UnaryLogicalDefinition(val definition: LogicalDefinition, val sign: String) : LogicalDefinition() {

    class Not(definition: LogicalDefinition, sign: String) : UnaryLogicalDefinition(definition, sign) {
        override fun toString(): String {
            return "$sign ($definition)"
        }
    }
}

@Serializable
open class BinaryLogicalDefinition(val left: LogicalDefinition, val right: LogicalDefinition, val sign: String) : LogicalDefinition() {

    class And(left: LogicalDefinition, right: LogicalDefinition, sign: String) : BinaryLogicalDefinition(left, right, sign)

    class Or(left: LogicalDefinition, right: LogicalDefinition, sign: String) : BinaryLogicalDefinition(left, right, sign)

    class Implicate(left: LogicalDefinition, right: LogicalDefinition, sign: String) : BinaryLogicalDefinition(left, right, sign)

    override fun toString(): String {
        return "($left) $sign ($right)"
    }
}
