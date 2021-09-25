package com.oshaev.artclub20.application

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.oshaev.artclub20.authentication.User
import com.oshaev.artclub20.di.FirebaseModule
import com.oshaev.artclub20.repository.auth.AuthRepository
import com.oshaev.artclub20.repository.auth.AuthRepositoryImpl
import com.oshaev.artclub20.repository.events.EventRepositoryImpl
import com.oshaev.artclub20.repository.schedule.ScheduleRepository
import com.oshaev.artclub20.repository.schedule.ScheduleRepositoryImpl

class ArtClubApplication: Application() {

    companion object {

        lateinit var eventRepository: EventRepositoryImpl
        lateinit var appContext: Context
        lateinit var scheduleRepository: ScheduleRepository
        lateinit var authRepository: AuthRepository
        val networkModule: FirebaseModule = FirebaseModule()
        lateinit var auth: FirebaseAuth
        lateinit var user: User
        //val realtimeDatabase = FirebaseDatabase.getInstance()
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        auth = FirebaseAuth.getInstance()
        scheduleRepository = ScheduleRepositoryImpl(FirebaseAuth.getInstance(), networkModule.createDatabaseInstance())
        eventRepository = EventRepositoryImpl(networkModule.createEventsReference())
        authRepository = AuthRepositoryImpl(FirebaseAuth.getInstance(), networkModule.createUsersReference())
        authRepository.getCurrentUser().subscribe {
            user = it
            Log.d("ArtClubApp", "current user" + user.toString())
        }
    }
}