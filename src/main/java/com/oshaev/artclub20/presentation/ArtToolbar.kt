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

class ArtToolbar
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    private val nameText: TextView
    private val statusText: TextView
    private val levelText: TextView
    private val expText: TextView
    private val levelProgress: ProgressBar
    private val avatar: ImageView

    init {
        View.inflate(this.context, R.layout.view_art_toolbar, this)
        nameText = findViewById(R.id.main_name)
        statusText = findViewById(R.id.main_status)
        levelText = findViewById(R.id.main_level)
        expText = findViewById(R.id.main_exp)
        avatar = findViewById(R.id.main_avatar)

        levelProgress = findViewById(R.id.main_level_progress)

        ArtClubApplication.authRepository.getCurrentUser().subscribe {
            nameText.text = it.fio
            statusText.text = ""
            levelText.text = it.level.toString() + "lvl"
            Glide.with(avatar).load(it.avatarUrl).into(avatar)
        }
    }
}