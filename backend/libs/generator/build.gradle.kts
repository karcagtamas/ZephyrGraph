plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    id("java-library")
}

group = "eu.karcags.ceg.generator"
version = "2.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlinx.serialization.json)

    implementation(project(":libs:common"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}