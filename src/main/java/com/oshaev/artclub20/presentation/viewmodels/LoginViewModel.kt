package com.oshaev.artclub20.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.database.snapshot.BooleanNode
import com.oshaev.artclub20.application.ArtClubApplication
import com.oshaev.artclub20.authentication.User
import io.reactivex.rxjava3.subjects.PublishSubject

class LoginViewModel: ViewModel() {

    val repository = ArtClubApplication.authRepository

    fun login(login: String, password: String): PublishSubject<Boolean> {
        return repository.login(login, password)
    }

    fun getCurrentUser(): PublishSubject<User> {
        return repository.getCurrentUser()
    }
}