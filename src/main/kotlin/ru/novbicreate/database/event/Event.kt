package ru.novbicreate.database.event

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Event: Table() {
    private val _type = varchar("type", 50)
    private val _source = varchar("source", 50)
    private val _time = long("time")
    private val _details = varchar("details", 1500)

    fun insert(eventDTO: EventDTO) {
        transaction {
            Event.insert { event ->
                event[_type] = eventDTO.type
                event[_source] = eventDTO.source
                event[_time] = eventDTO.time
                event[_details] = eventDTO.details
            }
        }
    }
    fun getAllEvents(): List<EventDTO> {
        return transaction {
            Event.selectAll().map { event ->
                EventDTO(
                    event[_type],
                    event[_source],
                    event[_time],
                    event[_details]
                )
            }
        }
    }
}