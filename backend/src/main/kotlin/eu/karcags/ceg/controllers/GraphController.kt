package eu.karcags.ceg.controllers

import eu.karcags.ceg.examples.*
import eu.karcags.ceg.services.GraphService
import eu.karcags.ceg.utils.wrapping
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private val exampleMap = mapOf(
    Pair("dummy", dummyGraph),
    Pair("date", dateGraph),
    Pair("vacation", vacationGraph),
    Pair("priceCalc", priceCalculationGraph),
    Pair("university", universityGraph),
)

fun Route.graphController() {
    val service = GraphService()

    route("/graph") {
        route("/parse") {
            post("/all") {
                call.respond(service.parseGraph(call.receive<GraphService.ParseObject>()) { service.all(it) }.wrapping())
            }

            post("/visual") {
                call.respond(service.parseGraph(call.receive<GraphService.ParseObject>()) { service.visual(it) }.wrapping())
            }

            route("/logical") {
                post {
                    call.respond(service.parseGraph(call.receive<GraphService.ParseObject>()) { service.logical(it) }.wrapping())
                }

                post("/simple") {
                    call.respond(service.parseGraph(call.receive<GraphService.ParseObject>()) { service.simpleLogical(it) }.wrapping())
                }

                post("/stepped") {
                    call.respond(service.parseGraph(call.receive<GraphService.ParseObject>()) { service.steppedLogical(it) }.wrapping())
                }
            }

            post("/decision-table") {
                call.respond(service.parseGraph(call.receive<GraphService.ParseObject>()) { service.decisionTable(it) }.wrapping())
            }

            post("/export") {
                call.respond(service.parseGraph(call.receive<GraphService.ParseObject>()) { service.export(it) }.wrapping())
            }

            post("/bva") {
                call.respond(service.parseGraph(call.receive<GraphService.ParseObject>()) { service.bva(it) }.wrapping())
            }
        }

        get("/initial") {
            call.respond(
                """import eu.karcags.ceg.graphmodel.dsl.*
                
graph {
    variables {
    
    }
    
    rule {
    
    }
}""".trimIndent().wrapping()
            )
        }

        get("/examples") {
            call.respond(exampleMap.keys.toList().wrapping())
        }

        get("/examples/{key}") {
            val key = call.parameters["key"]

            if (key == null) {
                call.respond(HttpStatusCode.BadRequest)
            }

            if (key !in exampleMap) {
                call.respond(HttpStatusCode.NotFound)
            }

            call.respond(service.all(exampleMap[key]!!).wrapping())
        }
    }
}