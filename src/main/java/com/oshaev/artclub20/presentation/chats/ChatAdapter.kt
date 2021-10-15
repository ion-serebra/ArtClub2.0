package com.oshaev.artclub20.presentation.chats

import android.util.Log
import android.view.ViewGroup
import com.oshaev.artclub20.repository.ChatMessage
import ru.sovcomcard.halva.v1.util.DiffUtilHelper
import ru.sovcomcard.halva.v1.util.SimpleListAdapter

class ChatAdapter: SimpleListAdapter<ChatMessage, ChatAdapter.ViewHolder>(
    DiffUtilHelper(
        comparatorId = { old, new -> old.javaClass == new.javaClass && "old.id "== new.id },
        comparatorHash = { old, new -> old.hashCode() == new.hashCode() }
    )
) {

    var chatClickListener: ((ChatMessage) -> Unit)? = null

    inner class ViewHolder(itemView: ChatMessageView):
        SimpleListAdapter<ChatMessage, ViewHolder>.ViewHolder(itemView) {

        override fun bind(item: ChatMessage) {
            itemView as ChatMessageView
            with(itemView) {
                Log.d("ChatAdapter", item.toString())

                setData(item)
                //setOnClickListener { dateClickListener?.invoke(item) }
                clickListener = chatClickListener

                Log.d("DatesNewAdapter", item.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ViewHolder {
        return ViewHolder(ChatMessageView(parent.context))
    }
}