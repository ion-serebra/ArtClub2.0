package com.oshaev.artclub20.repository.events

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*

class EventRepositoryImpl(val usersReference:DatabaseReference): EventsRepository {

    private val database: FirebaseDatabase
    private val eventsReference: DatabaseReference
    lateinit var immutableList:List<EventData>
    private var prevChildName: String? = ""

    init {
        database = FirebaseDatabase.getInstance()
        eventsReference = database.getReference("cultural event")
    }

    override fun getEvents(): PublishSubject<List<EventData>> {
        var eventsList = listOf<EventData>()
        var eventsListData = PublishSubject.create<List<EventData>>()

        eventsReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    Log.d("EventRepoValue", postSnapshot.toString())
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })


        eventsReference.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if(previousChildName!=prevChildName)
                {
                    if (snapshot != null) {
                        // var eventData = snapshot.getValue(EventData::class.java)!!

                        val ti = object: GenericTypeIndicator<Map<String, EventData>>() {}
                        eventsList = snapshot.getValue(ti)!!.map{ it.value }.sortedBy { it.timestamp }
                        eventsListData.onNext(Collections.unmodifiableList(eventsList))

                        //Log.d("EventRepo", eventData.title)
                        Log.d("EventRepo", eventsList.size.toString())
                        prevChildName = previousChildName
                    }
                }
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
        return eventsListData
    }

  /*  override fun getEvents(): LiveData<List<EventData>> {
        getEventsFromServer()
        return eventsListData
    }*/
}