package com.example.foodwasteapplication.food

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodwasteapplication.food.data.DemoFoodSeedData
import com.example.foodwasteapplication.food.data.FoodDatabase
import com.example.foodwasteapplication.food.data.FoodItemEntity
import com.example.foodwasteapplication.food.data.FoodRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FoodViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val repository = FoodRepository(
        FoodDatabase.getInstance(application).foodDao()
    )
    private val preferences = application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    val foods: StateFlow<List<FoodItemEntity>> = repository
        .observeFoods()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val totalFoodCount: StateFlow<Int> = repository
        .observeFoodCount()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0
        )

    init {
        seedDemoFoodsOnce()
    }

    suspend fun findFood(id: Long): FoodItemEntity? = repository.getFoodById(id)

    suspend fun getFoodsExpiringBetween(fromDate: Long, toDate: Long): List<FoodItemEntity> {
        return repository.getFoodsByExpiryRange(fromDate, toDate)
    }

    fun saveFood(food: FoodItemEntity, isEditing: Boolean) {
        viewModelScope.launch {
            if (isEditing) {
                repository.updateFood(food)
            } else {
                repository.insertFood(food.copy(id = 0))
            }
        }
    }

    fun deleteFood(food: FoodItemEntity) {
        viewModelScope.launch {
            repository.deleteFood(food)
        }
    }

    private fun seedDemoFoodsOnce() {
        val alreadySeeded = preferences.getBoolean(KEY_DEMO_SEEDED, false)
        if (alreadySeeded) return

        viewModelScope.launch {
            repository.seedFoods(DemoFoodSeedData.foods)
            preferences.edit().putBoolean(KEY_DEMO_SEEDED, true).apply()
        }
    }

    companion object {
        private const val PREFS_NAME = "freshguard_prefs"
        private const val KEY_DEMO_SEEDED = "demo_food_seeded_v1"
    }
}
