package eu.karcags.ceg.graph.converters.bva

import eu.karcags.ceg.generator.BVA
import eu.karcags.ceg.generator.DefinitionType
import eu.karcags.ceg.generator.TestType
import eu.karcags.ceg.graph.converters.AbstractConverter
import eu.karcags.ceg.graph.converters.logical.LogicalGraph
import eu.karcags.ceg.graph.converters.logical.definitions.*
import eu.karcags.ceg.graphmodel.expressions.Operator

class BVAConverter : AbstractConverter<LogicalGraph, List<List<List<TestType>>>>() {

    override fun convert(entity: LogicalGraph): List<List<List<TestType>>> {
        val bva = BVA()

        return entity.definitions.map { def -> bva.construct(def.cause.expressions().map { BVA.Item(getDefinitionType(it)) }) }
    }

    private fun getDefinitionType(expression: NodeExpression): DefinitionType {
        return when (expression.operator) {
            is Operator.Equal -> DefinitionType.Equal
            is Operator.NotEqual -> DefinitionType.NotEqual
            is Operator.LessThan -> DefinitionType.LessThan
            is Operator.LessThanOrEqual -> DefinitionType.LessThanOrEqualTo
            is Operator.GreaterThan -> DefinitionType.GreaterThan
            is Operator.GreaterThanOrEqual -> DefinitionType.GreaterThanOrEqualTo
            else -> throw IllegalArgumentException("Unknown operator ${expression.operator}")
        }
    }
}