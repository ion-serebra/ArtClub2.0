package com.oshaev.artclub20.presentation.mybookings

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.oshaev.artclub20.R

class MyBookingKIRView // КИР - Комментарий Инструмент Резидент
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    var clickListener: ((MyBookingKIRView.Data) -> Unit)? = null
    val image: ImageView
    val name: TextView
    val comment: TextView

    init {
        View.inflate(this.context, R.layout.view_my_booking_kir_item, this)
        image = findViewById(R.id.my_booking_kir_image)
        name = findViewById(R.id.my_booking_kir_name)
        comment = findViewById(R.id.my_booking_kir_comment)
    }

    fun setData(data: Data) {
        name.text = data.name
        comment.text = data.comment
    }

    data class Data(
        val id: String = "DefaultName",
        val name: String = "DefaultName",
        val comment: String = "DefaultComment",
        val imgUrl: String = "DefaultUrl",
        val timeStamp: Long = 0
    )
}