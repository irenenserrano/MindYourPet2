package com.example.mindyourpet2

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PetProfilesActivity : AppCompatActivity() {
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_profiles)
        displayPets()

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, AddPetActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        displayPets()
    }

    private fun displayPets() {
        //getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        //this creates a vertical layout manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        //ArrayList of PetProfilesData
        val data = mutableListOf<PetProfilesData>()

        db.collection("pets").get().addOnSuccessListener { result ->
            for (document in result) {

                val petName = document.data["name"].toString()

                data.add(PetProfilesData(petName))
            }
            //this will pass the arraylist to our adapter
            val adapter = PetProfilesAdapter(data)

            //setting the adapter with the recyclerview
            recyclerview.adapter = adapter
        }.addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents.", exception)
        }

    }

}