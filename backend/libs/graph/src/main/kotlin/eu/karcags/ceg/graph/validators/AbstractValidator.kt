package eu.karcags.ceg.graph.validators

import eu.karcags.ceg.common.exceptions.GraphException

abstract class AbstractValidator<T>(private val clazz: Class<T>) {

    abstract fun validate(data: T?): Boolean

    protected fun required(data: T?): T {

        if (data == null) {
            throw GraphException.ValidateException("${clazz.simpleName} entry is required")
        }

        return data
    }
}