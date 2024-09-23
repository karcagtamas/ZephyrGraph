package eu.karcags.ceg.graph.converters.logical.definitions

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
}