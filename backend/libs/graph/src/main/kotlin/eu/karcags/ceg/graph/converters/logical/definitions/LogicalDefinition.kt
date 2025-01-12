package eu.karcags.ceg.graph.converters.logical.definitions

import eu.karcags.ceg.graph.converters.logical.resources.AbstractSignResource
import eu.karcags.ceg.graphmodel.expressions.LogicalExpression
import kotlinx.serialization.Serializable

/**
 * Interface of logical definitions.
 */
@Serializable
sealed interface LogicalDefinition {

    /**
     * Evaluates the logical definition by the given context.
     * @param ctx the context for the logical definition evaluation
     * @return logical result of the definition
     */
    fun eval(ctx: Map<LogicalDefinition, Boolean>): Boolean

    /**
     * Gets value from the [ctx] context by the [key] or gives back an else value from the [provider].
     * @param ctx the context of the logical definition evaluation
     * @param key the key of the searched value
     * @param provider the else value provider
     * @return the context value
     */
    fun getOrElse(ctx: Map<LogicalDefinition, Boolean>, key: LogicalDefinition, provider: () -> Boolean): Boolean {
        if (ctx.containsKey(key)) {
            return ctx.getValue(key)
        }

        return provider()
    }

    /**
     * Stringifies the logical definition by the given sign [resource].
     * @param resource the sign resource
     * @return the [String] representation of the definition.
     */
    fun stringify(resource: AbstractSignResource): String

    /**
     * Determines that the current definition is simple or not.
     * @return true if the definition is simple
     */
    fun isSimple(): Boolean

    /**
     * Gets all the wrapped logical expressions.
     * @return the found logical expression
     */
    fun expressions(): List<LogicalExpression>
}