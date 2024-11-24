package eu.karcags.ceg

import com.typesafe.config.ConfigFactory
import eu.karcags.ceg.plugins.*
import eu.karcags.ceg.utils.getBooleanProperty
import eu.karcags.ceg.utils.getIntProperty
import eu.karcags.ceg.utils.getStringProperty
import io.ktor.network.tls.certificates.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.io.File

fun main(args: Array<String>) {
    embeddedServer(Netty, environment = applicationEngineEnvironment {
        config = HoconApplicationConfig(ConfigFactory.load(getConfigFile(args.getOrNull(0))))

        connector {
            host = getStringProperty(config, "server.host", "localhost")
            port = getIntProperty(config, "server.port", 8080)
        }

        if (getBooleanProperty(config, "ssl.use", false)) {
            val keyStoreFile = File(getStringProperty(config, "ssl.keyStore", "keystore.jks"))
            val alias = getStringProperty(config, "ssl.keyAlias", "alias")
            val privateKey = getStringProperty(config, "ssl.privateKeyPassword", "privateKey")
            val key = getStringProperty(config, "ssl.keyStorePassword", "key")
            val serverKeyStore = buildKeyStore {
                certificate(alias) {
                    password = privateKey
                    domains = listOf("127.0.0.1", "0.0.0.0", "localhost")
                }
            }
            serverKeyStore.saveToFile(keyStoreFile, key)

            sslConnector(
                keyStore = serverKeyStore,
                keyAlias = alias,
                keyStorePassword = { key.toCharArray() },
                privateKeyPassword = { privateKey.toCharArray() },
            ) {
                keyStorePath = keyStoreFile
                port = getIntProperty(config, "ssl.port", 8081)
            }
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