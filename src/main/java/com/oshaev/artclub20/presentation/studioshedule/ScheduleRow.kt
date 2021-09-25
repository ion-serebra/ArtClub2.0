package com.oshaev.artclub20.presentation.studioshedule

import com.oshaev.artclub20.data.remote.firebase.studioschedule.ScheduleItemDto

sealed class ScheduleRow(
    val id: String,
    val hashCode: Int
)

class ScheduleDatesRow(
    id: String,
    hashCode: Int,
    val data: ScreenScheduleDatesRecyclerView.Data
): ScheduleRow(id, hashCode)

class ScheduleCabinetsRow(
    id: String,
    hashCode: Int,
    val data: ScreenScheduleCabinetsRecyclerView.Data
): ScheduleRow(id, hashCode)

class ScheduleTitleRow(
    id: String,
    hashCode: Int,
    val data: ScreenScheduleTitleView.Data
): ScheduleRow(id, hashCode)

class ScheduleBookingRow(
    id: String,
    hashCode: Int,
    val data: ScheduleItemDto
): ScheduleRow(id, hashCode)