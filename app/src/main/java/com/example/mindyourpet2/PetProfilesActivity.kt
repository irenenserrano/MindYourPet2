package com.example.mindyourpet2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class PetProfilesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_profiles)

        //getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        //this creates a vertical layout manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        //ArrayList of PetProfilesViewModel
        val data = ArrayList<PetProfilesViewModel>()

        for (i in 1..20) {
            data.add(PetProfilesViewModel("Item " + i))
        }

        //this will pass the arraylist to our adapter
        val adapter = PetProfilesAdapter(data)

        //setting the adapter with the recyclerview
        recyclerview.adapter = adapter

        val fab : View = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, AddPetActivity ::class.java)
            startActivity(intent)
        }
    }
}