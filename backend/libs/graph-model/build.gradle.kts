plugins {
    alias(libs.plugins.kotlin.jvm)
    id("java-library")
    id("maven-publish")
}

group = "eu.karcags.ceg.graphmodel"
version = "1.2.0"

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

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["kotlin"])
        }
    }
    repositories {
        maven {
            url = uri(layout.buildDirectory.dir("publishing-repository"))
        }
    }
}