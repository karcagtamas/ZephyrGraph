package eu.karcags.ceg.controllers

import eu.karcags.ceg.common.exceptions.GraphException
import eu.karcags.ceg.examples.dummyGraph
import eu.karcags.ceg.graph.Graph
import eu.karcags.ceg.graph.converters.toLogicalGraph
import eu.karcags.ceg.graph.converters.toVisualGraph
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

            post("/logical") {
                call.respond(parseGraph(call.receive<ParseObject>()) { it.toLogicalGraph() }.wrapping())
            }
        }

        get("/initial") {
            call.respond(
                """import eu.karcags.ceg.graph.dsl.*
                
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
        throw GraphException.ParseException("Received content is empty")
    }

    val engine = ScriptEngineManager().getEngineByExtension("kts")
    return ScriptParser(engine).parse(obj.content).let(mapper)
}