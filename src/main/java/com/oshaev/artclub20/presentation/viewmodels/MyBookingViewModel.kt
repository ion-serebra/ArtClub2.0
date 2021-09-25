package com.oshaev.artclub20.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.oshaev.artclub20.application.ArtClubApplication
import com.oshaev.artclub20.repository.BookingModel
import com.oshaev.artclub20.repository.CommentModel

class MyBookingViewModel: ViewModel() {

    val repo = ArtClubApplication.scheduleRepository

    fun getUserBookings(): LiveData<List<BookingModel>> {
        return repo.getUserBookings()
    }

    fun sendComment(comment: CommentModel){
        repo.sendComment(comment)
    }

    fun acceptBooking(booking: BookingModel){
        repo.acceptBooking(booking)
    }

    fun removeBooking(booking: BookingModel) {
        repo.removeBooking(booking)
    }


}