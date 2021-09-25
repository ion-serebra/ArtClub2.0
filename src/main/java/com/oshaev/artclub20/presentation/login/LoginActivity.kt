package com.oshaev.artclub20.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.oshaev.artclub20.MainActivity
import com.oshaev.artclub20.R
import com.oshaev.artclub20.application.ArtClubApplication
import com.oshaev.artclub20.presentation.viewmodels.LoginViewModel

class LoginActivity: AppCompatActivity() {

    private lateinit var loginEdit: EditText
    private lateinit var passwordEdit: EditText
    private lateinit var loginButton: Button
    private lateinit var model: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ArtClubApplication.auth.currentUser!=null) {
            Log.d("LoginActivity", "authorized, uId: "+FirebaseAuth.getInstance().uid)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Log.d("LoginActivity", "not authorized")
        }
        setContentView(R.layout.screen_login)

        model = ViewModelProvider(this).get(LoginViewModel::class.java)

        bindXml()
        loginButton.setOnClickListener {
            if (!loginEdit.text.isNullOrEmpty() && !passwordEdit.text.isNullOrEmpty()) {
                model.login(
                    login = loginEdit.text.toString().trim(),
                    password = passwordEdit.text.toString().trim(),
                ).subscribe {
                    if (it) {
                        model.getCurrentUser()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                        Toast.makeText(this, "Authentication success", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Authentication denied", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun onAuthReceived() {
    }

    fun bindXml() {
        loginEdit = findViewById(R.id.emailEditText)
        passwordEdit = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.login_button)
    }
}