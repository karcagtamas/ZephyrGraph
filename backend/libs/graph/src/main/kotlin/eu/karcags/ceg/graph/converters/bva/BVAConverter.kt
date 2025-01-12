package eu.karcags.ceg.graph.converters.bva

import eu.karcags.ceg.generator.BVA
import eu.karcags.ceg.graph.converters.AbstractConverter
import eu.karcags.ceg.graph.converters.logical.LogicalGraph

/**
 * BVA converter. Converts a [LogicalGraph] into list of [BVA.FinalResult].
 * @constructor creates a BVA converter
 */
class BVAConverter : AbstractConverter<LogicalGraph, List<BVA.FinalResult>>() {

    override fun convert(entity: LogicalGraph): List<BVA.FinalResult> {
        val bva = BVA()

        return entity.definitions.map { def -> bva.construct(def.cause.expressions()) }
    }
}