package com.oshaev.artclub20.repository.chats

import com.google.firebase.database.*
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
                        ?.let { chatMapToModel(it) }?.map{ dtoToData(dataSnapshot.key ?: "", it)}?: emptyList()
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

        chatsReference.addValueEventListener(object: ValueEventListener {
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

    fun sendMessage(chatKey: String, message: ChatMessage) {
        chatsReference.child(chatKey).child("messages").push().setValue(message)
    }

/*    private fun chatsMapToList(map: Map<String, ChatDto>) {
        var list = mutableListOf<ChatDto>()
        map.forEach { list.add(
            ChatDto(
                id = ,
                key = ,
                name = ,
                messages = ,
            )
        ) }*/

    private fun chatMapToModel(map: Map<String, ChatDto>): List<ChatModel>{
        val list = mutableListOf<ChatModel>()
        map.forEach { list.add(
            ChatModel(
            id = it.value.id,
            key = it.key,
            name = it.value.name,
            messages = it.value.messages.values.toList()))
        }
        return list
    }

    private fun dtoToData(key: String, dto: ChatModel) : ChatModel {
        return ChatModel(
            id = dto.id,
            name = dto.name,
            key = dto.key,
            messages = dto.messages.sortedBy { it.timestamp }
        )
    }
    }



