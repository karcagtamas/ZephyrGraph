package eu.karcags.graph.validators

import eu.karcags.common.exceptions.GraphException

abstract class AbstractValidator<T>(val clazz: Class<T>) {

    abstract fun validate(data: T?): Boolean

    protected fun required(data: T?): T {

        if (data == null) {
            throw GraphException.ValidateException("${clazz.simpleName} entry is required")
        }

        return data
    }
}