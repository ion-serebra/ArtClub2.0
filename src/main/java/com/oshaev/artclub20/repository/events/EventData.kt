package com.oshaev.artclub20.repository.events

data class EventData(
    val id: String = "",
    val title: String = "",
    val summary: String = "",
    val paper: String = "",
    val registrationEnabled: Boolean = false,
    val timestamp: Long = 0,
    val imageUrlString: String = "",
    val imageUrlStrings: List<String> = emptyList()
)

