package com.example.mindyourpet2

import android.content.ContentValues.TAG
import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.okhttp.internal.http.HttpDate.format
import java.math.BigDecimal
import java.sql.Time
import java.sql.Timestamp
import android.text.format.DateFormat
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import java.text.MessageFormat.format
import java.util.*
import kotlin.collections.ArrayList

class RemindersActivity : AppCompatActivity() {

    lateinit var petID: String
    val db = Firebase.firestore
    val uid: String = FirebaseAuth.getInstance().currentUser?.uid ?: "no user logged in"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminders)

        val fab: View = findViewById(R.id.fab_reminders)
        fab.setOnClickListener {
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
        petID = intent.getStringExtra("petID").toString()

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview_reminders)

        recyclerview.layoutManager = LinearLayoutManager(this)

        db.collection("pets"+uid).document(petID).collection("reminders").get().addOnSuccessListener { result ->
            val data = mutableListOf<RemindersData>()
            for (document in result) {
                val title = document.data["title"].toString()
                val timestamp = document.data["timestamp"] as com.google.firebase.Timestamp
                val millisec = timestamp.seconds * 1000 +timestamp.nanoseconds/1000000
                val sdf = SimpleDateFormat("MM-dd-yyyy hh:mm aa")
                val netDate = Date(millisec)
                val date = sdf.format(netDate).toString()
                val frequency = document.data["frequency"].toString()

                data.add(RemindersData(title, date, frequency, document.id, petID))
            }
            val adapter = RemindersAdapter(data, this, Bundle())

            recyclerview.adapter = adapter

        }.addOnFailureListener {  e->
            Log.w(TAG, "Error getting documents", e)
        }
    }

}