package ru.novbicreate.controller.event

import kotlinx.serialization.Serializable

@Serializable
data class EventReceive(
    val type: String,
    val source: String,
    val time: Long,
    val details: String,
)
