package eu.karcags.ceg.controllers

import eu.karcags.ceg.examples.dateGraph
import eu.karcags.ceg.examples.dummyGraph
import eu.karcags.ceg.generator.BVA
import eu.karcags.ceg.graph.converters.logical.LogicalGraphConverter
import eu.karcags.ceg.graph.converters.logical.resources.AbstractSignResource
import eu.karcags.ceg.graph.converters.logical.resources.DefaultSignResource
import eu.karcags.ceg.graph.converters.toBVA
import eu.karcags.ceg.graph.converters.toLogicalGraph
import eu.karcags.ceg.graph.converters.toSteppedLogicalGraph
import eu.karcags.ceg.graph.converters.toVisualGraph
import eu.karcags.ceg.graph.converters.visual.VisualGraph
import eu.karcags.ceg.graph.decisiontable.DecisionTable
import eu.karcags.ceg.graph.exceptions.GraphParseException
import eu.karcags.ceg.graph.gpt.Exporter
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
            post("/all") {
                val result = parseGraph(call.receive<ParseObject>()) {
                    val visual = it.toVisualGraph()
                    val logical = it.toSteppedLogicalGraph()
                    val resource = DefaultSignResource()
                    val decisionTable = DecisionTable.from(logical.final.graph).optimize()
                    ParseResult(
                        visual,
                        LogicalResult(
                            LogicalItemResult(logical.final, resource),
                            logical.prevSteps.map { item -> LogicalItemResult(item, resource) }),
                        decisionTable.export(),
                        logical.final.graph.toBVA(),
                        Exporter(decisionTable).export()
                    )
                }

                call.respond(result.wrapping())
            }

            post("/visual") {
                call.respond(parseGraph(call.receive<ParseObject>()) { it.toVisualGraph() }.wrapping())
            }

            route("/logical") {
                post {
                    call.respond(parseGraph(call.receive<ParseObject>()) { it.toLogicalGraph() }.wrapping())
                }

                post("/simple") {
                    call.respond(parseGraph(call.receive<ParseObject>()) {
                        it.toLogicalGraph().stringify(DefaultSignResource())
                            .let { items -> mapOf(Pair("definitions", items)) }
                    }.wrapping())
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

        get("examples/date") {
            call.respond(dateGraph.toVisualGraph().wrapping())
        }
    }
}

@Serializable
data class ParseObject(val content: String)

@Serializable
data class ParseResult(val visual: VisualGraph, val logical: LogicalResult, val decisionTable: DecisionTable.Export, val bva: List<BVA.FinalResult>, val export: String)

@Serializable
data class LogicalResult(val final: LogicalItemResult, val prevSteps: List<LogicalItemResult>)

@Serializable
data class LogicalItemResult(val key: String, val definitions: List<String>) {

    constructor(item: LogicalGraphConverter.SteppedRefinerItem, resource: AbstractSignResource) : this(
        item.key,
        item.graph.stringify(resource)
    )
}

fun <T> parseGraph(obj: ParseObject, mapper: (Graph) -> T): T {
    if (obj.content.isBlank() || obj.content.isEmpty()) {
        throw GraphParseException("Received content is empty")
    }

    val engine = ScriptEngineManager().getEngineByExtension("kts")
    return ScriptParser(engine).parse(obj.content).let(mapper)
}