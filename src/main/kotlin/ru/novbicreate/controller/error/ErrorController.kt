package ru.novbicreate.controller.error

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.novbicreate.database.error.Error
import ru.novbicreate.database.error.ErrorDTO

class ErrorController(private val applicationCall: ApplicationCall) {
    suspend fun recordError() {
        try {
            val receive = applicationCall.receive<ErrorReceive>()
            val errorDTO = receiveToDTO(receive)
            Error.insert(errorDTO)
            applicationCall.respond(HttpStatusCode.OK)
        } catch (e: Exception) {
            applicationCall.respond(HttpStatusCode.BadRequest, e.localizedMessage)
        }
    }
    private fun receiveToDTO(errorReceive: ErrorReceive): ErrorDTO {
        return ErrorDTO(
            errorReceive.type,
            errorReceive.source,
            errorReceive.time,
            errorReceive.description
        )
    }
 }