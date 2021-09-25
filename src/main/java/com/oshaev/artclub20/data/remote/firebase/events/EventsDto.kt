package com.oshaev.artclub20.data.remote.firebase.events

data class EventsDto(
    val title: String?,
    val summary: String?,
    val paper: String?,
    val registrationEnabled: Boolean?,
    val timestamp: Long?,
    val imageUrlString: String?,
    val imageUrlStrings: List<String>?
)