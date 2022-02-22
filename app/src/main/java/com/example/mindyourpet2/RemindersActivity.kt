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
import java.sql.Timestamp
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class RemindersActivity : AppCompatActivity() {

    lateinit var petID: String
    val db = Firebase.firestore
    val uid: String = FirebaseAuth.getInstance().currentUser?.uid ?: "no user logged in"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminders)

        petID = intent.getStringExtra("petID").toString()
        db.collection("pets" + uid).document(petID).collection("reminders").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (task.getResult().size() == 0) {
                    addCollectiveReminders()
                    addPetSpecificReminders()
                }
            }
        }

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

        db.collection("pets" + uid).document(petID).collection("reminders").get()
            .addOnSuccessListener { result ->
                val data = mutableListOf<RemindersData>()
                for (document in result) {
                    val title = document.data["title"].toString()
                    val timestamp = document.data["timestamp"] as com.google.firebase.Timestamp
                    val millisec = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                    val sdf = SimpleDateFormat("MM-dd-yyyy hh:mm aa")
                    val netDate = Date(millisec)
                    val date = sdf.format(netDate).toString()
                    val frequency = document.data["frequency"].toString()

                    data.add(RemindersData(title, date, frequency, document.id, petID))
                }
                val adapter = RemindersAdapter(data, this, Bundle())

                recyclerview.adapter = adapter

            }.addOnFailureListener { e ->
                Log.w(TAG, "Error getting documents", e)
            }
    }

    private fun addCollectiveReminders() {
        petID = intent.getStringExtra("petID").toString()
        val feedTitle = "Feed Dinner"
        val calOne = Calendar.getInstance()
        calOne.set(
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
            19,
            0
        )
        val feedTimestamp = Timestamp(calOne.timeInMillis)
        val feedFrequency = "Every Day"
        val feed = hashMapOf(
            "title" to feedTitle,
            "timestamp" to feedTimestamp,
            "frequency" to feedFrequency
        )
        db.collection("pets" + uid).document(petID).collection("reminders").add(feed)

        val checkupTitle = "Visit Vet"
        val calTwo = Calendar.getInstance()
        calTwo.set(
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.DECEMBER),
            1,
            13,
            0
        )
        val checkupTimestamp = Timestamp(calTwo.timeInMillis)
        val checkupFrequency = "Every Year"
        val checkup = hashMapOf(
            "title" to checkupTitle,
            "timestamp" to checkupTimestamp,
            "frequency" to checkupFrequency
        )
        db.collection("pets" + uid).document(petID).collection("reminders").add(checkup)

        val waterTitle = "Check water"
        val calThree = Calendar.getInstance()
        calThree.set(
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
            19,
            0
        )
        val waterTimestamp = Timestamp(calThree.timeInMillis)
        val waterFrequency = "Every Day"
        val water = hashMapOf(
            "title" to waterTitle,
            "timestamp" to waterTimestamp,
            "frequency" to waterFrequency
        )
        db.collection("pets" + uid).document(petID).collection("reminders").add(water)
        displayReminders()
    }

    private fun addPetSpecificReminders(){
        petID = intent.getStringExtra("petID").toString()
        db.collection("pets" + uid).document(petID).get().addOnSuccessListener { document ->
            if (document.data?.get("species") == "Cat") {
                petID = intent.getStringExtra("petID").toString()
                val litterboxTitle = "Clean Litterbox"
                val calOne = Calendar.getInstance()
                calOne.set(
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                    17,
                    0
                )
                val litterboxTimestamp = Timestamp(calOne.timeInMillis)
                val litterboxFrequency = "Every Day"
                val litterbox = hashMapOf(
                    "title" to litterboxTitle,
                    "timestamp" to litterboxTimestamp,
                    "frequency" to litterboxFrequency
                )

                db.collection("pets" + uid).document(petID).collection("reminders").add(litterbox)
                displayReminders()
            } else {
                petID = intent.getStringExtra("petID").toString()
                val morningWalkTitle = "Morning Walk"
                val calOne = Calendar.getInstance()
                calOne.set(
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                    10,
                    0
                )
                val morningWalkTimestamp = Timestamp(calOne.timeInMillis)
                val morningWalkFrequency = "Every Day"
                val morningWalk = hashMapOf(
                    "title" to morningWalkTitle,
                    "timestamp" to morningWalkTimestamp,
                    "frequency" to morningWalkFrequency
                )
                db.collection("pets" + uid).document(petID).collection("reminders").add(morningWalk)

                val eveningWalkTitle = "Evening Walk"
                val calTwo = Calendar.getInstance()
                calTwo.set(
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                    17,
                    0
                )
                val eveningWalkTimestamp = Timestamp(calTwo.timeInMillis)
                val eveningWalkFrequency = "Every Day"
                val eveningWalk = hashMapOf(
                    "title" to eveningWalkTitle,
                    "timestamp" to eveningWalkTimestamp,
                    "frequency" to eveningWalkFrequency
                )
                db.collection("pets" + uid).document(petID).collection("reminders").add(eveningWalk)
                displayReminders()
            }
        }
    }
}