package eu.karcags.ceg.graph.decisiontable

import eu.karcags.ceg.graph.converters.logical.LogicalGraph
import eu.karcags.ceg.graph.converters.logical.LogicalGraphDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.AndDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NotDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.OrDefinition
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertTrue

class DecisionTableTest {

    @Test
    fun shouldParseEmptyLogicalGraph() {
        val result = DecisionTable(LogicalGraph(emptyList()))

        assertEquals(0, result.columns.size)
        assertTrue(result.columns.all { it.items.isEmpty() })
    }

    @Test
    fun shouldParseOneRuleLogicalGraph() {
        val c1 = NodeDefinition("C1", "C1", null)
        val e1 = NodeDefinition("E1", "E1", null)
        val result = DecisionTable(
            LogicalGraph(
                listOf(
                    LogicalGraphDefinition(
                        e1,
                        c1,
                    )
                )
            )
        )

        assertAll(
            listOf(
                { assertEquals(1, result.columns.size) },
                { assertTrue(result.columns.all { it.items.size == 2 }) },
                { assertEquals("R1-1", result.columns.first().name) },
                { assertTrue(result.columns.all { it.items.first().node == c1 }) },
                { assertTrue(result.columns.all { it.items[1].node == e1 }) },
                { assertTrue(result.columns.all { it.items[1].isEffect }) },
            )
        )
    }

    @Test
    fun shouldParseComplexRuleLogicalGraph() {
        val c1 = NodeDefinition("C1", "C1", null)
        val c2 = NodeDefinition("C2", "C2", null)
        val c3 = NodeDefinition("C3", "C3", null)
        val c4 = NodeDefinition("C4", "C4", null)
        val e1 = NodeDefinition("E1", "E1", null)
        val result = DecisionTable(
            LogicalGraph(
                listOf(
                    LogicalGraphDefinition(
                        e1,
                        OrDefinition(setOf(AndDefinition(setOf(c1, c2)), AndDefinition(setOf(NotDefinition(c3), c4)))),
                    )
                )
            )
        )

        assertAll(
            listOf(
                { assertEquals(2, result.columns.size) },
                { assertTrue(result.columns.all { it.items.size == 5 }) },
                { assertEquals("R1-1", result.columns.first().name) },
                { assertTrue(result.columns.all { it.items.first().node == c1 }) },
                { assertTrue(result.columns.all { it.items[1].node == c2 }) },
                { assertTrue(result.columns.all { it.items[2].node == c3 }) },
                { assertTrue(result.columns.all { it.items[3].node == c4 }) },
                { assertTrue(result.columns.all { it.items[4].node == e1 }) },
                { assertTrue(result.columns.all { it.items[4].isEffect }) },
            )
        )
    }
}