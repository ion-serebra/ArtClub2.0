package com.oshaev.artclub20.presentation.chats

import android.util.Log
import android.view.ViewGroup
import ru.sovcomcard.halva.v1.util.DiffUtilHelper
import ru.sovcomcard.halva.v1.util.SimpleListAdapter

class ChatsAdapter: SimpleListAdapter<ChatPreviewView.Data, ChatsAdapter.ViewHolder>(
    DiffUtilHelper(
        comparatorId = { old, new -> old.javaClass == new.javaClass && old.id == new.id },
        comparatorHash = { old, new -> old.hashCode() == new.hashCode() }
    )
) {

    var chatClickListener: ((ChatPreviewView.Data) -> Unit)? = null

    inner class ViewHolder(itemView: ChatPreviewView):
        SimpleListAdapter<ChatPreviewView.Data, ViewHolder>.ViewHolder(itemView) {

        override fun bind(item: ChatPreviewView.Data) {
            itemView as ChatPreviewView
            with(itemView) {
                setData(item)
                //setOnClickListener { dateClickListener?.invoke(item) }
                clickListener = chatClickListener

                Log.d("DatesNewAdapter", item.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsAdapter.ViewHolder {
        return ViewHolder(ChatPreviewView(parent.context))
    }
}