package com.oshaev.artclub20.presentation.studioshedule

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.oshaev.artclub20.R

class ScreenScheduleCabinetView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): CardView(context, attrs, defStyleAttr) {

    private val dayText: TextView
    private val root: View
    var clickListener: ((String) -> Unit)? = null

    init {
        root = View.inflate(context, R.layout.item_cabinet, this)
        dayText = root.findViewById(R.id.schedule_cabinet_text)
    }

    fun setData(data: String) {
        dayText.text = data
        this.setOnClickListener { clickListener?.invoke(data) }
    }
}