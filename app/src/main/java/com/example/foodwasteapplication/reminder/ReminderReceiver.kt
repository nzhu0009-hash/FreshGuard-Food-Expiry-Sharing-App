package com.example.foodwasteapplication.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.foodwasteapplication.R

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val foodName = intent.getStringExtra("foodName") ?: return
        val foodId = intent.getLongExtra("foodId", 0L)
        val daysBeforeExpiry = intent.getIntExtra("daysBeforeExpiry", 1)

        val message = when (daysBeforeExpiry) {
            2 -> "🟡 $foodName expires in 2 days – plan to use it soon!"
            1 -> "🔴 $foodName expires TOMORROW – use it today!"
            else -> "⚠️ $foodName is expiring soon!"
        }

        showNotification(
            context = context,
            notifId = "${foodId}_${daysBeforeExpiry}".hashCode(),
            title = "FreshGuard Reminder",
            message = message
        )
    }

    private fun showNotification(context: Context, notifId: Int, title: String, message: String) {
        val channelId = "food_expiry_channel"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Food Expiry Reminders",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notifId, notification)
    }
}
