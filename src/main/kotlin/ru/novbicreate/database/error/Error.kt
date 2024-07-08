package ru.novbicreate.database.error

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Error: Table() {
    private val _type = varchar("type", 50)
    private val _source = varchar("source", 50)
    private val _time = long("time")
    private val _description = varchar("description", 1500)

    fun insert(errorDTO: ErrorDTO) {
        transaction {
            Error.insert { error ->
                error[_type] = errorDTO.type
                error[_source] = errorDTO.source
                error[_time] = errorDTO.time
                error[_description] = errorDTO.description
            }
        }
    }
    fun getAllError(): List<ErrorDTO> {
        val errors = transaction {
            Error.selectAll().map { error ->
                ErrorDTO(
                    error[_type],
                    error[_source],
                    error[_time],
                    error[_description]
                )
            }
        }
        println("Error count: ${errors.size}")
        return errors
    }
}