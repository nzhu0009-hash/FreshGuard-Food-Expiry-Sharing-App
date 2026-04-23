package com.example.foodwasteapplication.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.foodwasteapplication.food.data.FoodItemEntity

object ReminderScheduler {

    private fun getRequestCode(foodId: Long, daysBeforeExpiry: Int): Int {
        return "${foodId}_${daysBeforeExpiry}".hashCode()
    }

    fun scheduleReminder(context: Context, food: FoodItemEntity) {
        Log.d("ReminderScheduler", "✅ scheduleReminder called for: ${food.name}")
        listOf(2, 1).forEach { daysBefore ->
            val reminderTime = food.expiryDateMillis - (daysBefore * 24 * 60 * 60 * 1000L)

            if (reminderTime > System.currentTimeMillis()) {
                val intent = Intent(context, ReminderReceiver::class.java).apply {
                    putExtra("foodName", food.name)
                    putExtra("foodId", food.id)
                    putExtra("daysBeforeExpiry", daysBefore)
                }

                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    getRequestCode(food.id, daysBefore),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (alarmManager.canScheduleExactAlarms()) {
                        alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            reminderTime,
                            pendingIntent
                        )
                    }
                } else {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        reminderTime,
                        pendingIntent
                    )
                }
            }
        }
    }

    fun cancelReminder(context: Context, foodId: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        listOf(2, 1).forEach { daysBefore ->
            val intent = Intent(context, ReminderReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                getRequestCode(foodId, daysBefore),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.cancel(pendingIntent)
        }
    }

    fun scheduleAllReminders(context: Context, foods: List<FoodItemEntity>) {
        foods.forEach { scheduleReminder(context, it) }
    }
}