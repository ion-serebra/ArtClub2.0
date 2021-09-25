package com.oshaev.artclub20.presentation.events

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.TooltipCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.oshaev.artclub20.R
import kotlinx.android.parcel.Parcelize

class EventsScreenTitleView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    private val titleTextView: TextView

    init {
        View.inflate(this.context, R.layout.events_screen_titile_view, this)
        titleTextView = findViewById(R.id.events_screen_title) // TextView заголовка
        }

    fun setData(data: String) {
        titleTextView.text = data
    }
}
