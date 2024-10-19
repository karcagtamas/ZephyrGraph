package eu.karcags.ceg.graph.converters.logical.refiners

import eu.karcags.ceg.graph.converters.logical.LogicalGraph
import eu.karcags.ceg.graph.converters.logical.LogicalGraphDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.AndDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.OrDefinition
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PreOptimizerTest {

    val refiner = PreOptimizer()

    @Test
    fun shouldLeaveEmptyGraphUnchanged() {
        val result = refiner.refine(LogicalGraph(emptyList()))
        val expected = LogicalGraph(emptyList())

        assertEquals(expected, result)
    }

    @Test
    fun shouldCollapseTheDoubleAnds() {
        val e1 = NodeDefinition("E1", "E1")
        val c1 = NodeDefinition("C1", "C1")
        val c2 = NodeDefinition("C2", "C2")
        val c3 = NodeDefinition("C3", "C3")

        val result = refiner.refine(
            LogicalGraph(
                listOf(
                    LogicalGraphDefinition(
                        e1,
                        AndDefinition(setOf(AndDefinition(setOf(c1, c2)), c3))
                    )
                )
            )
        )
        val expected = LogicalGraph(listOf(LogicalGraphDefinition(e1, AndDefinition(setOf(c1, c2, c3)))))

        assertEquals(expected, result)
    }

    @Test
    fun shouldCollapseTheDoubleOrs() {
        val e1 = NodeDefinition("E1", "E1")
        val c1 = NodeDefinition("C1", "C1")
        val c2 = NodeDefinition("C2", "C2")
        val c3 = NodeDefinition("C3", "C3")

        val result = refiner.refine(
            LogicalGraph(
                listOf(
                    LogicalGraphDefinition(
                        e1,
                        OrDefinition(setOf(c3, OrDefinition(setOf(c1, c2))))
                    )
                )
            )
        )
        val expected = LogicalGraph(listOf(LogicalGraphDefinition(e1, OrDefinition(setOf(c3, c1, c2)))))

        assertEquals(expected, result)
    }

    @Test
    fun shouldCollapseTheComplexBinaryDefinitions() {
        val e1 = NodeDefinition("E1", "E1")
        val c1 = NodeDefinition("C1", "C1")
        val c2 = NodeDefinition("C2", "C2")
        val c3 = NodeDefinition("C3", "C3")
        val c4 = NodeDefinition("C4", "C4")
        val c5 = NodeDefinition("C5", "C5")

        val result = refiner.refine(
            LogicalGraph(
                listOf(
                    LogicalGraphDefinition(
                        e1,
                        OrDefinition(
                            setOf(
                                AndDefinition(setOf(c3, AndDefinition(setOf(c4, c5)))),
                                OrDefinition(setOf(c1, c2))
                            )
                        )
                    )
                )
            )
        )
        val expected = LogicalGraph(
            listOf(
                LogicalGraphDefinition(
                    e1,
                    OrDefinition(setOf(AndDefinition(setOf(c3, c4, c5)), c1, c2))
                )
            )
        )

        assertEquals(expected, result)
    }
}