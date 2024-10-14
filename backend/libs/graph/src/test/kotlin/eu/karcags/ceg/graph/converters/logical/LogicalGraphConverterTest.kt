package eu.karcags.ceg.graph.converters.logical

import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition
import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.Rule
import eu.karcags.ceg.graphmodel.expressions.Expression
import eu.karcags.ceg.graphmodel.expressions.Literal
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
        val c1 = Node.Cause("C1", Expression(Variable("a"), Literal(12), Operator.Equal), null)
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
                        NodeDefinition(e1.id, e1.displayName),
                        NodeDefinition(c1.id, c1.displayName)
                    )
                )
            ), result
        )
    }
}