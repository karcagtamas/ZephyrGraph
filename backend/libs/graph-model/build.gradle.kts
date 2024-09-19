plugins {
    alias(libs.plugins.kotlin.jvm)
    id("java-library")
}

group = "eu.karcags.ceg.graphmodel"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.http)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}