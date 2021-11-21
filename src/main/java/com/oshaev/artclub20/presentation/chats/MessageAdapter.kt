package com.oshaev.artclub20.presentation.chats

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.oshaev.artclub20.R
import com.oshaev.artclub20.application.ArtClubApplication
import com.oshaev.artclub20.repository.ChatMessage
import java.text.SimpleDateFormat
import java.util.*

class MessageAdapter(var messagesList: MutableList<ChatMessage>): RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    companion object {

        var nameClickListener: ((userKey: String) -> Unit)? = null
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_user_message, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: MessageAdapter.ViewHolder, position: Int) {
        holder.bindItems(messagesList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return messagesList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        lateinit var name: TextView
        lateinit var text: TextView
        lateinit var time: TextView
        lateinit var layout: LinearLayout
        lateinit var mainLayout: LinearLayout

        fun bindItems(message: ChatMessage) {
            name = itemView.findViewById(R.id.message_name)
            text = itemView.findViewById(R.id.message_text)
            time = itemView.findViewById(R.id.message_time)
            layout = itemView.findViewById(R.id.message_layout)
            mainLayout = itemView.findViewById(R.id.message_main_layout)

            if (ArtClubApplication.user.key == message.userKey) {
                layout.layoutParams = LinearLayout
                    .LayoutParams(
                        itemView.resources.getDimension(R.dimen.message_width).toInt(),
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    .apply { gravity = Gravity.END }
                layout.background =
                    ContextCompat.getDrawable(ArtClubApplication.appContext, R.drawable.user_message_background_outline)
                text.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                name.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                time.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
            } else {
                layout.layoutParams = LinearLayout
                    .LayoutParams(
                        itemView.resources.getDimension(R.dimen.message_width).toInt(),
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    .apply { gravity = Gravity.START }
                layout.background =
                    ContextCompat.getDrawable(ArtClubApplication.appContext, R.drawable.bg_message_outline)
                text.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                name.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                time.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
            }
            var calendar = GregorianCalendar().apply { this.timeInMillis = message.timestamp }
            time.text = SimpleDateFormat().format(calendar.time)
            text.text = message.text
            name.text = message.name
            if (message.userKey != "") {
                name.setOnClickListener { MessageAdapter.nameClickListener?.invoke(message.userKey) }
            }
        }
    }
}