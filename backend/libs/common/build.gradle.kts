plugins {
    alias(libs.plugins.kotlin.jvm)
    id("java-library")
}

group = "eu.karcags.ceg.common"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.http)
    testImplementation(kotlin("test"))
    testImplementation(libs.mockk)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}