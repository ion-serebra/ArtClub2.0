package com.oshaev.artclub20.repository.chats

import com.google.firebase.database.*
import com.oshaev.artclub20.application.ArtClubApplication
import com.oshaev.artclub20.repository.ChatDto
import com.oshaev.artclub20.repository.ChatMessage
import com.oshaev.artclub20.repository.ChatModel
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

class ChatsRepositoryImpl(): ChatsRepository {

    public var chatsListData = BehaviorSubject.create<List<ChatModel>>()
    val chatsReference = FirebaseDatabase.getInstance().getReference("chats")

    override fun getUserChatsList(userId: String): BehaviorSubject<List<ChatModel>> {
        var chatsList = listOf<ChatModel>()

        chatsReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val ti = object: GenericTypeIndicator<Map<String, ChatDto>>() {}
                if (dataSnapshot.getValue(ti) != null) {
                    chatsList = dataSnapshot.getValue(ti)!!
                        .takeIf { it.isNotEmpty() }
                        ?.let { chatMapToModel(it) }
                        ?.map { dtoToData(dataSnapshot.key ?: "", it) }
                        ?.filter {
                            ((it.isDialog == true) && (it.member1Key == ArtClubApplication.user.key || it.member2Key == ArtClubApplication.user.key))
                                    || it.isDialog == false
                        } ?: emptyList() // если диалог, проверяем есть ли в списке участников наш текущий юзер
                    chatsListData.onNext(chatsList)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        return chatsListData
    }

    override fun getChat(chatId: String): PublishSubject<ChatDto> {
        var chatModel = ChatDto()
        var chatModelData = PublishSubject.create<ChatDto>()

        chatsReference.child(chatId).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //val ti = object: GenericTypeIndicator<Map<String, ChatModel>>() {}
                var snapshot = dataSnapshot
                if (snapshot.getValue(ChatDto::class.java) != null) {
                    chatModel = dataSnapshot.getValue(ChatDto::class.java)!!
                    chatModelData.onNext(chatModel)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
        return chatModelData
    }

    fun getChatByKey(chatId: String): PublishSubject<List<ChatMessage>> {
        var chatModel = ChatDto()
        var chatModelData = PublishSubject.create<List<ChatMessage>>()
        val messages = mutableListOf<ChatMessage>()

        chatsReference.child(chatId).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //val ti = object: GenericTypeIndicator<Map<String, ChatModel>>() {}
                var snapshot = dataSnapshot
                if (snapshot.getValue(ChatDto::class.java) != null) {
                    chatModel = dataSnapshot.getValue(ChatDto::class.java)!!
                    chatModelData.onNext(chatModel.messages.values.toList().sortedBy { it.timestamp })
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
        return chatModelData
    }

    fun getChatMessages(chatKey: String): PublishSubject<List<ChatMessage>> {
        val messages = mutableListOf<ChatMessage>()
        var chatModelData = PublishSubject.create<List<ChatMessage>>()

        chatsReference.child(chatKey).child("messages").addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                var message: ChatMessage = ChatMessage()
                message = snapshot.getValue(ChatMessage::class.java) ?: ChatMessage()
                message.key = snapshot.key ?: ""
                messages.add(message)
                chatModelData.onNext(messages)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                var message: ChatMessage = ChatMessage()
                messages.removeIf { it.key == snapshot.key }
                message = snapshot.getValue(ChatMessage::class.java) ?: ChatMessage()
                message.key = snapshot.key ?: ""
                chatModelData.onNext(messages)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                var message: ChatMessage = ChatMessage()
                message = snapshot.getValue(ChatMessage::class.java) ?: ChatMessage()
                message.key = snapshot.key ?: ""
                messages.add(message)
                chatModelData.onNext(messages)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return chatModelData
    }

    fun setChat(
        key: String,
        name: String,
        member1Key: String,
        member2Key: String,
        message: String,
        member1Name: String,
        member2Name: String
    ) {
        chatsReference.child(key).child("name").setValue(name)
        chatsReference.child(key).child("member1Key").setValue(member1Key)
        chatsReference.child(key).child("member2Key").setValue(member2Key)
        chatsReference.child(key).child("member1Name").setValue(member1Name)
        chatsReference.child(key).child("member2Name").setValue(member2Name)
        chatsReference.child(key).child("isDialog").setValue(true)

        chatsReference.child(key).child("messages").push().setValue(
            ChatMessage(
                name = ArtClubApplication.user.fio.substringBefore(" "),
                text = message,
                timestamp = System.currentTimeMillis(),
                userKey = ArtClubApplication.user.key,
            )
        )
    }

    fun sendMessage(chatKey: String, message: ChatMessage) {
        chatsReference.child(chatKey).child("messages").push().setValue(message)
    }

    private fun chatMapToModel(map: Map<String, ChatDto>): List<ChatModel> {
        val list = mutableListOf<ChatModel>()
        map.forEach {
            list.add(
                ChatModel(
                    id = it.key,
                    key = it.key,
                    name = it.value.name,
                    messages = it.value.messages.values.toList(),
                    member1Key = it.value.member1Key,
                    member2Key = it.value.member2Key,
                    isDialog = it.value.isDialog
                )
            )
        }
        return list
    }

    private fun dtoToData(key: String, dto: ChatModel): ChatModel {
        var chatName = ""
        if (dto.member1Key == ArtClubApplication.user.key) {
            chatName = dto.member2Name
        } else {
            chatName = dto.member1Name
        }
        if(dto.isDialog) {
            return ChatModel(
                id = dto.key,
                name = dto.member2Name,
                key = dto.key,
                messages = dto.messages.sortedBy { it.timestamp },
                member1Key = dto.member1Key,
                member2Key = dto.member2Key,
                isDialog = dto.isDialog,
                member1Name = dto.member1Name,
                member2Name = dto.member2Name
            )
        } else {
            return ChatModel(
                id = dto.key,
                name = dto.name,
                key = dto.key,
                messages = dto.messages.sortedBy { it.timestamp },
                member1Key = dto.member1Key,
                member2Key = dto.member2Key,
                isDialog = dto.isDialog,
                member1Name = dto.member1Name,
                member2Name = dto.member2Name
            )
        }
    }
}



