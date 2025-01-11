package eu.karcags.ceg.graphmodel.dsl.validators

import eu.karcags.ceg.graphmodel.exceptions.GraphException

/**
 * Abstract validator.
 * It validates the type [T] objects by the given definitions.
 * @property clazz the class of the inner (checked) type
 * @constructor creates a validator
 * @param clazz the class of the inner (checked) type
 * @param T the type of the checked object
 */
abstract class AbstractValidator<T>(private val clazz: Class<T>) {

    /**
     * Validates the [data] by the given definitions. It can use the [args] to determine run-time parameters.
     * @param data the checked data
     * @param args the additional arguments
     * @return True if the [data] is valid
     */
    abstract fun validate(data: T?, args: Map<String, Any>): Boolean

    /**
     * Validates the [data] by the given definitions.
     * @param data the checked data
     * @return True if the [data] is valid
     */
    fun validate(data: T?): Boolean {
        return validate(data, emptyMap())
    }

    /**
     * Checks that the given [data] is not null.
     * @param data the checked data
     * @throws GraphException.ValidateException graph validate exception
     * @return the not null object
     */
    protected fun required(data: T?): T {
        return required(data, "${clazz.simpleName} clause is required")
    }

    /**
     * Checks that the given [data] is not null.
     * @param data the checked data
     * @param message the exception message in case of error (null)
     * @throws GraphException.ValidateException graph validate exception with the given [message]
     * @return the not null object
     */
    protected fun required(data: T?, message: String): T {
        if (data == null) {
            throw GraphException.ValidateException(message)
        }

        return data
    }
}