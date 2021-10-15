package com.oshaev.artclub20.repository.chats

import com.oshaev.artclub20.repository.ChatDto
import com.oshaev.artclub20.repository.ChatModel
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

interface ChatsRepository {

    fun getUserChatsList(userId: String): BehaviorSubject<List<ChatModel>>

    fun getChat(chatId: String):PublishSubject<ChatDto>
}