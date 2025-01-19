package eu.karcags.ceg.graph.converters

/**
 * Abstract converter.
 * @param T source type
 * @param U destination type
 */
abstract class AbstractConverter<T, U> {

    /**
     * Coverts from [T] type to [U] type.
     * @param entity the source entity
     * @return the converted result
     */
    abstract fun convert(entity: T): U
}