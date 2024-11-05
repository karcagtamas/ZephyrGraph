package eu.karcags.ceg.graph.converters

abstract class AbstractConverter<T, U> {

    abstract fun convert(entity: T): U
}