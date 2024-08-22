package eu.karcags.ceg.graph.dsl.builders

import eu.karcags.ceg.common.exceptions.GraphException

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
}