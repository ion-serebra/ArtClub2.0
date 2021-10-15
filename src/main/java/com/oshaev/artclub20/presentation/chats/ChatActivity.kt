package com.oshaev.artclub20.presentation.chats

import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oshaev.artclub20.R
import com.oshaev.artclub20.application.ArtClubApplication
import com.oshaev.artclub20.repository.ChatMessage
import com.oshaev.artclub20.repository.ChatModel
import kotlinx.coroutines.flow.emptyFlow
import java.util.*

class ChatActivity : AppCompatActivity() {

    lateinit var chat: ChatModel
    val adapter = ChatAdapter()
    lateinit var recycler: RecyclerView
    private lateinit var editText: EditText
    private lateinit var sendButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_chat)
        recycler = findViewById(R.id.chat_recycler)
        editText = findViewById(R.id.chat_edit)
        sendButton = findViewById(R.id.chat_send)

        ArtClubApplication.chatsRepository.chatsListData.subscribe {
            adapter.submitList(getChatById(intent.getStringExtra("chatId")?: "", ArtClubApplication.chatsRepository.chatsListData.value).messages)

        }
        chat = getChatById(intent.getStringExtra("chatId")?: "", ArtClubApplication.chatsRepository.chatsListData.value)
            Log.d("ChatActivity", chat.toString())

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
        adapter.submitList(chat.messages)

        sendButton.setOnClickListener {

            ArtClubApplication.chatsRepository.sendMessage(chat.key,
                ChatMessage(
                    id = UUID.randomUUID().toString(),
                    key = "",
                    text = editText.text.toString(),
                    name = ArtClubApplication.user.fio.substringBefore(delimiter = " "),
                    imgUrl = "",
                    timestamp = System.currentTimeMillis(),
                )
            )
            editText.text = SpannableStringBuilder("")
        }

    }

    fun getChatById(chatId: String, list: List<ChatModel>): ChatModel {
        list.forEach {
            if(it.id == chatId) {
                return it
            }
        }
        return ChatModel(
            id = "",
            key = "",
            name = "",
            messages = emptyList()
        )
    }
}