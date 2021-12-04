package com.oshaev.artclub20.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.oshaev.artclub20.application.ArtClubApplication
import com.oshaev.artclub20.presentation.chats.ChatPreviewView
import com.oshaev.artclub20.repository.chats.ChatsRepositoryImpl
import io.reactivex.rxjava3.core.Observable

class ChatsViewModel: ViewModel() {

    val chatsRepository = ArtClubApplication.chatsRepository

    fun getChatsList(): Observable<List<ChatPreviewView.Data>> {
        return chatsRepository.getUserChatsList(ArtClubApplication.user.id).map {
            it.map {
                ChatPreviewView.Data(
                    id = it.id,
                    key = it.key,
                    name = it.name,
                    lastMessage = it.messages.takeIf { it.isNotEmpty() }?.let { it.last().text} ?: "",
                    imgUrl = "",
                    timeStamp = it.messages.takeIf { it.isNotEmpty() }?.let { it.last().timestamp} ?: 0,
                )
            }
        }
    }
}