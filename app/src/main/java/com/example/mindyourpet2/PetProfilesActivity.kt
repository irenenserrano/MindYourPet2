package com.example.mindyourpet2

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PetProfilesActivity : AppCompatActivity() {
    val db = Firebase.firestore
    lateinit var id: String
    val uid: String = FirebaseAuth.getInstance().currentUser?.uid ?: "no user logged in"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_profiles)
        displayPets()

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, AddPetActivity::class.java)
            startActivity(intent)
        }

        val logoutButton: Button = findViewById(R.id.logout)
        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, MainPageActivity::class.java)
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

        db.collection("pets"+uid).get().addOnSuccessListener { result ->
            //ArrayList of PetProfilesData
            val data = mutableListOf<PetProfilesData>()
            result.forEach { document -> data.add(PetProfilesData(document.data["name"].toString(), document.id)) }
            //this will pass the arraylist to our adapter
            val adapter = PetProfilesAdapter(data,this, Bundle())
            //setting the adapter with the recyclerview
            recyclerview.adapter = adapter
        }.addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents.", exception)
        }
    }
}