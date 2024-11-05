package eu.karcags.ceg.graph.converters.logical.definitions

import eu.karcags.ceg.graph.converters.logical.resources.AbstractSignResource
import eu.karcags.ceg.graphmodel.expressions.LogicalExpression
import kotlinx.serialization.Serializable

@Serializable
sealed interface LogicalDefinition {

    fun eval(ctx: Map<LogicalDefinition, Boolean>): Boolean

    fun getOrElse(ctx: Map<LogicalDefinition, Boolean>, key: LogicalDefinition, provider: () -> Boolean): Boolean {
        if (ctx.containsKey(key)) {
            return ctx.getValue(key)
        }

        return provider()
    }

    fun stringify(resource: AbstractSignResource): String

    fun isSimple(): Boolean

    fun expressions(): List<LogicalExpression>
}