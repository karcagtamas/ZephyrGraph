package eu.karcags.controllers

import eu.karcags.domain.exceptions.GraphException
import eu.karcags.domain.models.GraphModel
import eu.karcags.examples.dummy
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

            call.respond(wrapping(GraphModel(emptyList(), emptyList())))
        }

        get("/examples/dummy") {
            call.respond(wrapping(dummy))
        }
    }
}