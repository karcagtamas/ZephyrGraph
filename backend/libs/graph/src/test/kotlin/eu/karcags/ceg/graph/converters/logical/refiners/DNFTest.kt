package eu.karcags.ceg.graph.converters.logical.refiners

import eu.karcags.ceg.graph.converters.logical.LogicalGraph
import eu.karcags.ceg.graph.converters.logical.LogicalGraphDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.AndDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NotDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.OrDefinition
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DNFTest {

    val refiner = DNF()

    @Test
    fun shouldLeaveUnchangedTheEmptyGraph() {
        val result = refiner.refine(LogicalGraph(emptyList()))
        val expected = LogicalGraph(emptyList())

        assertEquals(expected, result)
    }

    @Test
    fun shouldNotChangeSimpleDefinition() {
        val e1 = NodeDefinition("E1", "E1", null)
        val c1 = NodeDefinition("C1", "C1", null)

        val result = refiner.refine(LogicalGraph(listOf(LogicalGraphDefinition(e1, c1))))
        val expected = LogicalGraph(listOf(LogicalGraphDefinition(e1, c1)))

        assertEquals(expected, result)
    }

    @Test
    fun shouldNotChangeSimpleNotDefinition() {
        val e1 = NodeDefinition("E1", "E1", null)
        val c1 = NodeDefinition("C1", "C1", null)

        val result = refiner.refine(LogicalGraph(listOf(LogicalGraphDefinition(e1, NotDefinition(c1)))))
        val expected = LogicalGraph(listOf(LogicalGraphDefinition(e1, NotDefinition(c1))))

        assertEquals(expected, result)
    }

    @Test
    fun shouldNotChangeSimpleAndDefinition() {
        val e1 = NodeDefinition("E1", "E1", null)
        val c1 = NodeDefinition("C1", "C1", null)
        val c2 = NodeDefinition("C2", "C2", null)

        val result = refiner.refine(LogicalGraph(listOf(LogicalGraphDefinition(e1, AndDefinition(setOf(c1, c2))))))
        val expected = LogicalGraph(listOf(LogicalGraphDefinition(e1, AndDefinition(setOf(c1, c2)))))

        assertEquals(expected, result)
    }

    @Test
    fun shouldConvertSimpleOrDefinition() {
        val e1 = NodeDefinition("E1", "E1", null)
        val c1 = NodeDefinition("C1", "C1", null)
        val c2 = NodeDefinition("C2", "C2", null)

        val result = refiner.refine(LogicalGraph(listOf(LogicalGraphDefinition(e1, OrDefinition(setOf(c1, c2))))))
        val expected = LogicalGraph(listOf(LogicalGraphDefinition(e1, OrDefinition(setOf(c1, c2)))))

        assertEquals(expected, result)
    }

    @Test
    fun shouldConvertAlreadyDNFDefinition() {
        val e1 = NodeDefinition("E1", "E1", null)
        val c1 = NodeDefinition("C1", "C1", null)
        val c2 = NodeDefinition("C2", "C2", null)
        val c3 = NodeDefinition("C3", "C3", null)

        val result = refiner.refine(
            LogicalGraph(
                listOf(
                    LogicalGraphDefinition(
                        e1,
                        OrDefinition(setOf(AndDefinition(setOf(c1, c3)), c2))
                    )
                )
            )
        )
        val expected =
            LogicalGraph(listOf(LogicalGraphDefinition(e1, OrDefinition(setOf(AndDefinition(setOf(c1, c3)), c2)))))

        assertEquals(expected, result)
    }

    @Test
    fun shouldConvertFullDNFDefinition() {
        val e1 = NodeDefinition("E1", "E1", null)
        val c1 = NodeDefinition("C1", "C1", null)
        val c2 = NodeDefinition("C2", "C2", null)
        val c3 = NodeDefinition("C3", "C3", null)
        val c4 = NodeDefinition("C4", "C4", null)

        val result = refiner.refine(
            LogicalGraph(
                listOf(
                    LogicalGraphDefinition(
                        e1, OrDefinition(
                            setOf(
                                AndDefinition(setOf(c1, c3)),
                                AndDefinition(setOf(c2, c4))
                            )
                        )
                    )
                )
            )
        )
        val expected = LogicalGraph(
            listOf(
                LogicalGraphDefinition(
                    e1, OrDefinition(
                        setOf(
                            AndDefinition(setOf(c1, c3)),
                            AndDefinition(setOf(c2, c4))
                        )
                    )
                )
            )
        )

        assertEquals(expected, result)
    }

    @Test
    fun shouldConvertComplexDefinition() {
        val e1 = NodeDefinition("E1", "E1", null)
        val c1 = NodeDefinition("C1", "C1", null)
        val c2 = NodeDefinition("C2", "C2", null)
        val c3 = NodeDefinition("C3", "C3", null)
        val c4 = NodeDefinition("C4", "C4", null)

        val result = refiner.refine(
            LogicalGraph(
                listOf(
                    LogicalGraphDefinition(
                        e1, AndDefinition(
                            setOf(
                                OrDefinition(setOf(c1, c3)),
                                AndDefinition(setOf(c2, c4))
                            )
                        )
                    )
                )
            )
        )
        val expected = LogicalGraph(
            listOf(
                LogicalGraphDefinition(
                    e1, OrDefinition(
                        setOf(
                            AndDefinition(setOf(c1.not(), c3, c2, c4)),
                            AndDefinition(setOf(c1, c3.not(), c2, c4)),
                            AndDefinition(setOf(c1, c3, c2, c4)),
                        )
                    )
                )
            )
        )

        assertEquals(expected, result)
    }
}