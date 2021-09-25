package com.oshaev.artclub20.repository.events

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.subjects.PublishSubject

interface EventsRepository {

    fun getEvents(): PublishSubject<List<EventData>>
}