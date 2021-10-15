package com.oshaev.artclub20.presentation.chats

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.oshaev.artclub20.R
import com.oshaev.artclub20.repository.ChatMessage

class ChatMessageView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    var clickListener: ((com.oshaev.artclub20.repository.ChatMessage) -> Unit)? = null
    lateinit var image: ImageView
    lateinit var name: TextView
    lateinit var messageText: TextView
    lateinit var time: TextView

init {
    View.inflate(this.context, R.layout.view_message_layout, this)
    image = findViewById(R.id.message_img)
    name = findViewById(R.id.message_name)
    messageText = findViewById(R.id.message_text)
    time = findViewById(R.id.message_time)
}

    fun setData(data: ChatMessage) {

        Log.d("ChatMessage", data.toString())

        name.text = data.name
        messageText.text = data.text
        Glide.with(image).load(data.imgUrl).into(image)
        setOnClickListener { clickListener?.invoke(data) }
    }
}