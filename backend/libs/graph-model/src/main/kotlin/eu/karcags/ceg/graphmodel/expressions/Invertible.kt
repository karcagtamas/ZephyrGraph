package eu.karcags.ceg.graphmodel.expressions

/**
 * Interface of invertible objects.
 * @param T the type of the invertible value
 */
interface Invertible<T> {

    /**
     * Inverts the current object by the defined logic.
     * @return the inverted value
     */
    fun invert(): T
}