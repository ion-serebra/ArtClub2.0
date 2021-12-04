package com.oshaev.artclub20.repository.auth

import com.oshaev.artclub20.authentication.User
import com.oshaev.artclub20.presentation.profile.Friend
import com.oshaev.artclub20.presentation.profile.FriendsAdapter
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

interface AuthRepository {
    fun login(login: String, password: String): PublishSubject<Boolean>

    fun getCurrentUser(): PublishSubject<User>

    fun getUserById(uid: String) : BehaviorSubject<User>

    fun getUserByKey(userKey: String) : BehaviorSubject<User>

    fun getUserFriends(userKey: String) : BehaviorSubject<List<User>>
    fun updateCurrentUser()
}