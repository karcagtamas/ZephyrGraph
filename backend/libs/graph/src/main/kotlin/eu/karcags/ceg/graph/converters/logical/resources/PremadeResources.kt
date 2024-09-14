package eu.karcags.ceg.graph.converters.logical.resources

enum class PremadeResources(private val resource: AbstractSignResource) {
    DEFAULT(DefaultSignResource()),
    TEXT(TextSignResource());

    fun getNot() = resource.NOT

    fun getImplicate() = resource.IMPLICATE

    fun getOr() = resource.OR

    fun getAnd() = resource.AND

    fun getResource() = resource
}