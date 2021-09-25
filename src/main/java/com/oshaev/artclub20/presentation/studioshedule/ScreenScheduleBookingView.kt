package com.oshaev.artclub20.presentation.studioshedule

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.oshaev.artclub20.R
import com.oshaev.artclub20.application.ArtClubApplication
import com.oshaev.artclub20.data.remote.firebase.studioschedule.ScheduleItemDto
import java.text.SimpleDateFormat

class ScreenScheduleBookingView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    private val timeTextView: TextView
    private val nameTextView: TextView

    init {
        View.inflate(this.context, R.layout.item_schedule_booking_view, this)
        timeTextView = findViewById(R.id.schedule_booking_time_text_view) // TextView заголовка
        nameTextView = findViewById(R.id.schedule_booking_username_text_view) // ImageView иконки со знаком вопроса
    }

    fun setData(data: ScheduleItemDto) {
        timeTextView.text = SimpleDateFormat("HH:mm").format(data.timestampStart).toString()+":"+SimpleDateFormat("HH:mm").format(data.timestampFinish).toString()
        ArtClubApplication.authRepository.getUserById(data.uid).subscribe {
            nameTextView.text = it.fio
        }
    }
    /*    @Parcelize
        data class Data(
            val time: String,
            val name: String,
        ): Parcelable*/
    data class Data(
        val id: String,
        val userName: String,
        val cabinet: String,
        val timeStart: String,
        val timeFinish: String,
    )
}