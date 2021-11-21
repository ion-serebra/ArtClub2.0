package com.oshaev.artclub20.repository.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.oshaev.artclub20.application.ArtClubApplication
import com.oshaev.artclub20.authentication.User
import com.oshaev.artclub20.presentation.profile.Friend
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

class AuthRepositoryImpl(val auth: FirebaseAuth, val usersReference: DatabaseReference): AuthRepository {

    override fun login(login: String, password: String): PublishSubject<Boolean> {
        val isSuccess = PublishSubject.create<Boolean>()
        auth.signInWithEmailAndPassword(login, password).addOnSuccessListener {
            isSuccess.onNext(true)
            Log.d("AuthRepository", auth.currentUser.toString())
        }.addOnCanceledListener {
            isSuccess.onNext(false)
        }.addOnFailureListener {
            isSuccess.onNext(false)
        }
        return isSuccess
    }

    override fun getCurrentUser(): PublishSubject<User> {
        var userSubject = PublishSubject.create<User>()
        Log.d("AuthRepo", "getCurrentUser")

        usersReference.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d("AuthRepo", (snapshot.toString()))

                if (snapshot.getValue(User::class.java)?.id.equals(auth.uid)) {
                    Log.d("AuthRepo", (snapshot.getValue(User::class.java).toString()))
                    var user = snapshot.getValue(User::class.java)
                    user?.key = snapshot.key ?: ""
                    userSubject.onNext(user)
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
        return userSubject
    }

    override fun getUserById(uid: String): BehaviorSubject<User> {
        var user = BehaviorSubject.create<User>()
        usersReference.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.getValue(User::class.java)?.id.equals(uid)) {
                    Log.d("AuthRepo", (snapshot.getValue(User::class.java).toString()))
                    user.onNext(snapshot.getValue(User::class.java))
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
        return user
    }

    override fun getUserByKey(userKey: String): BehaviorSubject<User> {
        var user = BehaviorSubject.create<User>()
        usersReference.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.getValue(User::class.java)?.key.equals(userKey)) {
                    Log.d("AuthRepo", (snapshot.getValue(User::class.java).toString()))
                    user.onNext(snapshot.getValue(User::class.java))
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.getValue(User::class.java)?.key.equals(userKey)) {
                    Log.d("AuthRepo", (snapshot.getValue(User::class.java).toString()))
                    user.onNext(snapshot.getValue(User::class.java))
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return user
    }


    override fun getUserFriends(userKey: String): BehaviorSubject<List<User>> {
        Log.d("AuthRepo", "Начинаю поиск друзей")

        var friendsSubject = BehaviorSubject.create<List<User>>()
        var friendsList = mutableListOf<User>()
        usersReference.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d("AuthRepo", "Перебираю список друзей")
                try {
                    var friend = snapshot.getValue(User::class.java).apply { this?.key = snapshot.key ?: "" }
                    friend?.let {
                        if (ArtClubApplication.user.friends.contains(it.key)) {
                            Log.d("AuthRepo", "Найден друг")
                            friendsList.add(it)
                            friendsSubject.onNext(friendsList)
                        }
                    }
                } catch (tr: Throwable) {
                    Log.e("AuthRepository", "Друг в дб неправильно сохранён, чуть приложение не упало")
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
        return friendsSubject
    }

    override fun updateCurrentUser() {
        Log.d("AuthRepo", "Обновляю пользователя")
        usersReference
            .child(ArtClubApplication.user.key)
            .setValue(ArtClubApplication.user)
    }
}