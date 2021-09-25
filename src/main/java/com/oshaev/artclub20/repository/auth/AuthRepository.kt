package com.oshaev.artclub20.repository.auth

import com.oshaev.artclub20.authentication.User
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

interface AuthRepository {
    fun login(login: String, password: String): PublishSubject<Boolean>

    fun getCurrentUser(): PublishSubject<User>

    fun getUserById(uid: String) : BehaviorSubject<User>
}