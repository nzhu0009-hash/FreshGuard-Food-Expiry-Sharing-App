package com.example.foodwasteapplication.food.data

import kotlinx.coroutines.flow.Flow

class FoodRepository(
    private val dao: FoodDao,
) {
    fun observeFoods(ownerEmail: String): Flow<List<FoodItemEntity>> = dao.observeAllFoods(ownerEmail)
    fun observeFoodCount(ownerEmail: String): Flow<Int> = dao.observeFoodCount(ownerEmail)

    suspend fun getFoodById(id: Long, ownerEmail: String): FoodItemEntity? = dao.getFoodById(id, ownerEmail)
    suspend fun getFoodsByExpiryRange(ownerEmail: String, fromDate: Long, toDate: Long): List<FoodItemEntity> {
        return dao.getFoodsByExpiryRange(ownerEmail, fromDate, toDate)
    }

    suspend fun insertFood(food: FoodItemEntity): Long = dao.insertFood(food)

    suspend fun updateFood(food: FoodItemEntity) = dao.updateFood(food)

    suspend fun deleteFood(food: FoodItemEntity) = dao.deleteFood(food)

    suspend fun getFoodCount(ownerEmail: String): Int = dao.getFoodCount(ownerEmail)

    suspend fun seedFoods(seedFoods: List<FoodItemEntity>) {
        seedFoods.forEach { food ->
            dao.insertFood(food.copy(id = 0))
        }
    }
}
