package com.oshaev.artclub20.presentation.settings

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.oshaev.artclub20.R
import com.oshaev.artclub20.application.ArtClubApplication
import com.oshaev.artclub20.presentation.login.LoginActivity
import kotlinx.coroutines.delay

class SettingsActivity: AppCompatActivity() {

    private lateinit var logOutButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_settings)
        logOutButton = findViewById(R.id.settigs_log_out)

        logOutButton.setOnClickListener {
            ArtClubApplication.auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}