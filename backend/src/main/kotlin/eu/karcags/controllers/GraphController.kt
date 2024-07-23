package eu.karcags.controllers

import eu.karcags.common.exceptions.GraphException
import eu.karcags.examples.dummyGraph
import eu.karcags.parser.ScriptParser
import eu.karcags.utils.wrapping
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Route.graphController() {
    route("/graph") {
        post("/parse") {
            val obj = call.receive<ParseObject>()

            if (obj.content.isBlank() || obj.content.isEmpty()) {
                throw GraphException.ParseException("Received content is empty")
            }

            val result = ScriptParser().parse(obj.content)

            call.respond(result.toVisualGraph().wrapping())
        }

        get("/initial") {
            call.respond("""import eu.karcags.graph.dsl.*
                
graph {

}""".trimIndent().wrapping())
        }

        get("/examples/dummy") {
            call.respond(dummyGraph.toVisualGraph().wrapping())
        }
    }
}

@Serializable
data class ParseObject(val content: String)