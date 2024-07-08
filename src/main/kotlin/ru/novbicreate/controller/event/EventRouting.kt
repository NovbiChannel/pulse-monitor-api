package ru.novbicreate.controller.event

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.novbicreate.controller.Endpoints.GET_ALL_EVENTS
import ru.novbicreate.controller.Endpoints.POST_EVENT

fun Application.configureEventRouting() {
    routing {
        post(POST_EVENT) {
            val controller = EventController(call)
            controller.recordEvent()
        }
        get(GET_ALL_EVENTS) {
            val controller = EventController(call)
            controller.getTopEvent()
        }
    }
}