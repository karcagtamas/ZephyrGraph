package eu.karcags.controllers

import eu.karcags.common.exceptions.GraphException
import eu.karcags.examples.dummyGraph
import eu.karcags.graph.converters.visual.VisualGraph
import eu.karcags.parser.ScriptParser
import eu.karcags.utils.wrapping
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.graphController() {
    route("/graph") {
        post("/parse") {
            val content = call.receive<String>()

            if (content.isBlank()) {
                throw GraphException.ParseException("Received content is empty")
            }

            val parser = ScriptParser()
            val result = parser.parse(content)

            call.respond(VisualGraph(emptyList(), emptyList()).wrapping())
        }

        get("/examples/dummy") {
            call.respond(dummyGraph.toVisualGraph().wrapping())
        }
    }
}