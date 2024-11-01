package eu.karcags.ceg.graph.converters.logical

import eu.karcags.ceg.graph.converters.logical.definitions.AndDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NotDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.OrDefinition
import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.Rule
import eu.karcags.ceg.graphmodel.expressions.Literal
import eu.karcags.ceg.graphmodel.expressions.LogicalExpression
import eu.karcags.ceg.graphmodel.expressions.Operator
import eu.karcags.ceg.graphmodel.expressions.Variable
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LogicalGraphConverterTest {

    var converter: LogicalGraphConverter = LogicalGraphConverter()

    @Test
    fun shouldConvertEmptyGraph() {
        val graph = Graph(emptyList())
        val result = converter.convert(graph)

        assertEquals(LogicalGraph(emptyList()), result)
    }

    @Test
    fun shouldConvertGraphWithOneRule() {
        val c1 = Node.Cause("C1", LogicalExpression(Variable("a"), Literal(12), Operator.Equal), null)
        val e1 = Node.Effect("E1", "description")
        val graph = Graph(
            listOf(
                Rule(
                    1,
                    c1,
                    e1
                )
            )
        )
        val result = converter.convert(graph)

        assertEquals(
            LogicalGraph(
                listOf(
                    LogicalGraphDefinition(
                        NodeDefinition(e1.id, e1.displayName, null),
                        NodeDefinition(c1.id, c1.displayName, null)
                    )
                )
            ), result
        )
    }

    @Test
    fun shouldConvertGraphWithOneComplexRule() {
        val c1 = Node.Cause("C1", LogicalExpression(Variable("a"), Literal(10), Operator.Equal), null)
        val c2 = Node.Cause("C2", LogicalExpression(Variable("a"), Literal(10), Operator.GreaterThan), null)
        val c3 = Node.Cause("C3", LogicalExpression(Variable("b"), Literal(10), Operator.LessThan), null)
        val or = Node.BinaryAction.Or(setOf(c1, Node.UnaryAction.Not(c2)))
        val and = Node.BinaryAction.And(setOf(or, c3))
        val e1 = Node.Effect("E1", "description")
        val graph = Graph(
            listOf(
                Rule(
                    1,
                    and,
                    e1
                )
            )
        )
        val result = converter.convert(graph)

        assertEquals(
            LogicalGraph(
                listOf(
                    LogicalGraphDefinition(
                        NodeDefinition(e1.id, e1.displayName, null),
                        AndDefinition(
                            setOf(
                                OrDefinition(
                                    setOf(
                                        NodeDefinition(c1.id, c1.displayName, null), NotDefinition(
                                            NodeDefinition(c2.id, c2.displayName, null)
                                        )
                                    )
                                ), NodeDefinition(c3.id, c3.displayName, null)
                            )
                        ),
                    )
                )
            ), result
        )
    }

    @Test
    fun shouldConvertGraphWithMultipleRule() {
        val c1 = Node.Cause("C1", LogicalExpression(Variable("a"), Literal(12), Operator.Equal), null)
        val c2 = Node.Cause("C2", LogicalExpression(Variable("b"), Literal(12), Operator.LessThan), null)
        val c3 = Node.Cause("C3", LogicalExpression(Variable("a"), Literal(true), Operator.Equal), null)
        val e1 = Node.Effect("E1", "description")
        val e2 = Node.Effect("E2", "description")
        val e3 = Node.Effect("E3", "description")
        val graph = Graph(
            listOf(
                Rule(
                    1,
                    c1,
                    e1
                ),
                Rule(
                    2,
                    c2,
                    e2
                ),
                Rule(
                    3,
                    c3,
                    e3,
                )
            )
        )
        val result = converter.convert(graph)

        assertEquals(
            LogicalGraph(
                listOf(
                    LogicalGraphDefinition(
                        NodeDefinition(e1.id, e1.displayName, null),
                        NodeDefinition(c1.id, c1.displayName, null)
                    ),
                    LogicalGraphDefinition(
                        NodeDefinition(e2.id, e2.displayName, null),
                        NodeDefinition(c2.id, c2.displayName, null)
                    ),
                    LogicalGraphDefinition(
                        NodeDefinition(e3.id, e3.displayName, null),
                        NodeDefinition(c3.id, c3.displayName, null)
                    )
                )
            ), result
        )
    }

    @Test
    fun shouldConvertGraphWithMultipleComplexRule() {
        val c1 = Node.Cause("C1", LogicalExpression(Variable("a"), Literal(12), Operator.Equal), null)
        val c2 = Node.Cause("C2", LogicalExpression(Variable("b"), Literal(12), Operator.LessThan), null)
        val c3 = Node.Cause("C3", LogicalExpression(Variable("a"), Literal(true), Operator.Equal), null)
        val r1 = Node.BinaryAction.And(setOf(Node.BinaryAction.And(setOf(c1, c2)), c3))
        val r2 = Node.UnaryAction.Not(Node.BinaryAction.Or(setOf(c1, Node.UnaryAction.Not(c3))))
        val r3 = Node.BinaryAction.Or(setOf(Node.BinaryAction.And(setOf(c1, c3)), c2))
        val e1 = Node.Effect("E1", "description")
        val e2 = Node.Effect("E2", "description")
        val e3 = Node.Effect("E3", "description")
        val graph = Graph(
            listOf(
                Rule(
                    1,
                    r1,
                    e1,
                ),
                Rule(
                    2,
                    r2,
                    e2,
                ),
                Rule(
                    3,
                    r3,
                    e3,
                )
            )
        )
        val result = converter.convert(graph)

        assertEquals(
            LogicalGraph(
                listOf(
                    LogicalGraphDefinition(
                        NodeDefinition(e1.id, e1.displayName, null),
                        AndDefinition(
                            setOf(
                                AndDefinition(
                                    setOf(
                                        NodeDefinition(c1.id, c1.displayName, null),
                                        NodeDefinition(c2.id, c2.displayName, null)
                                    )
                                ),
                                NodeDefinition(c3.id, c3.displayName, null)
                            )
                        ),
                    ),
                    LogicalGraphDefinition(
                        NodeDefinition(e2.id, e2.displayName, null),
                        NotDefinition(
                            OrDefinition(
                                setOf(
                                    NodeDefinition(c1.id, c1.displayName, null), NotDefinition(
                                        NodeDefinition(c3.id, c3.displayName, null)
                                    )
                                )
                            )
                        ),
                    ),
                    LogicalGraphDefinition(
                        NodeDefinition(e3.id, e3.displayName, null),
                        OrDefinition(
                            setOf(
                                AndDefinition(
                                    setOf(
                                        NodeDefinition(c1.id, c1.displayName, null),
                                        NodeDefinition(c3.id, c3.displayName, null)
                                    )
                                ), NodeDefinition(c2.id, c2.displayName, null)
                            )
                        )
                    )
                )
            ), result
        )
    }
}