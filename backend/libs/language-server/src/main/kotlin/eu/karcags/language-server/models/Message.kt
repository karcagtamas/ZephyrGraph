package eu.karcags.`language-server`.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

@Serializable
data class Message(val id: Int, val jsonrpc: String, val method: String, var processId: Int?, val params: String) {

    companion object {
        private val MyJson = Json {
            ignoreUnknownKeys = true
        }

        fun encode(message: Message): String {
            val jsonObject = buildJsonObject {
                put("id", message.id)
                put("jsonrpc", message.jsonrpc)
                put("method", message.method)
                putJsonObject("params") {
                    for (el in Json.parseToJsonElement(message.params).jsonObject) {
                        put(el.key, el.value)
                    }
                    put("processId", message.processId)
                }
            }

            return jsonObject.toString()
        }

        fun decode(message: String): Message {
            val jsonObject = Json.parseToJsonElement(message).jsonObject

            return Message(
                jsonObject["id"]!!.jsonPrimitive.int,
                jsonObject["jsonrpc"]!!.jsonPrimitive.content,
                jsonObject["method"]!!.jsonPrimitive.content,
                jsonObject["params"]!!.jsonObject["processId"]!!.jsonPrimitive.intOrNull,
                jsonObject["params"]!!.toString(),
            )
        }

        fun isRequest(message: Message): Boolean {
            return true
        }

        fun isResponse(message: Message): Boolean {
            return false
        }
    }
}