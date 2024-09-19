package eu.karcags.ceg.graphmodel.dsl.validators

import eu.karcags.ceg.graphmodel.exceptions.GraphException

abstract class AbstractValidator<T>(private val clazz: Class<T>) {

    abstract fun validate(data: T?): Boolean

    protected fun required(data: T?): T {

        if (data == null) {
            throw GraphException.ValidateException("${clazz.simpleName} entry is required")
        }

        return data
    }
}