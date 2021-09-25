package com.oshaev.artclub20.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.oshaev.artclub20.application.ArtClubApplication
import com.oshaev.artclub20.repository.BookingModel
import com.oshaev.artclub20.repository.schedule.ScheduleRepository
import com.oshaev.artclub20.repository.schedule.ScheduleRepositoryImpl

class AddBookingViewModel: ViewModel() {

    val repository: ScheduleRepository = ArtClubApplication.scheduleRepository

    fun sendBookingToDB(booking: BookingModel) {
        repository.sendBookingToDB(booking)
        Log.d("AddBookingModel", "Booking sended: "+booking.toString())
    }
}