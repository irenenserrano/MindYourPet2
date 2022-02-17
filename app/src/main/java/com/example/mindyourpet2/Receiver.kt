package com.example.mindyourpet2

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat


class Receiver : Service() {
    lateinit var reminderTitle: String
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        val intent = Intent(this, AddReminderActivity::class.java)
        val pattern = longArrayOf(0, 300, 0)
        val pi = PendingIntent.getActivity(this, 1234, intent, PendingIntent.FLAG_IMMUTABLE)
        reminderTitle = intent.getStringExtra("reminderTitle").toString()

        val builder = NotificationCompat.Builder(this)
            .setContentTitle(reminderTitle)
            .setVibrate(pattern).setAutoCancel(true)

        builder.setContentIntent(pi)
        builder.setDefaults(Notification.DEFAULT_SOUND)
        builder.setAutoCancel(true)
        val notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1234, builder.build())

    }
}