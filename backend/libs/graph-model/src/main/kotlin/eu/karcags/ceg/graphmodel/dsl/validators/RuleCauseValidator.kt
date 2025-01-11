package eu.karcags.ceg.graphmodel.dsl.validators

import eu.karcags.ceg.graphmodel.Node

/**
 * Rule cause validator.
 */
class RuleCauseValidator : AbstractValidator<Node>(Node::class.java) {

    override fun validate(
        data: Node?,
        args: Map<String, Any>
    ): Boolean {
        val ruleId = args["ruleId"] as Int

        required(data, "Cause definition (cause, causeById, and, or, not) is required in the rule (R$ruleId) clause")

        return true
    }
}