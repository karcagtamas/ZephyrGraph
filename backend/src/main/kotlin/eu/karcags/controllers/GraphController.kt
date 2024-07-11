package eu.karcags.controllers

import eu.karcags.examples.dummy
import eu.karcags.models.GraphModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.graphController() {
    get("/parse") {
        val content = call.receive<String>()

        if (content.isBlank()) {
            call.respond(HttpStatusCode.BadRequest)
        }

        call.respond(GraphModel(emptyList(), emptyList()))
    }

    get("/examples/dummy") {
        call.respond(dummy)
    }
}