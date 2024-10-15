package eu.karcags.ceg.graph.decisiontable

import eu.karcags.ceg.graph.converters.logical.LogicalGraph
import eu.karcags.ceg.graph.converters.logical.LogicalGraphDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.AndDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NotDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.OrDefinition
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DecisionTableTest {

    @Test
    fun shouldParseEmptyLogicalGraph() {
        val result = DecisionTable(LogicalGraph(emptyList()))

        assertEquals(0, result.columns.size)
        assertEquals(0, result.rows.size)
    }

    @Test
    fun shouldParseOneRuleLogicalGraph() {
        val c1 = NodeDefinition("C1", "C1")
        val e1 = NodeDefinition("E1", "E1")
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
                { assertEquals(2, result.rows.size) },
                { assertEquals(listOf("R1-1"), result.columns) },
                { assertEquals(c1, result.rows.first().node) },
                { assertEquals(e1, result.rows[1].node) },
                { assertTrue(result.rows[1].isEffect) },
            )
        )
    }

    @Test
    fun shouldParseComplexRuleLogicalGraph() {
        val c1 = NodeDefinition("C1", "C1")
        val c2 = NodeDefinition("C2", "C2")
        val c3 = NodeDefinition("C3", "C3")
        val c4 = NodeDefinition("C4", "C4")
        val e1 = NodeDefinition("E1", "E1")
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
                { assertEquals(5, result.rows.size) },
                { assertEquals(c1, result.rows.first().node) },
                { assertEquals(c2, result.rows[1].node) },
                { assertEquals(c3, result.rows[2].node) },
                { assertEquals(c4, result.rows[3].node) },
                { assertEquals(e1, result.rows[4].node) },
                { assertTrue(result.rows[4].isEffect) },
            )
        )
    }
}