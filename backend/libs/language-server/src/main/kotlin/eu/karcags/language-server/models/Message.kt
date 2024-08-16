package eu.karcags.`language-server`.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

@Serializable
data class Message(val id: Int?, val jsonrpc: String, val method: String, var processId: Int?, val params: String?) {

    companion object {
        fun encode(message: Message): String {
            val jsonObject = buildJsonObject {
                if (message.id != null) {
                    put("id", message.id)
                }
                put("jsonrpc", message.jsonrpc)
                put("method", message.method)
                if (message.params != null) {
                    putJsonObject("params") {
                        for (el in Json.parseToJsonElement(message.params).jsonObject) {
                            put(el.key, el.value)
                        }

                        if (message.processId != null) {
                            put("processId", message.processId)
                        }
                    }
                }
            }

            return jsonObject.toString()
        }

        fun decode(message: String): Message {
            val jsonObject = Json.parseToJsonElement(message).jsonObject

            return Message(
                jsonObject["id"]?.jsonPrimitive?.intOrNull,
                jsonObject["jsonrpc"]!!.jsonPrimitive.content,
                jsonObject["method"]!!.jsonPrimitive.content,
                jsonObject["params"]?.jsonObject?.get("processId")?.jsonPrimitive?.intOrNull,
                jsonObject["params"]?.toString(),
            )
        }
    }
}