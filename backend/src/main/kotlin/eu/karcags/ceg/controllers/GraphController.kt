package eu.karcags.ceg.controllers

import eu.karcags.ceg.examples.dummyGraph
import eu.karcags.ceg.graph.converters.logical.LogicalGraph
import eu.karcags.ceg.graph.converters.logical.definitions.AndDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.ImplicateDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NotDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.OrDefinition
import eu.karcags.ceg.graph.converters.logical.refiners.ApplyDistributiveLaw
import eu.karcags.ceg.graph.converters.logical.refiners.ImplicationElimination
import eu.karcags.ceg.graph.converters.logical.refiners.NegationInwardMover
import eu.karcags.ceg.graph.converters.logical.resources.PremadeResources
import eu.karcags.ceg.graph.converters.toLogicalGraph
import eu.karcags.ceg.graph.converters.toVisualGraph
import eu.karcags.ceg.graph.exceptions.GraphParseException
import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.parser.ScriptParser
import eu.karcags.ceg.utils.wrapping
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import javax.script.ScriptEngineManager

fun Route.graphController() {
    route("/graph") {
        route("/parse") {
            post("/visual") {
                call.respond(parseGraph(call.receive<ParseObject>()) { it.toVisualGraph() }.wrapping())
            }

            route("/logical") {
                post {
                    call.respond(parseGraph(call.receive<ParseObject>()) { it.toLogicalGraph() }.wrapping())
                }

                post("/simple") {
                    call.respond(parseGraph(call.receive<ParseObject>()) { it.toLogicalGraph().definitions.map { def -> def.toString() }.let { items -> mapOf(Pair("definitions", items)) } }.wrapping())
                }

                post("/simple/test") {
                    val resource = PremadeResources.DEFAULT.getResource()
                    call.respond(
                        LogicalGraph(listOf(
                            AndDefinition(
                                ImplicateDefinition(
                                    NodeDefinition("A", "A"),
                                    NodeDefinition("B", "B"),
                                    resource.IMPLICATE),
                                AndDefinition(
                                    OrDefinition(
                                        NotDefinition(NodeDefinition("B", "B"), resource.NOT),
                                        NodeDefinition("C", "C"),
                                        resource.OR
                                    ),
                                    OrDefinition(
                                        NotDefinition(NodeDefinition("C", "C"), resource.NOT),
                                        NodeDefinition("B", "B"),
                                        resource.OR
                                    ),
                                    resource.AND
                                ),
                                resource.AND))).let { ApplyDistributiveLaw(resource).refine(NegationInwardMover(resource).refine(ImplicationElimination(resource).refine(it))) }.let { it.toString() }.wrapping())
                }
            }
        }

        get("/initial") {
            call.respond(
                """import eu.karcags.ceg.graphmodel.dsl.*
                
graph {

}""".trimIndent().wrapping()
            )
        }

        get("/examples/dummy") {
            call.respond(dummyGraph.toVisualGraph().wrapping())
        }
    }
}

@Serializable
data class ParseObject(val content: String)

fun <T> parseGraph(obj: ParseObject, mapper: (Graph) -> T): T {
    if (obj.content.isBlank() || obj.content.isEmpty()) {
        throw GraphParseException("Received content is empty")
    }

    val engine = ScriptEngineManager().getEngineByExtension("kts")
    return ScriptParser(engine).parse(obj.content).let(mapper)
}