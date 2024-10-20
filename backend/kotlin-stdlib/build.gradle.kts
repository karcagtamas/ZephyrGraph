plugins {
    kotlin("jvm") version "2.0.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

tasks.register("downloadDeps") {
    doLast {
        configurations["implementation"].forEach { println(it) }
    }
}
