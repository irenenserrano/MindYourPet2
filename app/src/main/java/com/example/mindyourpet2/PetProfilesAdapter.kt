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

class PetProfilesAdapter(private val mList: List<PetProfilesData>, context: Context, bundle: Bundle) : RecyclerView.Adapter<PetProfilesAdapter.ViewHolder>() {
    private val context = context
    private val bundle = bundle
    //create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflate the pet_profile view, which is used to hold the list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pet_profile, parent, false)
        return ViewHolder(view, context, bundle)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val PetProfilesData = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.textView.text = PetProfilesData.text

        holder.id = PetProfilesData.id

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View, context: Context, bundle: Bundle) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.petProfile)
        lateinit var id: String
        init {
            textView.setOnClickListener {
                Toast.makeText(it.context, "ID is " + id, Toast.LENGTH_LONG).show()
                val intent = Intent(context, RemindersActivity::class.java)
                intent.putExtra("petID", id)
                startActivity(context, intent, bundle)
            }
        }
    }


}