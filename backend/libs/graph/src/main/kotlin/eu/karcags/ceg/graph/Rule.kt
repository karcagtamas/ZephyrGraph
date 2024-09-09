package eu.karcags.ceg.graph

class Rule(val source: Node, val target: Node.EffectNode) {
    var id: String = "${source.id}-${target.id}"
}