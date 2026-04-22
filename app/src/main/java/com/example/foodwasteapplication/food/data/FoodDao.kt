package com.example.foodwasteapplication.food.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM food_items ORDER BY expiryDateMillis ASC, name COLLATE NOCASE ASC")
    fun observeAllFoods(): Flow<List<FoodItemEntity>>

    @Query("SELECT * FROM food_items WHERE id = :id LIMIT 1")
    suspend fun getFoodById(id: Long): FoodItemEntity?

    @Query("SELECT * FROM food_items WHERE expiryDateMillis BETWEEN :fromDate AND :toDate ORDER BY expiryDateMillis ASC")
    suspend fun getFoodsByExpiryRange(fromDate: Long, toDate: Long): List<FoodItemEntity>

    @Query("SELECT COUNT(*) FROM food_items")
    fun observeFoodCount(): Flow<Int>

    @Insert
    suspend fun insertFood(food: FoodItemEntity): Long

    @Update
    suspend fun updateFood(food: FoodItemEntity)

    @Delete
    suspend fun deleteFood(food: FoodItemEntity)
}
