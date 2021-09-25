package com.oshaev.artclub20.presentation.events

import com.oshaev.artclub20.repository.events.EventData

sealed class EventsRow(
    val id: String,
    val hashCode: Int
)

class EventsTitleRow( // Заголовок
    id: String,
    hashCode: Int,
    val data: String
): EventsRow(id, hashCode)

class EventsRecyclerRow( // Заголовок
    id: String,
    hashCode: Int,
    val data: List<EventData>
): EventsRow(id, hashCode)
