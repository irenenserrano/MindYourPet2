package com.example.mindyourpet2

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddPetActivity : AppCompatActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pet)
        val spinner: Spinner = findViewById(R.id.pet_species)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.species,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        val saveButton: Button = findViewById(R.id.save_button)
        saveButton.setOnClickListener {
            val species = spinner.selectedItem
            val editText = findViewById<EditText>(R.id.pet_name)
            val petName = editText.text.toString()

            val pet = hashMapOf(
                "name" to petName,
                "species" to species
            )

            db.collection("pets").add(pet).addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }.addOnFailureListener { e ->
                Log.d(TAG, "Error adding document", e)
            }
        }
    }

    fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        //item was selected, you can retrieve the selected item using
        parent.getItemAtPosition(pos).toString()
    }
//    override fun onNothingSelected(parent: AdapterView<*>) {
//
//    }
}