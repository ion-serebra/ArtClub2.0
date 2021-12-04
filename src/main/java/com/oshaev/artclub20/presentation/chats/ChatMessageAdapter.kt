package com.oshaev.artclub20.presentation.chats

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.imageview.ShapeableImageView
import com.oshaev.artclub20.R
import com.oshaev.artclub20.application.ArtClubApplication
import com.oshaev.artclub20.repository.ChatMessage
import java.text.SimpleDateFormat

class ChatMessageAdapter(context: Context, resource: Int, messages: List<ChatMessage>):
    ArrayAdapter<ChatMessage?>(context, resource, messages) {

    var listener: OnLongClickListener? = null
    var imageClickListener: OnImageClickListener? = null
    var chatMessages: List<ChatMessage>

    interface OnImageClickListener {
        fun onImageClick(position: Int, view: View?)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val message: ChatMessage = getItem(position) ?: ChatMessage()
        if (convertView == null) {
            convertView = (context as Activity).layoutInflater.inflate(
                R.layout.view_message_layout,
                parent, false
            )
        }
        if (message.name.trim().equals(ArtClubApplication.user.fio.trim())) {
            convertView = (context as Activity).layoutInflater.inflate(
                R.layout.user_message_layout,
                parent, false
            )
        } else {
            convertView = (context as Activity).layoutInflater.inflate(
                R.layout.view_message_layout,
                parent, false
            )
        }
        val messageImg: ShapeableImageView = convertView!!.findViewById(R.id.message_img)
        val nameText = convertView.findViewById<TextView>(R.id.message_name)
        val messageText = convertView.findViewById<TextView>(R.id.message_text)
        val messageTime = convertView.findViewById<TextView>(R.id.message_time)
        val imageMessageCardView: CardView = convertView.findViewById(R.id.imageMessageCardView)
        val formatForDateNow = SimpleDateFormat("hh:mm  ddMMM")
        messageImg.setOnClickListener {
            //Toast.makeText(getContext(), "55", Toast.LENGTH_SHORT).show();
            //int position = getAdapterPosition();
            // установка итерфейса OnClickListener
            if (listener != null) {
                if (position != ListView.NO_ID) {
                    imageClickListener!!.onImageClick(position, imageMessageCardView) // выполняем полученный метод
                }
            }
            if (chatMessages[position].imgUrl != null) {
                val showImageIntent = Intent(context, ShowImageActivity::class.java)
                val bundle: Bundle
                //View messageImage = view.findViewById(R.id.messageImg);
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    context as Activity,
                    imageMessageCardView, "showingImage"
                )
                bundle = options.toBundle()
                showImageIntent.putExtra("path", chatMessages[position].imgUrl)
                if (bundle == null) {
                    context.startActivity(showImageIntent)
                } else {
                    context.startActivity(showImageIntent, bundle)
                }
            }
        }
        val isText = message.imgUrl == null // если == null, значит сообщение с текстом
        if (isText) {
            messageText.visibility = View.VISIBLE
            messageImg.visibility = View.GONE
            messageText.setText(message.text)
            if (message.timestamp != null) {
                //messageTime.text = formatForDateNow.format(message.getDate())
            }
        } else {
            messageText.visibility = View.GONE
            messageImg.visibility = View.VISIBLE
            if (message.timestamp != null) {
                //messageTime.text = formatForDateNow.format(message.getDate())
            }
            //messageImg.setMaxHeight();
            Glide.with(messageImg.context)
                .asBitmap()
                .load(message.imgUrl)
                .into(object: SimpleTarget<Bitmap?>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                        //messageImg.setMaxHeight(resource.getScaledHeight(3));
                        messageImg.setImageBitmap(resource)
                        messageImg.adjustViewBounds = true
                    }
                })
            //Glide.with(messageImg.getContext()).load(message.getImgUrl()).into(messageImg);
            //messageImg.setScaleType(ImageView.ScaleType.CENTER);
        }
        nameText.setText(message.name)
        convertView.setOnLongClickListener(object: OnLongClickListener, View.OnLongClickListener {
            override fun onLongClick(v: View): Boolean {
                if (listener != null) {
                    if (position != ListView.INVALID_POSITION) {
                        listener!!.onLongMessageClick(position) // выполняем полученный метод
                    }
                }
                return false
            }

            override fun onLongMessageClick(position: Int) {
                TODO("Not yet implemented")
            }
        })
        return convertView
    }

    interface OnLongClickListener {

        fun onLongMessageClick(position: Int)
    }

    fun setOnLongMessageClickListener(listener: OnLongClickListener?) {
        this.listener = listener
    }

    override fun remove(`object`: ChatMessage?) {
        super.remove(`object`)
    }

    init {
        chatMessages = messages
    }
}