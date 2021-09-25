package com.oshaev.artclub20.presentation.mybookings

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oshaev.artclub20.R
import com.oshaev.artclub20.presentation.viewmodels.MyBookingViewModel

class MyBookingsActivity: AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    val adapter = MyBookingsAdapter()
    lateinit var model: MyBookingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MyBookingsActivity", "Activity Created")

        setContentView(R.layout.screen_my_bookings)

        model = ViewModelProvider(this).get(MyBookingViewModel::class.java)
        recyclerView = findViewById(R.id.my_bookings_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        model.getUserBookings().observe(this, {
            adapter.submitList(it)
            Log.d("MyBookingsActivity", it[0].toString() )
        })
        adapter.acceptedClicked ={
            model.acceptBooking(it)
        }
        adapter.commentSend = {
            model.sendComment(it)
        }
        adapter.removeClicked = {
            model.removeBooking(it)
        }
    }
}