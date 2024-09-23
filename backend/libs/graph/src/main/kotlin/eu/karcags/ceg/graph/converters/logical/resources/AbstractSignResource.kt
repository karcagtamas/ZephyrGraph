package eu.karcags.ceg.graph.converters.logical.resources

abstract class AbstractSignResource {

    fun get(sign: Sign, vararg args: String): String {
        return args.foldIndexed(sign(sign)) { idx, acc, s -> acc.replace("{$idx}", s) }
    }

    protected abstract fun sign(value: Sign): String
}