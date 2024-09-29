package eu.karcags.ceg.graphmodel.dsl.validators

import eu.karcags.ceg.graphmodel.Node

class RuleCauseValidator : AbstractValidator<Node>(Node::class.java) {

    override fun validate(data: Node?): Boolean {
        required(data)

        return true
    }
}