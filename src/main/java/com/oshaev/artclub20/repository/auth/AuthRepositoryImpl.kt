package com.oshaev.artclub20.repository.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.oshaev.artclub20.authentication.User
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
        var user = PublishSubject.create<User>()
        usersReference.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.getValue(User::class.java)?.id.equals(auth.uid)) {
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
}