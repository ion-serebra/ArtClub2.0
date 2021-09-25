package com.oshaev.artclub20.di

import android.view.contentcapture.DataRemovalRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseModule {

    fun createDatabaseInstance():FirebaseDatabase{
        return FirebaseDatabase.getInstance()
    }

    fun createEventsReference(): DatabaseReference {
        return createDatabaseInstance().getReference("cultural event")
    }

    fun createCabinetsReference(): DatabaseReference {
        return createDatabaseInstance().getReference("cabinets")
    }

    fun createUsersReference(): DatabaseReference {
        return createDatabaseInstance().getReference("new_users").child("new_users")
    }
}