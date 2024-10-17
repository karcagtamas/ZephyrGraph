package eu.karcags.ceg.graphmodel.dsl.validators

import eu.karcags.ceg.graphmodel.exceptions.GraphException

abstract class AbstractValidator<T>(private val clazz: Class<T>) {

    abstract fun validate(data: T?, args: Map<String, Any>): Boolean

    fun validate(data: T?): Boolean {
        return validate(data, emptyMap())
    }

    protected fun required(data: T?): T {
        return required(data, "${clazz.simpleName} clause is required")
    }

    protected fun required(data: T?, message: String): T {
        if (data == null) {
            throw GraphException.ValidateException(message)
        }

        return data
    }
}