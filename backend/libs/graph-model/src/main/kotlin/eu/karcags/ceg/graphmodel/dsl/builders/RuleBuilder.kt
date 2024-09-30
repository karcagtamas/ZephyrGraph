package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.Rule
import eu.karcags.ceg.graphmodel.dsl.markers.GraphDsl
import eu.karcags.ceg.graphmodel.dsl.validators.RuleCauseValidator
import eu.karcags.ceg.graphmodel.dsl.validators.RuleEffectValidator

@GraphDsl
class RuleBuilder(val id: Int, val graphNodes: Set<Node.Cause>) : AbstractBuilder<Rule>() {
    var cause: Node? = null
    var effect: Node.Effect? = null

    override fun build() = Rule(id, cause!!, effect!!)

    override fun validate(): Boolean {
        return RuleEffectValidator().validate(effect) && RuleCauseValidator().validate(cause)
    }
}