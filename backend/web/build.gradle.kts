import com.github.gradle.node.yarn.task.YarnTask

plugins {
    kotlin("jvm")
    id("com.github.node-gradle.node") version "7.0.2"
}

group = "eu.karcags"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
}

tasks.register("prepareKotlinBuildScriptModel") {}

tasks.create<YarnTask>("buildReact") {
    dependsOn(tasks.yarnSetup)
    dependsOn(tasks.yarn)

    args.set(listOf("build"))
}