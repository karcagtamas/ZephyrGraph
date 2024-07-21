package eu.karcags.parser

import eu.karcags.graph.Graph
import javax.script.ScriptEngineManager

class ScriptParser {

    fun parse(script: String): Graph {
        val engine = ScriptEngineManager().getEngineByExtension("kts")
        val result = engine.eval(script)

        return result as Graph
    }
}