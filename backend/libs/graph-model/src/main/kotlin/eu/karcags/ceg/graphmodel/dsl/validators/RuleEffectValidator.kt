package eu.karcags.ceg.graphmodel.dsl.validators

import eu.karcags.ceg.graphmodel.Node

class RuleEffectValidator : AbstractValidator<Node.Effect>(Node.Effect::class.java) {

    override fun validate(data: Node.Effect?): Boolean {
        required(data)

        return true
    }
}