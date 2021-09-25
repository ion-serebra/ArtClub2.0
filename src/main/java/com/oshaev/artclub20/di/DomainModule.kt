package com.oshaev.artclub20.di

import com.oshaev.artclub20.application.ArtClubApplication
import com.oshaev.artclub20.domain.events.EventsInteractor

class DomainModule {

    fun createEventsInteractor():EventsInteractor{
        return EventsInteractor(eventsRepository = ArtClubApplication.eventRepository)
    }

    fun createUsersInteractor(){
        TODO()
    }
}