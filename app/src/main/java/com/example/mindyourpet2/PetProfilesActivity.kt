package com.example.mindyourpet2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

    }
}