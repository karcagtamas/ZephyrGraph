package eu.karcags.ceg.graphmodel.dsl

import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.Rule
import eu.karcags.ceg.graphmodel.dsl.builders.*
import eu.karcags.ceg.graphmodel.dsl.markers.GraphDsl
import eu.karcags.ceg.graphmodel.expressions.*

/**
 * Defines the graph as the root element.
 * @param initializer the initializer graph builder for the graph construction
 * @return the constructed graph itself
 */
fun graph(initializer: (@GraphDsl GraphBuilder).() -> Unit): Graph {
    return GraphBuilder().apply { initializer() }.validateAndBuild()
}

/**
 * Defines a rule for the graph. The constructed rule is added to the build context of the graph.
 * @param initializer the initializer rule builder for the rule construction.
 * @return the constructed rule
 */
fun GraphBuilder.rule(initializer: RuleBuilder.() -> Unit): Rule {
    return RuleBuilder(nextRuleId(), nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { addRule(it) }
}

/**
 * Defines the variables for the graph. The constructed variables are added to the build context of the graph.
 * @param initializer the initializer variables builder for the variables construction.
 * @return the list of the defined variables
 */
fun GraphBuilder.variables(initializer: VariablesBuilder.() -> Unit): List<Variable<*>> {
    return VariablesBuilder().apply { initializer() }.validateAndBuild().onEach { variable -> addVariable(variable) }
}

/**
 * Defines an Int variable. The created variable is added to the build context of the variables builder.
 * @param name the name of the variable
 * @return the constructed variable
 */
fun VariablesBuilder.int(name: String): Variable<*> {
    return Variable(name, Int::class).also { add(it) }
}

/**
 * Defines a Boolean variable. The created variable is added to the build context of the variables builder.
 * @param name the name of the variable
 * @return the constructed variable
 */
fun VariablesBuilder.boolean(name: String): Variable<*> {
    return Variable(name, Boolean::class).also { add(it) }
}

/**
 * Defines a Float variable. The created variable is added to the build context of the variables builder.
 * @param name the name of the variable
 * @return the constructed variable
 */
fun VariablesBuilder.float(name: String, precision: Float = 0.1f): Variable<*> {
    return Variable(name, Float::class, precision).also { add(it) }
}

/**
 * Defines a cause node for the graph. The constructed node is added to the build context of the graph.
 * @param displayName the display name of the cause
 * @param initializer the initializer cause node builder for the graph construction.
 * @return the constructed cause node
 */
fun GraphBuilder.cause(displayName: String, initializer: CauseNodeBuilder.() -> LogicalExpression): Node.Cause {
    return CauseNodeBuilder(displayName, variableProvider).apply { expression = initializer() }.validateAndBuild().also { addNode(it) }
}

/**
 * Defines an effect node for the rule. The constructed node is added to the build context of the rule.
 * @param initializer the initializer effect node builder for the rule construction.
 * @return the constructed effect node
 */
fun RuleBuilder.effect(initializer: EffectNodeBuilder.() -> String): Node.Effect {
    return EffectNodeBuilder(id).apply { description = initializer() }.validateAndBuild().also { effect = it }
}

/**
 * Defines an AND node for the rule. The constructed node is added to the build context of the rule.
 * @param initializer the initializer AND node builder for the rule construction.
 * @return the constructed AND node
 */
fun RuleBuilder.and(initializer: AndNodeBuilder.() -> Unit): Node.BinaryAction.And {
    return AndNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { cause = it }
}

/**
 * Defines an OR node for the rule. The constructed node is added to the build context of the rule.
 * @param initializer the initializer OR node builder for the rule construction.
 * @return the constructed OR node
 */
fun RuleBuilder.or(initializer: OrNodeBuilder.() -> Unit): Node.BinaryAction.Or {
    return OrNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { cause = it }
}

/**
 * Defines an NOT node for the rule. The constructed node is added to the build context of the rule.
 * @param initializer the initializer NOT node builder for the rule construction.
 * @return the constructed NOT node
 */
fun RuleBuilder.not(initializer: NotNodeBuilder.() -> Unit): Node.UnaryAction.Not {
    return NotNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { cause = it }
}

/**
 * Defines a cause node for the rule. The constructed node is added to the build context of the rule.
 * @param displayName the display name of the cause
 * @param initializer the initializer cause node builder for the rule construction.
 * @return the constructed cause node
 */
fun RuleBuilder.cause(displayName: String, initializer: CauseNodeBuilder.() -> LogicalExpression): Node.Cause {
    return CauseNodeBuilder(displayName, variableProvider).apply { expression = initializer() }.validateAndBuild().also { cause = it }
}

/**
 * References a cause node from the globally defined cause node store (graph level). The found node is added to the build context of the rule.
 * @param displayName the display name of the cause
 * @return the found cause node
 */
fun RuleBuilder.causeById(displayName: String): Node.Cause {
    return nodeProvider.byKey(displayName)?.also { cause = it }!!
}

/**
 * Defines an AND node for the AND node. The constructed node is added to the build context of the AND node.
 * @param initializer the initializer AND node builder for the AND node construction.
 * @return the constructed AND node
 */
fun AndNodeBuilder.and(initializer: AndNodeBuilder.() -> Unit): Node.BinaryAction.And {
    return AndNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { addNode(it) }
}

/**
 * Defines an OR node for the AND node. The constructed node is added to the build context of the AND node.
 * @param initializer the initializer OR node builder for the AND node construction.
 * @return the constructed OR node
 */
fun AndNodeBuilder.or(initializer: OrNodeBuilder.() -> Unit): Node.BinaryAction.Or {
    return OrNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { addNode(it) }
}

/**
 * Defines an NOT node for the AND node. The constructed node is added to the build context of the AND node.
 * @param initializer the initializer NOT node builder for the AND node construction.
 * @return the constructed NOT node
 */
fun AndNodeBuilder.not(initializer: NotNodeBuilder.() -> Unit): Node.UnaryAction.Not {
    return NotNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { addNode(it) }
}

/**
 * Defines a cause node for the AND node. The constructed node is added to the build context of the AND node.
 * @param displayName the display name of the cause
 * @param initializer the initializer cause node builder for the AND node construction.
 * @return the constructed cause node
 */
fun AndNodeBuilder.cause(displayName: String, initializer: CauseNodeBuilder.() -> LogicalExpression): Node.Cause {
    return CauseNodeBuilder(displayName, variableProvider).apply { expression = initializer() }.validateAndBuild().also { addNode(it) }
}

/**
 * References a cause node from the globally defined cause node store (graph level). The found node is added to the build context of the AND node.
 * @param displayName the display name of the cause
 * @return the found cause node
 */
fun AndNodeBuilder.causeById(displayName: String): Node.Cause {
    return nodeProvider.byKey(displayName)?.also { addNode(it) }!!
}

/**
 * Defines an AND node for the OR node. The constructed node is added to the build context of the OR node.
 * @param initializer the initializer AND node builder for the OR node construction.
 * @return the constructed AND node
 */
fun OrNodeBuilder.and(initializer: AndNodeBuilder.() -> Unit): Node.BinaryAction.And {
    return AndNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { addNode(it) }
}

/**
 * Defines an OR node for the OR node. The constructed node is added to the build context of the OR node.
 * @param initializer the initializer OR node builder for the OR node construction.
 * @return the constructed OR node
 */
fun OrNodeBuilder.or(initializer: OrNodeBuilder.() -> Unit): Node.BinaryAction.Or {
    return OrNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { addNode(it) }
}

/**
 * Defines an NOT node for the OR node. The constructed node is added to the build context of the OR node.
 * @param initializer the initializer NOT node builder for the AND node construction.
 * @return the constructed OR node
 */
fun OrNodeBuilder.not(initializer: NotNodeBuilder.() -> Unit): Node.UnaryAction.Not {
    return NotNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { addNode(it) }
}

/**
 * Defines a cause node for the OR node. The constructed node is added to the build context of the OR node.
 * @param displayName the display name of the cause
 * @param initializer the initializer cause node builder for the OR node construction.
 * @return the constructed cause node
 */
fun OrNodeBuilder.cause(displayName: String, initializer: CauseNodeBuilder.() -> LogicalExpression): Node.Cause {
    return CauseNodeBuilder(displayName, variableProvider).apply { expression = initializer() }.validateAndBuild().also { addNode(it) }
}

/**
 * References a cause node from the globally defined cause node store (graph level). The found node is added to the build context of the OR node.
 * @param displayName the display name of the cause
 * @return the found cause node
 */
fun OrNodeBuilder.causeById(displayName: String): Node.Cause {
    return nodeProvider.byKey(displayName)?.also { addNode(it) }!!
}

/**
 * Defines an AND node for the NOT node. The constructed node is added to the build context of the NOT node.
 * @param initializer the initializer AND node builder for the NOT node construction.
 * @return the constructed AND node
 */
fun NotNodeBuilder.and(initializer: AndNodeBuilder.() -> Unit): Node.BinaryAction.And {
    return AndNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { node = it }
}

/**
 * Defines an OR node for the NOT node. The constructed node is added to the build context of the NOT node.
 * @param initializer the initializer OR node builder for the NOT node construction.
 * @return the constructed OR node
 */
fun NotNodeBuilder.or(initializer: OrNodeBuilder.() -> Unit): Node.BinaryAction.Or {
    return OrNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { node = it }
}

/**
 * Defines an NOT node for the NOT node. The constructed node is added to the build context of the NOT node.
 * @param initializer the initializer NOT node builder for the NOT node construction.
 * @return the constructed NOT node
 */
fun NotNodeBuilder.not(initializer: NotNodeBuilder.() -> Unit): Node.UnaryAction.Not {
    return NotNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { node = it }
}

/**
 * Defines a cause node for the NOT node. The constructed node is added to the build context of the NOT node.
 * @param displayName the display name of the cause
 * @param initializer the initializer cause node builder for the NOT node construction.
 * @return the constructed cause node
 */
fun NotNodeBuilder.cause(displayName: String, initializer: CauseNodeBuilder.() -> LogicalExpression): Node.Cause {
    return CauseNodeBuilder(displayName, variableProvider).apply { expression = initializer() }.validateAndBuild().also { node = it }
}

/**
 * References a cause node from the globally defined cause node store (graph level). The found node is added to the build context of the NOT node.
 * @param displayName the display name of the cause
 * @return the found cause node
 */
fun NotNodeBuilder.causeById(displayName: String): Node.Cause {
    return nodeProvider.byKey(displayName)?.also { node = it }!!
}

/**
 * Defines an expression for the cause node. The constructed expression is added to the build context of the cause node.
 * @param initializer the initializer expression builder for the cause node construction.
 * @return the constructed logical expression
 */
fun CauseNodeBuilder.expression(initializer: () -> LogicalExpression): LogicalExpression {
    return ExpressionBuilder().apply { expression = initializer() }.validateAndBuild().also { expression = it }
}

/**
 * Defines a description for the effect node. The constructed description is added to the build context of the effect node.
 * @param initializer the initializer description builder for the effect node construction.
 * @return the constructed description
 */
fun EffectNodeBuilder.description(initializer: () -> String): String {
    return DescriptionBuilder().apply { description = initializer() }.validateAndBuild().also { description = it }
}