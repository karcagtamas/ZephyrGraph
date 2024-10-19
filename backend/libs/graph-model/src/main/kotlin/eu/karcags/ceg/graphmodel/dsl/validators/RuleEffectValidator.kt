package eu.karcags.ceg.graphmodel.dsl.validators

import eu.karcags.ceg.graphmodel.Node

class RuleEffectValidator : AbstractValidator<Node.Effect>(Node.Effect::class.java) {

    override fun validate(
        data: Node.Effect?,
        args: Map<String, Any>
    ): Boolean {
        val ruleId = args["ruleId"] as Int

        required(data, "Effect clause is required in the rule (R$ruleId) clause")

        return true
    }
}