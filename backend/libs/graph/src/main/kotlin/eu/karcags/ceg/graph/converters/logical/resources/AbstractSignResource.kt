package eu.karcags.ceg.graph.converters.logical.resources

/**
 * Abstract sign resources.
 */
abstract class AbstractSignResource {

    /**
     * Gets parsed sign parameterized with dynamic arguments.
     * @param sign the requested sign
     * @param args the input parameters
     * @return the parsed and modified sign
     */
    fun get(sign: Sign, vararg args: String): String {
        return args.foldIndexed(sign(sign, args.size)) { idx, acc, s -> acc.replace("{$idx}", s) }
    }

    /**
     * Gets sign parameterized with dynamic items.
     * @param value the requested sign
     * @param dynamicItems the number of the dynamic items
     * @return the requested resource sign
     */
    protected abstract fun sign(value: Sign, dynamicItems: Int): String

    /**
     * Get sign.
     * @param value the requested sign
     * @return the requested resource sign
     */
    protected fun sign(value: Sign): String {
        return sign(value, 0)
    }

    /**
     * Constructs dynamic expression concatenated with the given [signText].
     * @param items the number of the items
     * @param signText the textual sign
     * @return the assembled dynamic text
     */
    protected fun constructDynamicSign(items: Int, signText: String): String {
        return List(items) { "{$it}" }.joinToString(signText)
    }
}