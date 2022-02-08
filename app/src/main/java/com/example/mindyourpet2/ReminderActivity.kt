package com.example.mindyourpet2

import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp
import java.util.*

class ReminderActivity : AppCompatActivity() {
    lateinit var reminderID: String
    lateinit var petID: String
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        reminderID = intent.getStringExtra("reminderID").toString()
        petID = intent.getStringExtra("petID").toString()
        val reminderTitle: TextView = findViewById(R.id.title)
        val reminderDate: TextView = findViewById(R.id.date)
        val reminderFrequency: TextView = findViewById(R.id.frequency)

        db.collection("pets").document(petID).collection("reminders").document(reminderID).get()
            .addOnSuccessListener {
                reminderTitle.setText(it.get("title").toString())
                val timestamp = it.get("timestamp") as com.google.firebase.Timestamp
                val millisec = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                val sdf = SimpleDateFormat("MM-dd-yyyy hh:mm aa")
                val netDate = Date(millisec)
                val date = sdf.format(netDate).toString()
                reminderDate.setText(date)
                reminderFrequency.setText(it.get("frequency").toString())
            }

        val completeTask: Button = findViewById(R.id.complete_button)
        completeTask.setOnClickListener {
            db.collection("pets").document(petID).collection("reminders").document(reminderID).get()
                .addOnSuccessListener { reminder ->
                    val ts = reminder.get("timestamp") as com.google.firebase.Timestamp
                    val millisec = ts.seconds * 1000 + ts.nanoseconds / 1000000
                    val netDate = Date(millisec)
                    val frequency = reminder.get("frequency").toString()
                    val cal: Calendar = Calendar.getInstance()
                    cal.time = netDate

                    when (frequency) {
                        ("Every Day") -> cal.add(Calendar.DATE, 1)
                        ("Every Week") -> cal.add(Calendar.WEEK_OF_MONTH, 1)
                        ("Every Month") -> cal.add(Calendar.MONTH, 1)
                        ("Every Year") -> cal.add(Calendar.YEAR, 1)
                        else -> {
                        }
                    }

                    val sdf = SimpleDateFormat("MM-dd-yyyy hh:mm aa")
                    val date = sdf.format(cal.time).toString()
                    Toast.makeText(this, date, Toast.LENGTH_LONG).show()
                    val time = Timestamp(cal.timeInMillis)
                    val map = mapOf( "timestamp" to time)
                    db.collection("pets").document(petID).collection("reminders")
                        .document(reminderID).update(map)

                    val intent = Intent(this, RemindersActivity::class.java)
                    intent.putExtra("reminderID", reminderID)
                    intent.putExtra("petID", petID)
                    startActivity(intent)
                }
        }

    }

}

