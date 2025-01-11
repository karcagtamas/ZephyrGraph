package eu.karcags.ceg.graphmodel.dsl

import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.exceptions.GraphException
import eu.karcags.ceg.graphmodel.expressions.Literal
import eu.karcags.ceg.graphmodel.expressions.LogicalExpression
import eu.karcags.ceg.graphmodel.expressions.Operator
import eu.karcags.ceg.graphmodel.expressions.Variable
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class ExtensionsGraphTest {

    @Test
    fun shouldGraphConstructAnEmptyGraph() {
        val result = graph { }
        val expected = Graph(emptyList())

        assertEquals(expected, result)
    }

    @Test
    fun shouldRuleConstructAnRuleIntoAGraph() {
        val result = graph {
            variables {
                int("a")
            }
            rule {
                cause("C1") { expression { variable("a") eq 10 } }
                effect { "alma" }
            }
        }

        assertAll(
            listOf(
                { assertEquals(1, result.rules.size) },
                { assertEquals(1, result.rules.first().id) },
                { assertEquals("C1", result.rules.first().cause.displayName) },
                {
                    assertEquals(
                        LogicalExpression(Variable("a", Int::class), Literal(10), Operator.Equal),
                        result.rules.first().cause.expression
                    )
                },
                { assertEquals("E1", result.rules.first().effect.displayName) },
                { assertEquals("alma", result.rules.first().effect.description) },
            )
        )
    }

    @Test
    fun shouldRuleThrowExceptionWithoutEffect() {
        assertThrows<GraphException.ValidateException> {
            graph {
                variables { float("a") }
                rule {
                    cause("C1") { expression { variable("a") eq 10f } }
                }
            }
        }
    }

    @Test
    fun shouldRuleThrowExceptionWithoutCause() {
        assertThrows<GraphException.ValidateException> {
            graph {
                rule {
                    effect { "alma" }
                }
            }
        }
    }

    @Test
    fun shouldRuleConstructAnComplexRuleIntoAGraph() {
        val result = graph {
            variables { int("a") }
            cause("C10") { variable("a") lte 100 }

            rule {
                and {
                    or {
                        cause("C1") { expression { variable("a") eq 10 } }
                        causeById("C10")
                    }
                    not { cause("C3") { variable("a") eq 12 } }
                }

                effect { "alma" }
            }
        }

        assertAll(
            listOf(
                { assertEquals(1, result.rules.size) },
                { assertEquals(1, result.rules.first().id) },
                { assert(result.rules.first().cause is Node.BinaryAction.And) },
                { assertEquals("E1", result.rules.first().effect.displayName) },
                { assertEquals("alma", result.rules.first().effect.description) },
            )
        )
    }

    @Test
    fun shouldAndThrowExceptionWithoutChild() {
        assertThrows<GraphException.ValidateException> {
            graph {
                rule {
                    and { }
                    effect { "alma" }
                }
            }
        }
    }

    @Test
    fun shouldAndThrowExceptionWithOnlyOneChild() {
        assertThrows<GraphException.ValidateException> {
            graph {
                rule {
                    and {
                        cause("C1") { lit(1) gt lit(2) }
                    }
                    effect { "alma" }
                }
            }
        }
    }

    @Test
    fun shouldOrThrowExceptionWithoutChild() {
        assertThrows<GraphException.ValidateException> {
            graph {
                rule {
                    or { }
                    effect { "alma" }
                }
            }
        }
    }

    @Test
    fun shouldOrThrowExceptionWithOnlyOneChild() {
        assertThrows<GraphException.ValidateException> {
            graph {
                rule {
                    or {
                        cause("C1") { lit(1) gt lit(2) }
                    }
                    effect { "alma" }
                }
            }
        }
    }

    @Test
    fun shouldNotThrowExceptionWithoutChild() {
        assertThrows<GraphException.ValidateException> {
            graph {
                rule {
                    not { }
                    effect { "alma" }
                }
            }
        }
    }

    @Test
    fun shouldGraphThrowExceptionWithSameCauseNames() {
        assertThrows<GraphException.ValidateException> {
            graph {
                variables { float("c") }
                rule {
                    and {
                        cause("C1") { variable("c") gte 1f }
                        cause("C1") { variable("c") gt 2f }
                    }
                    effect { "alma" }
                }
            }
        }
    }

    @Test
    fun shouldGraphThrowExceptionWithSameCauseNamesInDifferentRules() {
        assertThrows<GraphException.ValidateException> {
            graph {
                variables {
                    int("c")
                    float("d")
                }
                rule {
                    and {
                        cause("C1") { variable("c") gte 1 }
                        cause("C3") { variable("c") lte 3 }
                    }
                    effect { "alma" }
                }

                rule {
                    cause("C1") { variable("d") lt 3f }
                    effect { "korte" }
                }
            }
        }
    }

    @Test
    fun shouldGraphThrowExceptionWithSameCauseNamesWhenDefinedAbove() {
        assertThrows<GraphException.ValidateException> {
            graph {
                variables { int("c") }
                cause("C1") { lit(2) lte variable("c") }

                rule {
                    and {
                        cause("C2") { lit(1) lt variable("c") }
                        cause("C3") { lit(3) gt variable("c") }
                    }
                    effect { "alma" }
                }

                rule {
                    cause("C1") { lit(3) lt variable("c") }
                    effect { "korte" }
                }
            }
        }
    }

    @Test
    fun shouldGraphThrowExceptionWhenInvalidCauseByIdUsed() {
        assertThrows<NullPointerException> {
            graph {
                variables {
                    int("c")
                    boolean("b")
                }
                cause("C1") { variable("b") eq true }

                rule {
                    and {
                        cause("C2") { variable("c") lt lit(1) }
                        cause("C3") { variable("c") gt lit(3) }
                    }
                    effect { "alma" }
                }

                rule {
                    causeById("c2")
                    effect { "korte" }
                }
            }
        }
    }

    @Test
    fun shouldEffectThrowExceptionWithEmptyDescription() {
        assertThrows<GraphException.ValidateException> {
            graph {
                rule {
                    cause("C1") { lit(1) eq lit(2) }
                    effect { "" }
                }
            }
        }
    }

    @Test
    fun shouldDescriptionThrowExceptionWithEmptyDescription() {
        assertThrows<GraphException.ValidateException> {
            graph {
                rule {
                    cause("C1") { lit(1) eq lit(2) }
                    effect { description { "" } }
                }
            }
        }
    }
}