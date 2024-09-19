plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "CauseEffectGraph"

includeBuild("web")

include("libs:common")
findProject(":libs:common")?.name = "common"

include("libs:language-server")
findProject(":libs:language-server")?.name = "language-server"

include("libs:graph-model")
findProject(":libs:graph-model")?.name = "graph-model"

include("libs:graph")
findProject(":libs:graph")?.name = "graph"
