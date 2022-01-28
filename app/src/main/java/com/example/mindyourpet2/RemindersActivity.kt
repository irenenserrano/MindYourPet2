package com.example.mindyourpet2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RemindersActivity : AppCompatActivity() {

    lateinit var petID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminders)
        displayReminders()

        petID = intent.getStringExtra("petID").toString()

        val fab: View = findViewById(R.id.fab_reminders)
        fab.setOnClickListener{
            val intent = Intent(this, AddReminderActivity::class.java)
            intent.putExtra("petID", petID)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        displayReminders()
    }

    private fun displayReminders() {
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview_reminders)

        recyclerview.layoutManager = LinearLayoutManager(this)

        val data = ArrayList<RemindersData>()

        val adapter = RemindersAdapter(data)

        recyclerview.adapter = adapter
    }
}