package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.exceptions.GraphException

abstract class AbstractBuilder<T> {

    protected abstract fun build(): T

    open fun validate(): Boolean {
        return true
    }

    open fun validateAndBuild(): T {
        if (!validate()) {
            throw GraphException.ValidateException("Invalid state. Build cannot be performed")
        }

        return build()
    }

    protected open fun args(): Map<String, Any> {
        return emptyMap()
    }
}