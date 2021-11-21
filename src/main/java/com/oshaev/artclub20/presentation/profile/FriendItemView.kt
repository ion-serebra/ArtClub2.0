package com.oshaev.artclub20.presentation.profile

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.oshaev.artclub20.R

class FriendItemView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    private var avatar: ImageView
    private var name: TextView
    private var status: TextView

    init {
        View.inflate(this.context, R.layout.view_friend_item, this)
        avatar = findViewById(R.id.friend_item_avatar)
        name = findViewById(R.id.friend_item_name)
        status = findViewById(R.id.friend_item_status)
    }

    fun setData(){

    }
}