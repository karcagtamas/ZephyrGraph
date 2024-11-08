package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.Rule
import eu.karcags.ceg.graphmodel.dsl.markers.GraphDsl
import eu.karcags.ceg.graphmodel.dsl.validators.RuleCauseValidator
import eu.karcags.ceg.graphmodel.dsl.validators.RuleEffectValidator
import eu.karcags.ceg.graphmodel.expressions.Variable

@GraphDsl
class RuleBuilder(val id: Int, val nodeProvider: ValueProvider<String, Node.Cause>, val variableProvider: ValueProvider<String, Variable<*>>) : AbstractBuilder<Rule>() {
    var cause: Node? = null
    var effect: Node.Effect? = null

    override fun build() = Rule(id, cause!!, effect!!)

    override fun validate(): Boolean {
        return RuleEffectValidator().validate(effect, args()) && RuleCauseValidator().validate(cause, args())
    }

    override fun args(): Map<String, Any> {
        return mapOf(Pair("ruleId", id))
    }
}