package com.example.mindyourpet2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class RemindersAdapter(private val mList:List<RemindersData>, private val context: Context, private val bundle: Bundle) : RecyclerView.Adapter<RemindersAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent:ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reminder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val remindersData = mList[position]

        holder.title.text = remindersData.title
        holder.date.text = remindersData.date
        holder.frequency.text = remindersData.frequency

        holder.title.setOnClickListener {
            val intent = Intent(context, ReminderActivity::class.java)
            intent.putExtra("reminderID", remindersData.reminderID)
            intent.putExtra("petID", remindersData.petID)
            startActivity(context, intent, bundle)
        }
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