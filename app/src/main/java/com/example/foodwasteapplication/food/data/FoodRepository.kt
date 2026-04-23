package com.example.foodwasteapplication.food.data

import kotlinx.coroutines.flow.Flow

class FoodRepository(
    private val dao: FoodDao,
) {
    fun observeFoods(): Flow<List<FoodItemEntity>> = dao.observeAllFoods()
    fun observeFoodCount(): Flow<Int> = dao.observeFoodCount()

    suspend fun getFoodById(id: Long): FoodItemEntity? = dao.getFoodById(id)
    suspend fun getFoodsByExpiryRange(fromDate: Long, toDate: Long): List<FoodItemEntity> {
        return dao.getFoodsByExpiryRange(fromDate, toDate)
    }

    suspend fun insertFood(food: FoodItemEntity): Long = dao.insertFood(food)

    suspend fun updateFood(food: FoodItemEntity) = dao.updateFood(food)

    suspend fun deleteFood(food: FoodItemEntity) = dao.deleteFood(food)

    suspend fun seedFoods(seedFoods: List<FoodItemEntity>) {
        seedFoods.forEach { food ->
            dao.insertFood(food.copy(id = 0))
        }
    }
}
