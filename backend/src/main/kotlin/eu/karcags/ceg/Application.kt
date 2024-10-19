package eu.karcags.ceg

import com.typesafe.config.ConfigFactory
import eu.karcags.ceg.plugins.*
import eu.karcags.ceg.utils.getIntProperty
import eu.karcags.ceg.utils.getStringProperty
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    embeddedServer(Netty, environment = applicationEngineEnvironment {
        config = HoconApplicationConfig(ConfigFactory.load(getConfigFile(args.getOrNull(0))))

        connector {
            host = getStringProperty(config, "server.host", "localhost")
            port = getIntProperty(config, "server.port", 8080)
        }

        module {
            mainModule()
        }
    }).start(wait = true)
}

fun Application.mainModule() {
    configureSockets()
    configureSerialization()
    configureMonitoring()
    configureOpenAPI()
    configureExceptionHandling()
    configureAPIRouting()
    configureStaticRouting()
    configureCORS()
}

fun getConfigFile(env: String?): String {
    return when (env) {
        "prod" -> "application.prod.conf"
        else -> "application.conf"
    }
}