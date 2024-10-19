package eu.karcags.ceg.graph.converters.logical.resources

abstract class AbstractSignResource {

    fun get(sign: Sign, vararg args: String): String {
        return args.foldIndexed(sign(sign, args.size)) { idx, acc, s -> acc.replace("{$idx}", s) }
    }

    protected abstract fun sign(value: Sign, dynamicItems: Int): String

    protected fun sign(value: Sign): String {
        return sign(value, 0)
    }

    protected fun constructDynamicSign(items: Int, signText: String): String {
        return List(items) { "{$it}" }.joinToString(signText)
    }
}