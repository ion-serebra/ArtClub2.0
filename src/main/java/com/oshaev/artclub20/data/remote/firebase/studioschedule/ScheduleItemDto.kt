package com.oshaev.artclub20.data.remote.firebase.studioschedule

data class ScheduleItemDto(
    val id: String = "",
    val cabinet: String = "",
    val timestampStart: Long = 0,
    val timestampFinish: Long = 0,
    val uid: String = "",
)