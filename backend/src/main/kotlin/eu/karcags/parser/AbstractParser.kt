package eu.karcags.parser

abstract class AbstractParser<T> {

    abstract fun parse(content: String): T

    protected open fun prepare(original: String): String {
        return original
    }

    protected open fun validate(content: String): Boolean {
        return true
    }
}