package com.example.mindyourpet2

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.Year
import java.time.ZoneId
import java.util.*
import java.util.Calendar.MONTH

class AddReminderActivity : AppCompatActivity() {

    val db = Firebase.firestore
    lateinit var petID: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reminder)

        val spinner: Spinner = findViewById(R.id.reminder_frequency)
        ArrayAdapter.createFromResource(
            this,
            R.array.frequency,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }


        val saveButton: Button = findViewById(R.id.reminder_save_button)
        saveButton.setOnClickListener {
            val editText: EditText = findViewById(R.id.reminder_title)
            val reminderTitle = editText.text.toString()

            val timePicker: TimePicker = findViewById(R.id.reminder_time)
            val datePicker: DatePicker = findViewById(R.id.reminder_date)
            val combinedCal = Calendar.getInstance()
            combinedCal.set(
                datePicker.year,
                datePicker.month,
                datePicker.dayOfMonth,
                timePicker.hour,
                timePicker.minute
            )
            val timestamp = Timestamp(combinedCal.timeInMillis)

            val frequency = spinner.selectedItem
            petID = intent.getStringExtra("petID").toString()

            val reminder = hashMapOf(
                "title" to reminderTitle,
                "timestamp" to timestamp,
                "frequency" to frequency
            )

            db.collection("pets").document(petID).collection("reminders").add(reminder)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }.addOnFailureListener { e ->
                    Log.d(TAG, "Error adding document", e)
                }
        }

    }
}