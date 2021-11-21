package com.oshaev.artclub20.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.oshaev.artclub20.R
import com.oshaev.artclub20.application.ArtClubApplication
import com.oshaev.artclub20.application.makeGone
import com.oshaev.artclub20.authentication.User
import kotlinx.android.synthetic.main.view_art_profile_toolbar.view.*
import kotlin.math.log

class ProfileToolbar
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    lateinit var nameText: TextView
    lateinit var statusText: TextView
    lateinit var levelText: TextView
    lateinit var expText: TextView
    lateinit var levelProgress: ProgressBar
    lateinit var avatar: ImageView
    lateinit var friendsValue: TextView
    lateinit var tasksValue: TextView
    lateinit var eventsValue: TextView

    init {
        View.inflate(this.context, R.layout.view_art_profile_toolbar, this)
        ArtClubApplication.authRepository.getCurrentUser().subscribe {
            nameText = findViewById(R.id.profile_name)
            statusText = findViewById(R.id.profile_status)
            levelText = findViewById(R.id.profile_level)
            expText = findViewById(R.id.profile_exp)
            avatar = findViewById(R.id.profile_avatar)
            friendsValue = findViewById(R.id.profile_friends_value)
            tasksValue = findViewById(R.id.profile_tasks_value)
            eventsValue = findViewById(R.id.profile_events_value)
            levelProgress = findViewById(R.id.profile_progress)

            nameText.text = it.fio
            statusText.text = it.creativityDirection
            levelText.text = it.level.toString() + "lvl"
            friendsValue.text = it.friends.size.toString()
            Glide.with(avatar).load(it.avatarUrl).into(avatar)
        }
    }

    fun setData(user: User) {
        profile_events_title.text = "Сообщения"
        profile_toolbar_logout.makeGone()
        nameText.text = user.fio
        statusText.text = ""
        levelText.text = user.level.toString() + "lvl"
        friendsValue.visibility = INVISIBLE
        if (!ArtClubApplication.user.friends.contains(user.key)) {
            profile_friends_title.text = "Добавить\n в друзья"
            profile_friends_title.setOnClickListener {
                ArtClubApplication.user.friends.add(user.key)
                friendsValue.text = ArtClubApplication.user.friends.size.toString()
                ArtClubApplication.authRepository.updateCurrentUser()
            }
        } else {
            profile_friends_title.text = "Удалить\n из друзей"
            profile_friends_title.setOnClickListener {
                ArtClubApplication.user.friends.remove(user.key)
                friendsValue.text = ArtClubApplication.user.friends.size.toString()
                ArtClubApplication.authRepository.updateCurrentUser()
            }
        }
        Glide.with(avatar).load(user.avatarUrl).into(avatar)
    }
}
