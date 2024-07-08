package ru.novbicreate.controller.anomaly

import ru.novbicreate.database.error.Error
import ru.novbicreate.database.error.ErrorDTO
import ru.novbicreate.utils.Constants.ONE_DAY
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class AnomaliesController {
    companion object {
        const val TRIGGER_SIZE = 10
    }
    fun checkForAnomalies(): Pair<String, File>? {
        return try {
            val oneDayAgo = System.currentTimeMillis() - ONE_DAY
            val errors = Error.getAllError().filter { it.time > oneDayAgo }
            val anomaly = errors.groupBy { it.source to it.description }
                .filterValues { it.size > TRIGGER_SIZE }
                .map { (key, errors) -> buildInformationMessage(key, errors) }
                .joinToString(separator = "\n\n")
            val errorFile = createErrorFile(errors)
            anomaly to errorFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    private fun createErrorFile(errors: List<ErrorDTO>): File {
        val file = File("${System.currentTimeMillis()}.txt")
        file.writeText(errors.joinToString("\n"))
        return file
    }
    private fun buildInformationMessage(key: Pair<String, String>, errors: List<ErrorDTO>): String {
        val initiator = key.first + " (${errors.first().type})"
        val description = key.second
        val count = errors.size.toString()
        val dateTime = convertTimestampToDateTime(errors.maxOf { it.time })
        val message = "Частые проблемы, влияющие на стабильность работы: " +
                "\nИнициатор: $initiator; " +
                "\nОписание: $description; " +
                "\nКоличество: $count шт. в течении дня." +
                "\n$dateTime"
        return message
    }
    private fun convertTimestampToDateTime(timestamp: Long): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("dd.MM.yyyy: HH:mm", Locale.getDefault())
        return format.format(date)
    }
}