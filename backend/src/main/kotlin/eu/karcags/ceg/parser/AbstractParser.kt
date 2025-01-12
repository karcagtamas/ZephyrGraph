package eu.karcags.ceg.parser

/**
 * Abstract parse.
 * @param T the type of the parse result
 */
abstract class AbstractParser<T> {

    /**
     * Parses the content into a specified result.
     * @param content content
     * @return the parsed result
     */
    abstract fun parse(content: String): T

    /**
     * Prepares the content for parsing.
     * @param original original content
     * @return the prepared content
     */
    protected open fun prepare(original: String): String {
        return original
    }

    /**
     * Validates the content before parsing
     * @param content the checked content
     * @return true if the validation is success
     */
    protected open fun validate(content: String): Boolean {
        return true
    }
}