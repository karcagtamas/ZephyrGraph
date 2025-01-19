package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.exceptions.GraphException

/**
 * Abstract builder design pattern.
 * @constructor creates a builder instance
 * @param T the type of the build target
 */
abstract class AbstractBuilder<T> {

    /**
     * Finalizes the build process and constructs the target.
     * @return the constructed result of the build
     */
    protected abstract fun build(): T

    /**
     * Validates the current state of the builder.
     * @return True if the state is ready to build
     */
    open fun validate(): Boolean {
        return true
    }

    /**
     * Validates the current state and builds the target. See [build] and [validate].
     * @return the constructed result of the build
     * @throws GraphException.ValidateException if the validation return with False
     */
    open fun validateAndBuild(): T {
        if (!validate()) {
            throw GraphException.ValidateException("Invalid state. Build cannot be performed")
        }

        return build()
    }

    /**
     * The arguments of the build process.
     * @return map keys and values
     */
    protected open fun args(): Map<String, Any> {
        return emptyMap()
    }
}