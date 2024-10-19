plugins {
    alias(libs.plugins.kotlin.jvm)
    id("java-library")
}

group = "eu.karcags.ceg.common"
version = "2.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.http)
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.1.0")
    testImplementation(libs.mockk)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}