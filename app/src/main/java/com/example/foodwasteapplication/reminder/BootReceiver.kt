package com.example.foodwasteapplication.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.foodwasteapplication.food.data.FoodDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            CoroutineScope(Dispatchers.IO).launch {
                val foods = FoodDatabase.getInstance(context).foodDao()
                    .getFoodsByExpiryRangeAll(0L, Long.MAX_VALUE)
                ReminderScheduler.scheduleAllReminders(context, foods)
            }
        }
    }
}
