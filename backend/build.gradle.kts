plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
    alias(libs.plugins.shadow)
}

group = "eu.karcags.ceg"
version = "2.0.0"

val webDistSource = file("web/dist")
val webDistDestination = layout.buildDirectory.dir("install/${application.applicationName}/lib/web/dist").get().asFile
val shadowWebDistDestination = layout.buildDirectory.dir("install/${application.applicationName}-shadow/lib/web/dist").get().asFile

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    applicationDefaultJvmArgs = listOf("-Dfile.encoding=UTF-8")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.websockets)
    implementation(libs.ktor.server.contentNegotiation)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.statusPages)
    implementation(libs.ktor.server.host)
    implementation(libs.ktor.server.callLogging)
    implementation(libs.ktor.server.swagger)
    implementation(libs.ktor.server.openapi)
    implementation(libs.ktor.serialization.kotlinx.json)

    implementation(libs.logback)

    implementation(libs.kotlin.scripting)

    implementation(project(":libs:common"))
    implementation(project(":libs:language-server"))
    implementation(project(":libs:graph-model"))
    implementation(project(":libs:graph"))

    testImplementation(kotlin("test"))
    testImplementation(libs.mockk)
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}

tasks.build {
    dependsOn(gradle.includedBuild("web").task(":buildReact"))
}

tasks.jar {
    dependsOn(gradle.includedBuild("web").task(":buildReact"))

    from("web/dist") {
        into("web/dist")
    }
}

tasks.shadowJar {
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
}

tasks.installDist {
    doLast {
        copy {
            from(webDistSource)
            into(webDistDestination)
        }
    }
}

tasks.distZip {
    doLast {
        copy {
            from(webDistSource)
            into(webDistDestination)
        }
    }
}

tasks.distTar {
    doLast {
        copy {
            from(webDistSource)
            into(webDistDestination)
        }
    }
}

tasks.shadowJar {
    from(webDistSource) {
        into("web/dist")
    }
}