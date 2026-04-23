package com.example.foodwasteapplication.food.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_items")
data class FoodItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val ownerEmail: String = "",
    val name: String,
    val quantity: Int,
    val expiryDateMillis: Long,
    val category: String = "General",
    val note: String = "",
    val createdAtMillis: Long = System.currentTimeMillis(),
)
