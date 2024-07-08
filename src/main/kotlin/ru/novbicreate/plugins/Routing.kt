package ru.novbicreate.plugins

import io.ktor.server.application.*
import ru.novbicreate.controller.anomaly.configureAnomalyRouting
import ru.novbicreate.controller.error.configureErrorRouting
import ru.novbicreate.controller.event.configureEventRouting

fun Application.configureRouting() {
    configureErrorRouting()
    configureEventRouting()
    configureAnomalyRouting()
}
