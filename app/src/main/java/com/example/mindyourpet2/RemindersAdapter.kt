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

class RemindersAdapter(private val mList:List<RemindersData>, private val context: Context, private val bundle: Bundle) : RecyclerView.Adapter<RemindersAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent:ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reminder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val RemindersData = mList[position]

        holder.title.text = RemindersData.title
        holder.date.text = RemindersData.date
        holder.frequency.text = RemindersData.frequency

    }

    override fun getItemCount(): Int {
        return mList.size
    }


    inner class ViewHolder(ItemView: View) :RecyclerView.ViewHolder(ItemView){
        val title: TextView = itemView.findViewById(R.id.reminder_title)
        val date: TextView = itemView.findViewById(R.id.reminder_date)
        val frequency: TextView = itemView.findViewById(R.id.reminder_frequency)
    }
}