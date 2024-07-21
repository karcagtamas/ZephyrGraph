plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "CauseEffect"

includeBuild("web")

include("libs:common")
findProject(":libs:common")?.name = "common"

include("libs:graph")
findProject(":libs:graph")?.name = "graph"
