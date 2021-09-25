package com.oshaev.artclub20.repository.schedule

import androidx.lifecycle.LiveData
import com.oshaev.artclub20.data.remote.firebase.studioschedule.ScheduleItemDto
import com.oshaev.artclub20.presentation.studioshedule.ScreenScheduleBookingView
import com.oshaev.artclub20.repository.BookingModel
import com.oshaev.artclub20.repository.CommentModel
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*

interface ScheduleRepository {

    fun getCabinetsList(): LiveData<List<String>>

    fun getScheduleEntryByCabinetAndDay(cabinet: String, day: Calendar)
            : PublishSubject<List<ScheduleItemDto>>

    fun sendBookingToDB(booking: BookingModel)

    fun getUserBookings(): LiveData<List<BookingModel>>

    fun sendComment(comment: CommentModel)

    fun acceptBooking(booking: BookingModel)

    fun removeBooking(booking: BookingModel)

}