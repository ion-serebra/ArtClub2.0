package com.oshaev.artclub20.presentation.chats

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.oshaev.artclub20.R

class ChatPreviewView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    var clickListener: ((Data) -> Unit)? = null
    val image: ImageView
    val name: TextView
    val lastMessage: TextView
    val lastMessageTime: TextView

    init {
        View.inflate(this.context, R.layout.view_chat_preview, this)
        image = findViewById(R.id.chat_preview_image)
        name = findViewById(R.id.chat_preview_name)
        lastMessage = findViewById(R.id.chat_preview_last_message)
        lastMessageTime = findViewById(R.id.chat_preview_last_message_time)

    }

    fun setData(data: Data) {
        name.text = data.name
        lastMessage.text = data.lastMessage
        Glide.with(image).load(data.imgUrl).into(image)
        setOnClickListener { clickListener?.invoke(data) }
    }

    data class Data(
        val id: String = "DefaultName",
        val name: String = "DefaultName",
        val lastMessage: String = "DefaultComment",
        val imgUrl: String = "DefaultUrl",
        val key: String = "",
        val timeStamp: Long = 0
    )
}