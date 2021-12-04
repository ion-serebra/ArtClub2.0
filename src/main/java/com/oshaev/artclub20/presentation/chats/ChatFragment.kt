package com.oshaev.artclub20.presentation.chats

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oshaev.artclub20.MainActivity
import com.oshaev.artclub20.R
import com.oshaev.artclub20.application.ArtClubApplication
import com.oshaev.artclub20.presentation.profile.ProfileFragment
import com.oshaev.artclub20.repository.ChatMessage
import com.oshaev.artclub20.repository.ChatModel
import java.util.*

class ChatFragment(
    var chatId: String,
    var chatType: String = "",
    var member1Key: String = "",
    var member2Key: String = "",
    var name: String = "",
    var openFromProfile: Boolean = false
): Fragment() {

    lateinit var chat: ChatModel
    lateinit var recycler: RecyclerView
    private lateinit var editText: EditText
    private lateinit var sendButton: ImageView
    private var adapter = MessageAdapter(mutableListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.screen_chat, container, false)
        recycler = rootView.findViewById(R.id.chat_recycler)
        editText = rootView.findViewById(R.id.chat_edit)
        sendButton = rootView.findViewById(R.id.chat_send)

        chat =
            getChatById(chatId, ArtClubApplication.chatsRepository.chatsListData.value)
        Log.d("ChatActivity", chat.toString())

        recycler.layoutManager = LinearLayoutManager(context)
        //adapter.setHasStableIds(true)
        //adapter.submitList(chat.messages)
        if (chatType == "dialog") {
            sendButton.setOnClickListener {

                ArtClubApplication.authRepository.getUserByKey(member1Key.takeIf { it != ArtClubApplication.user.key }
                    ?: member2Key).subscribe {
                    Log.d("ChatFragment", "получен получатель сообщения:" + it.toString())
                    Log.d("ChatFragment", "Сообщение в диалоге:" + editText.text)

                    if (editText.text.isNotBlank()) {
                        ArtClubApplication.chatsRepository.setChat(
                            chatId,
                            name = name,
                            member1Key = member1Key,
                            member2Key = member2Key,
                            message = editText.text.toString(),
                            member1Name = ArtClubApplication.user.fio.substring(
                                0,
                                ArtClubApplication.user.fio.indexOf(" ", ArtClubApplication.user.fio.indexOf(" ") + 1)
                            ),
                            member2Name = it.fio.substring(0, it.fio.indexOf(" ", it.fio.indexOf(" ") + 1)),
                        )
                    }
                }
            }
        } else {
            sendButton.setOnClickListener {
                Log.d("ChatFragment", "Сообщение:" + editText.text)

                if (editText.text.isNotBlank()) {
                    ArtClubApplication.chatsRepository.sendMessage(
                        chat.key,
                        ChatMessage(
                            id = UUID.randomUUID().toString(),
                            key = "",
                            userKey = ArtClubApplication.user.key,
                            text = editText.text.toString(),
                            name = ArtClubApplication.user.fio.substringBefore(delimiter = " "),
                            imgUrl = "",
                            timestamp = System.currentTimeMillis(),
                        )
                    )
                }
            }
        }

        ArtClubApplication.chatsRepository.getChatMessages(chatId).subscribe {
            adapter = MessageAdapter(it.toMutableList())
            recycler.adapter = adapter
            (recycler.layoutManager as LinearLayoutManager).scrollToPosition(it.lastIndex)
            MessageAdapter.nameClickListener = {
                (activity as MainActivity).hideBottomNav()
                parentFragmentManager.beginTransaction().add(R.id.nav_host_fragment_activity_main, ProfileFragment(it))
                    .addToBackStack(null).commit()
            }
        }
        return rootView
    }

    fun getChatById(chatId: String, list: List<ChatModel>): ChatModel {
        list.forEach {
            if (it.id == chatId) {
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

    override fun onDetach() {
        if (openFromProfile == false) {
            (activity as MainActivity).unhideBottomNav()
        }
        super.onDetach()
    }
}