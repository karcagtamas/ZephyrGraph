package eu.karcags.graph

class Rule(val source: Node, val target: Node.EffectNode) {
    var id: String = "${source.id}-${target.id}"
}