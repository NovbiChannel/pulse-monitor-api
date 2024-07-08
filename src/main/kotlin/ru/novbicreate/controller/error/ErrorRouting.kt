package ru.novbicreate.controller.error

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import ru.novbicreate.controller.Endpoints.POST_ERROR
import ru.novbicreate.controller.Endpoints.WS_ERROR_SUBSCRIBE
import ru.novbicreate.controller.anomaly.AnomaliesController
import ru.novbicreate.utils.Constants.ONE_MINUTE

fun Application.configureErrorRouting() {
    routing {
        post(POST_ERROR) {
            val controller = ErrorController(call)
            controller.recordError()
        }
    }
}