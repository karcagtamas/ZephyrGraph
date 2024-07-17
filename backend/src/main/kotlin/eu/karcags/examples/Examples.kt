package eu.karcags.examples

import eu.karcags.domain.models.*

val dummyNodes = listOf(
    Node("c1", "C1", NodeMeta.CauseMeta()),
    Node("c2", "C2", NodeMeta.CauseMeta()),
    Node("c3", "C3", NodeMeta.CauseMeta()),
    Node("c4", "C4", NodeMeta.CauseMeta()),
    Node("c5", "C5", NodeMeta.CauseMeta()),
    Node("c6", "C6", NodeMeta.CauseMeta()),
    Node("c7", "C7", NodeMeta.CauseMeta()),
    Node("e1", "E1", NodeMeta.EffectMeta()),
    Node("e2", "E2", NodeMeta.EffectMeta()),
    Node("e3", "E3", NodeMeta.EffectMeta()),
    Node("a1", "AND", NodeMeta.ActionMeta(Action.AND)),
    Node("a2", "AND", NodeMeta.ActionMeta(Action.AND)),
    Node("a3", "OR", NodeMeta.ActionMeta(Action.OR)),
    Node("c8", "C8", NodeMeta.CauseMeta()),
)

val dummyEdges = listOf(
    Edge(dummyNodes[0], dummyNodes[7]),
    Edge(dummyNodes[1], dummyNodes[7]),
    Edge(dummyNodes[2], dummyNodes[10]),
    Edge(dummyNodes[3], dummyNodes[10]),
    Edge(dummyNodes[10], dummyNodes[9]),
    Edge(dummyNodes[4], dummyNodes[11]),
    Edge(dummyNodes[5], dummyNodes[11]),
    Edge(dummyNodes[6], dummyNodes[12]),
    Edge(dummyNodes[11], dummyNodes[12]),
    Edge(dummyNodes[12], dummyNodes[9]),
    Edge(dummyNodes[13], dummyNodes[10]),
)

val dummy = GraphModel(
    dummyNodes,
    dummyEdges,
)