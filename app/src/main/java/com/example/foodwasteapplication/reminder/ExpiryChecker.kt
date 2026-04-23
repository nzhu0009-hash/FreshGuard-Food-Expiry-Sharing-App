package com.example.foodwasteapplication.reminder

import com.example.foodwasteapplication.food.data.FoodItemEntity

object ExpiryChecker {
    fun getExpiringFoods(foods: List<FoodItemEntity>): List<FoodItemEntity> {
        val twoDaysMillis = 2 * 24 * 60 * 60 * 1000L
        val now = System.currentTimeMillis()
        return foods.filter { food ->
            food.expiryDateMillis - now in 0..twoDaysMillis
        }
    }

    fun getExpiredFoods(foods: List<FoodItemEntity>): List<FoodItemEntity> {
        val now = System.currentTimeMillis()
        return foods.filter { it.expiryDateMillis < now }
    }
}