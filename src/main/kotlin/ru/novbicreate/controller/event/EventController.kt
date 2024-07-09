package ru.novbicreate.controller.event

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.novbicreate.database.event.Event
import ru.novbicreate.database.event.EventDTO

class EventController(private val applicationCall: ApplicationCall) {
    suspend fun recordEvent() {
        try {
            val receive = applicationCall.receive<EventReceive>()
            val eventDTO = receiveToDTO(receive)
            Event.insert(eventDTO)
            applicationCall.respond(HttpStatusCode.OK)
        } catch (e: Exception) {
            applicationCall.respond(HttpStatusCode.BadRequest, e.localizedMessage)
        }
    }
    suspend fun getTopEvent() {
        try {
            val eventsDto = Event.getAllEvents()
            val groupedEvents = eventsDto.groupingBy { it.details }.eachCount()
            val combinedEvents = groupedEvents.map { "${it.key} - ${it.value}" }
            val sortedEvents = combinedEvents.sortedByDescending { it.split(" - ")[1].toInt() }
            applicationCall.respond(sortedEvents)
        } catch (e: Exception) {
            applicationCall.respond(HttpStatusCode.BadRequest, e.localizedMessage)
        }
    }
    private fun receiveToDTO(eventReceive: EventReceive): EventDTO {
        return EventDTO(
            eventReceive.type,
            eventReceive.source,
            eventReceive.time,
            eventReceive.details
        )
    }
}