package com.oshaev.artclub20.presentation.mybookings

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oshaev.artclub20.R
import com.oshaev.artclub20.application.ArtClubApplication
import com.oshaev.artclub20.repository.BookingModel
import com.oshaev.artclub20.repository.CommentModel
import java.text.SimpleDateFormat
import java.util.*

class MyBookingView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    var clickListener: ((BookingModel) -> Unit)? = null
    val userText: TextView
    val cabinet: TextView
    val date: TextView
    val time: TextView
    val acceptButton: TextView
    val removeButton: TextView
    val instruments: RecyclerView
    val members: RecyclerView
    val comments: RecyclerView
    var startCalendar: GregorianCalendar = GregorianCalendar()
    var endCalendar: GregorianCalendar = GregorianCalendar()
    val timeFormat: SimpleDateFormat = SimpleDateFormat("HH:mm")
    val instrumentsAdapter: MyBookingKIRAdapter = MyBookingKIRAdapter()
    val membersAdapter: MyBookingKIRAdapter = MyBookingKIRAdapter()
    val commentsAdapter: MyBookingKIRAdapter = MyBookingKIRAdapter()
    val commentEdit: EditText
    val commentSendButton: ImageView
    // Комментарии
    val commentsList: MutableList<MyBookingKIRView.Data> = mutableListOf()
    var commentsSortedByTime: MutableList<MyBookingKIRView.Data> = mutableListOf()

    var updateAdapter: (() -> Unit)? = null
    var onCommentSend: ((CommentModel) -> Unit)? = null
    var onAccepted: ((BookingModel) -> Unit)? = null
    var onRemove: ((BookingModel) -> Unit)? = null

    init {
        View.inflate(this.context, R.layout.view_my_booking, this)
        layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )

        acceptButton = findViewById(R.id.my_booking_accept)
        removeButton = findViewById(R.id.my_booking_remove)

        userText = findViewById(R.id.my_booking_user_fio)
        cabinet = findViewById(R.id.my_booking_cabinet)
        date = findViewById(R.id.my_booking_date)
        time = findViewById(R.id.my_booking_time)
        instruments = findViewById(R.id.my_booking_instruments_recycler)
        members = findViewById(R.id.my_booking_members_recycler)
        comments = findViewById(R.id.my_booking_comments_recycler)
        instruments.layoutManager = LinearLayoutManager(context)
        comments.layoutManager = LinearLayoutManager(context)
        members.layoutManager = LinearLayoutManager(context)
        instruments.adapter = instrumentsAdapter
        members.adapter = membersAdapter
        comments.adapter = commentsAdapter
        commentEdit = findViewById(R.id.my_booking_response_commnent)
        commentSendButton = findViewById(R.id.my_booking_send_button)
    }

    fun setData(data: BookingModel) {
        if (ArtClubApplication.user.accessLevel == 4 && data.isAccepted == false) {
            acceptButton.visibility = View.VISIBLE
        } else {
            acceptButton.visibility = View.GONE
        }

        setUserFio(data.uid)
        startCalendar.timeInMillis = data.timestampStart
        endCalendar.timeInMillis = data.timestampFinish
        cabinet.text = "Кабинет " + data.cabinet
        date.text = startCalendar.get(Calendar.MONTH).getMonthName() + ", " + startCalendar.get(Calendar.DAY_OF_MONTH)
        time.text = timeFormat.format(startCalendar.time) + ":" + timeFormat.format(endCalendar.time)
        instrumentsAdapter.submitList(data.instruments.map {
            MyBookingKIRView.Data(
                name = it.value
            )
        })
        membersAdapter.submitList(data.members.map {
            MyBookingKIRView.Data(
                name = it.value
            )
        })

        data.comments.forEach {
            var text = it.value.text
            var commentTimestamp = it.value.timestamp
            ArtClubApplication.authRepository.getUserById(it.value.uid).subscribe {
                commentsList.add(
                    MyBookingKIRView.Data(
                        name = it.fio,
                        comment = text,
                        timeStamp = commentTimestamp
                    )
                )
                commentsSortedByTime = commentsList.sortedBy { it.timeStamp }.toMutableList()
                commentsAdapter.submitList(commentsSortedByTime)
            }
            commentsAdapter.notifyDataSetChanged()

        }

        commentSendButton.setOnClickListener {
            val currentCommentCalendar =
                GregorianCalendar().also { it.timeInMillis += 10800000 } // смещение на 3 часа для Москвы

            if (commentEdit.text.isNotEmpty() && ArtClubApplication.user.fio.isNotEmpty()) {
                commentsSortedByTime.add(
                    MyBookingKIRView.Data(
                        name = ArtClubApplication.user.fio,
                        comment = commentEdit.text.toString()
                    )
                )
                commentsAdapter.submitList(commentsSortedByTime)

                updateAdapter?.invoke()
                onCommentSend?.invoke(
                    CommentModel(
                        id = UUID.randomUUID().toString(),
                        text = commentEdit.text.toString(),
                        bookingKey = data.key,
                        uid = ArtClubApplication.auth.uid.toString(),
                        timestamp = currentCommentCalendar.timeInMillis
                    )
                )
                commentEdit.text.clear()
                commentsAdapter.notifyDataSetChanged()
            }
        }

        acceptButton.setOnClickListener {
            data.isAccepted = true
            onAccepted?.invoke(data)
        }

        removeButton.setOnClickListener {
            onRemove?.invoke(data)
            layoutParams.height = 0
            visibility = View.GONE
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        commentsList.clear()
    }

    private fun Int.getMonthName(): String {
        when (this) {
            0 -> return resources.getString(R.string.month_january)
            1 -> return resources.getString(R.string.month_february)
            2 -> return resources.getString(R.string.month_march)
            3 -> return resources.getString(R.string.month_april)
            4 -> return resources.getString(R.string.month_may)
            5 -> return resources.getString(R.string.month_june)
            6 -> return resources.getString(R.string.month_july)
            7 -> return resources.getString(R.string.month_august)
            8 -> return resources.getString(R.string.month_september)
            9 -> return resources.getString(R.string.month_october)
            10 -> return resources.getString(R.string.month_november)
            11 -> return resources.getString(R.string.month_december)
            else -> return ""
        }
    }

    private fun Int.getWeekdayName(): String {
        when (this) {
            1 -> return resources.getString(R.string.sunday)
            2 -> return resources.getString(R.string.monday)
            3 -> return resources.getString(R.string.tuesday)
            4 -> return resources.getString(R.string.wednesday)
            5 -> return resources.getString(R.string.thursday)
            6 -> return resources.getString(R.string.friday)
            7 -> return resources.getString(R.string.saturday)
            else -> return ""
        }
    }

    private fun setUserFio(uid: String){
        ArtClubApplication.authRepository.getUserById(uid).subscribe {
            userText.text = it.fio
        }
    }
}