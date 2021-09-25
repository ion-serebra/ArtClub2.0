package com.oshaev.artclub20.domain.events

import androidx.lifecycle.LiveData
import com.oshaev.artclub20.repository.events.EventData
import com.oshaev.artclub20.repository.events.EventsRepository
import io.reactivex.rxjava3.subjects.PublishSubject

class EventsInteractor(val eventsRepository: EventsRepository) {

    fun getEvents(): PublishSubject<List<EventData>> {
        return eventsRepository.getEvents()
    }
}