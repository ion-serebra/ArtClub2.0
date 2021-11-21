package com.oshaev.artclub20.repository.schedule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.oshaev.artclub20.application.ArtClubApplication
import com.oshaev.artclub20.data.remote.firebase.studioschedule.ScheduleItemDto
import com.oshaev.artclub20.repository.BookingModel
import com.oshaev.artclub20.repository.CommentModel
import com.oshaev.artclub20.repository.events.EventData
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*

class ScheduleRepositoryImpl(val auth: FirebaseAuth, val realtimeDatabase: FirebaseDatabase): ScheduleRepository {

    val cabinetsReference = realtimeDatabase.getReference("cabinets")
    val bookingsReference = realtimeDatabase.getReference("bookings")

    override fun getCabinetsList(): LiveData<List<String>> {
        var cabinetsList = listOf<String>()
        val cabinetsLiveData = MutableLiveData<List<String>>()

        cabinetsReference.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                //cabinetsList = (snapshot.getValue(HashMap::class.java)).orEmpty().values.toList()
                val ti = object: GenericTypeIndicator<List<String>>() {}
                cabinetsList = snapshot.getValue(ti).orEmpty()
                cabinetsLiveData.value = cabinetsList
                Log.d("ScheduleRepo", cabinetsList.toString())
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return cabinetsLiveData
    }

    override fun getScheduleEntryByCabinetAndDay(
        cabinet: String,
        day: Calendar
    ): PublishSubject<List<ScheduleItemDto>> {
        var scheduleList = listOf<ScheduleItemDto>()
        val schedulePublishSubject = PublishSubject.create<List<ScheduleItemDto>>()

        bookingsReference.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                //if(snapshot.getValue(ScheduleItemDto::class.java)?.cabinet.equals())

                val ti = object: GenericTypeIndicator<Map<String, ScheduleItemDto>>() {}
                Log.d("ScheduleRepository", "Added: " + snapshot.getValue().toString())

                if(snapshot.getValue(ti) != null) {
                    scheduleList = snapshot.getValue(ti)!!.map { it.value }.filter { it.cabinet.equals(cabinet) }
                }

                var dto = snapshot.getValue(ScheduleItemDto::class.java)
                if (dto != null) {
                    if (dto.cabinet.
                        equals(cabinet)) {
                        //scheduleList.add(dto)
                        Log.d("ScheduleRepository", "Added: " + dto.toString())
                    }
                }

                Log.d("ScheduleRepository", "scheduleList: size " + scheduleList.size)
                schedulePublishSubject.onNext(Collections.unmodifiableList(scheduleList))
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        return schedulePublishSubject
    }

    override fun sendBookingToDB(booking: BookingModel) {
        realtimeDatabase.getReference("bookings").child("bookings").push().setValue(booking)
        Log.d("AddBookingModel", "Booking sended: " + booking.toString())
    }

    override fun getUserBookings(): LiveData<List<BookingModel>> {
        var bookingsList = mutableListOf<BookingModel>()

        val bookingsLiveData = MutableLiveData<List<BookingModel>>()
        Log.d("ScheduleRepository", "uId: " + auth.uid)

        realtimeDatabase.getReference("bookings").addChildEventListener(
            object: ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    Log.d("ScheduleRepository", "booking uId: " + snapshot.getValue(BookingModel::class.java)?.uid)

                    if (snapshot.getValue(BookingModel::class.java)?.uid.equals(auth.uid) ||
                        (ArtClubApplication.user.accessLevel == 4&&snapshot.getValue(BookingModel::class.java)?.isAccepted==false)) {
                        var dto = snapshot.getValue(BookingModel::class.java)
                        if (dto != null) {
                            dto.key = snapshot.key.toString()
                            bookingsList.add(dto)
                            bookingsLiveData.value = bookingsList
                            Log.d("ScheduleRepository", "Received Booking: " + dto.toString())
                        }
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    bookingsList.remove(snapshot.getValue(BookingModel::class.java))
                    bookingsLiveData.value = Collections.unmodifiableList(bookingsList)
                    Log.d("ScheduleRepo", "removed")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onCancelled(error: DatabaseError) {}
            })
        return bookingsLiveData
    }

    override fun sendComment(comment: CommentModel) {
        bookingsReference.child(comment.bookingKey).child("comments").push().setValue(comment)
    }

    override fun acceptBooking(booking: BookingModel) {
        bookingsReference.child(booking.key).setValue(booking)
    }

    override fun removeBooking(booking: BookingModel) {
        bookingsReference.child(booking.key).removeValue()
    }
}