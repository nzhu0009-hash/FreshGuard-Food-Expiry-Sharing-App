package com.example.foodwasteapplication.reminder

import com.example.foodwasteapplication.food.data.FoodItemEntity

object ExpiryChecker {
    fun getExpiringFoods(foods: List<FoodItemEntity>): List<FoodItemEntity> {
        val twoDaysMillis = 2 * 24 * 60 * 60 * 1000L
        val todayStart = todayStartMillis()
        return foods.filter { food ->
            food.expiryDateMillis in todayStart..(todayStart + twoDaysMillis)
        }
    }

    fun getExpiredFoods(foods: List<FoodItemEntity>): List<FoodItemEntity> {
        val todayStart = todayStartMillis()
        return foods.filter { it.expiryDateMillis < todayStart }
    }

    private fun todayStartMillis(): Long {
        val calendar = java.util.Calendar.getInstance()
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
        calendar.set(java.util.Calendar.MINUTE, 0)
        calendar.set(java.util.Calendar.SECOND, 0)
        calendar.set(java.util.Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
}