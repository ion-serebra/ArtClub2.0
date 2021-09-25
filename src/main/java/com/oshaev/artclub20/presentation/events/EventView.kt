package com.oshaev.artclub20.presentation.events

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.oshaev.artclub20.R
import com.oshaev.artclub20.repository.events.EventData

class EventView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    private var imageView: ImageView

    init {
        View.inflate(context, R.layout.view_event_card, this)
        imageView = findViewById(R.id.event_card_image)
    }

    fun setData(data: EventData) {
        Glide.with(imageView).load(data.imageUrlString).into(imageView)
        Log.d("EventView", data.imageUrlString)
        setOnClickListener { Log.d("EventView", "touched") }

    }

    data class Data(
        val id: String,
        val imgUrl: String,
    )
}