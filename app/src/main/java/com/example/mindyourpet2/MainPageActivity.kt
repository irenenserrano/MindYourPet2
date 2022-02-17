package com.example.mindyourpet2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        val loginButton: Button = findViewById(R.id.login)
        loginButton.setOnClickListener {
            Toast.makeText(this, "login", Toast.LENGTH_SHORT).show()
        }

        val createButton: Button = findViewById(R.id.create_account)
        createButton.setOnClickListener {
            Toast.makeText(this, "test", Toast.LENGTH_SHORT).show()
        }

    }
}