package eu.karcags.graph.dsl.builders

abstract class AbstractBuilder<T> {

    abstract fun build(): T
}