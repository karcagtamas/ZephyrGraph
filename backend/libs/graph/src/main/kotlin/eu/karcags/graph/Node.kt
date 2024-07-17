package eu.karcags.graph

import java.util.UUID

open class Node(val displayName: String) {
    
    val id = UUID.randomUUID().toString()
    
    class CauseNode(displayName: String) : Node(displayName)
    
    class EffectNode(displayName: String): Node(displayName)
    
    open class ActionNode(displayName: String, val left: Node, val right: Node) : Node(displayName) {
        
        class AndNode(left: Node, right: Node) : ActionNode("AND", left, right)
        
        class OrNode(left: Node, right: Node) : ActionNode("OR", left, right)
    }
}