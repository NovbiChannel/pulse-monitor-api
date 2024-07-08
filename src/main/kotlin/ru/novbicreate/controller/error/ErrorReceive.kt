package ru.novbicreate.controller.error

import kotlinx.serialization.Serializable

@Serializable
data class ErrorReceive (
    val type: String,
    val source: String,
    val time: Long,
    val description: String,
)