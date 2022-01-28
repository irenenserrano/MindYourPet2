package com.example.mindyourpet2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class PetProfilesAdapter(private val mList: List<PetProfilesData>, private val context: Context, private val bundle: Bundle) : RecyclerView.Adapter<PetProfilesAdapter.ViewHolder>() {

    //create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflate the pet_profile view, which is used to hold the list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pet_profile, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val petProfilesData = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.textView.text = petProfilesData.text

        holder.textView.setOnClickListener {
            val intent = Intent(context, RemindersActivity::class.java)
            intent.putExtra("petID", petProfilesData.id)
            startActivity(context, intent, bundle)
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.petProfile)
    }


}