package ru.novbicreate

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import ru.novbicreate.plugins.configureRouting
import ru.novbicreate.plugins.configureSerialization
import ru.novbicreate.plugins.configureWebSockets

fun main() {
    val dbUrl = System.getenv("DB_URL")
    val dbDriver = System.getenv("DB_DRIVER")
    val dbUser = System.getenv("DB_USER")
    val dbPassword = System.getenv("DB_PASSWORD")

    Database.connect(dbUrl, driver = dbDriver, user = dbUser, password = dbPassword)
    embeddedServer(Netty, port = 8081, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureWebSockets()
    configureSerialization()
    configureRouting()
}
