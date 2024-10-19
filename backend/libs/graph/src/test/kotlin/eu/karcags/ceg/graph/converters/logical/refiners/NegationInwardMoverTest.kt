package eu.karcags.ceg.graph.converters.logical.refiners

import eu.karcags.ceg.graph.converters.logical.LogicalGraph
import eu.karcags.ceg.graph.converters.logical.LogicalGraphDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.AndDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NotDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.OrDefinition
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class NegationInwardMoverTest {

    val refiner = NegationInwardMover()

    @Test
    fun shouldLeaveUnchangedTheEmptyGraph() {
        val result = refiner.refine(LogicalGraph(emptyList()))
        val expected = LogicalGraph(emptyList())

        assertEquals(expected, result)
    }

    @Test
    fun shouldLeaveUnchangedTheNodeDefinitions() {
        val e1 = NodeDefinition("E1", "E1")
        val c1 = NodeDefinition("C1", "C1")

        val result = refiner.refine(
            LogicalGraph(
                listOf(
                    LogicalGraphDefinition(e1, c1)
                )
            )
        )
        val expected = LogicalGraph(
            listOf(
                LogicalGraphDefinition(e1, c1)
            )
        )

        assertEquals(expected, result)
    }

    @Test
    fun shouldLeaveUnchangedTheDefinitionsWithoutNot() {
        val e1 = NodeDefinition("E1", "E1")
        val c1 = NodeDefinition("C1", "C1")
        val c2 = NodeDefinition("C2", "C2")

        val result = refiner.refine(
            LogicalGraph(
                listOf(
                    LogicalGraphDefinition(e1, AndDefinition(setOf(c1, c2)))
                )
            )
        )
        val expected = LogicalGraph(
            listOf(
                LogicalGraphDefinition(e1, AndDefinition(setOf(c1, c2)))
            )
        )

        assertEquals(expected, result)
    }

    @Test
    fun shouldLeaveUnchangedTheDefinitionsWithNodeNot() {
        val e1 = NodeDefinition("E1", "E1")
        val c1 = NodeDefinition("C1", "C1")
        val c2 = NodeDefinition("C2", "C2")

        val result = refiner.refine(
            LogicalGraph(
                listOf(
                    LogicalGraphDefinition(e1, AndDefinition(setOf(NotDefinition(c1), c2)))
                )
            )
        )
        val expected = LogicalGraph(
            listOf(
                LogicalGraphDefinition(e1, AndDefinition(setOf(NotDefinition(c1), c2)))
            )
        )

        assertEquals(expected, result)
    }

    @Test
    fun shouldRemoveTheDuplicatedNotOnNode() {
        val e1 = NodeDefinition("E1", "E1")
        val c1 = NodeDefinition("C1", "C1")

        val result = refiner.refine(
            LogicalGraph(
                listOf(
                    LogicalGraphDefinition(e1, NotDefinition(NotDefinition(c1)))
                )
            )
        )
        val expected = LogicalGraph(
            listOf(
                LogicalGraphDefinition(e1, c1)
            )
        )

        assertEquals(expected, result)
    }

    @Test
    fun shouldReduceTheTriplicatedNotOnNode() {
        val e1 = NodeDefinition("E1", "E1")
        val c1 = NodeDefinition("C1", "C1")

        val result = refiner.refine(
            LogicalGraph(
                listOf(
                    LogicalGraphDefinition(e1, NotDefinition(NotDefinition(NotDefinition(c1))))
                )
            )
        )
        val expected = LogicalGraph(
            listOf(
                LogicalGraphDefinition(e1, NotDefinition(c1))
            )
        )

        assertEquals(expected, result)
    }

    @Test
    fun shouldMoveNotInwardOnSimpleAnd() {
        val e1 = NodeDefinition("E1", "E1")
        val c1 = NodeDefinition("C1", "C1")
        val c2 = NodeDefinition("C2", "C2")

        val result = refiner.refine(
            LogicalGraph(
                listOf(
                    LogicalGraphDefinition(e1, NotDefinition(AndDefinition(setOf(c1, c2))))
                )
            )
        )
        val expected = LogicalGraph(
            listOf(
                LogicalGraphDefinition(e1, OrDefinition(setOf(NotDefinition(c1), NotDefinition(c2))))
            )
        )

        assertEquals(expected, result)
    }

    @Test
    fun shouldMoveNotInwardOnSimpleOr() {
        val e1 = NodeDefinition("E1", "E1")
        val c1 = NodeDefinition("C1", "C1")
        val c2 = NodeDefinition("C2", "C2")

        val result = refiner.refine(
            LogicalGraph(
                listOf(
                    LogicalGraphDefinition(e1, NotDefinition(OrDefinition(setOf(c1, c2))))
                )
            )
        )
        val expected = LogicalGraph(
            listOf(
                LogicalGraphDefinition(e1, AndDefinition(setOf(NotDefinition(c1), NotDefinition(c2))))
            )
        )

        assertEquals(expected, result)
    }

    @Test
    fun shouldMoveNotInwardOnComplexDefinition() {
        val e1 = NodeDefinition("E1", "E1")
        val c1 = NodeDefinition("C1", "C1")
        val c2 = NodeDefinition("C2", "C2")

        val result = refiner.refine(
            LogicalGraph(
                listOf(
                    LogicalGraphDefinition(
                        e1, NotDefinition(
                            OrDefinition(
                                setOf(
                                    NotDefinition(
                                        AndDefinition(
                                            setOf(
                                                NotDefinition(c1), c2
                                            )
                                        )
                                    ), c2
                                )
                            )
                        )
                    )
                )
            )
        )
        val expected = LogicalGraph(
            listOf(
                LogicalGraphDefinition(
                    e1, AndDefinition(
                        setOf(
                            AndDefinition(setOf(NotDefinition(c1), c2)),
                            NotDefinition(c2)
                        )
                    )
                )
            )
        )

        assertEquals(expected, result)
    }
}