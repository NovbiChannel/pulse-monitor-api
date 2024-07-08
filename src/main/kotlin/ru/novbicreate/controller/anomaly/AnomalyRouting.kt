package ru.novbicreate.controller.anomaly

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import ru.novbicreate.controller.Endpoints.WS_ERROR_SUBSCRIBE
import ru.novbicreate.utils.Constants.ONE_MINUTE
import ru.novbicreate.utils.minutes

fun Application.configureAnomalyRouting() {
    routing {
        webSocket(WS_ERROR_SUBSCRIBE) {
            val controller = AnomaliesController()
            while (isActive) {
                val (anomalies, errorFile) = controller.checkForAnomalies()!!
                send(Frame.Text(anomalies))
                send(Frame.Binary(true, errorFile.readBytes()))
                errorFile.delete()
                delay(30.minutes())
            }
        }
    }
}