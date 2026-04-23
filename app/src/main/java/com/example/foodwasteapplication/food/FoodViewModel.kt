package com.example.foodwasteapplication.food

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodwasteapplication.auth.MockAuthStore
import com.example.foodwasteapplication.food.data.DemoFoodSeedData
import com.example.foodwasteapplication.food.data.FoodDatabase
import com.example.foodwasteapplication.food.data.FoodItemEntity
import com.example.foodwasteapplication.food.data.FoodRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.foodwasteapplication.reminder.ReminderScheduler

class FoodViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val repository = FoodRepository(
        FoodDatabase.getInstance(application).foodDao()
    )
    private val activeUserEmail = MockAuthStore.currentUser
        ?.email
        ?.trim()
        ?.lowercase()
        .orEmpty()
    private val isDemoAccount = activeUserEmail == DEMO_EMAIL

    val foods: StateFlow<List<FoodItemEntity>> = repository
        .observeFoods(activeUserEmail)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val totalFoodCount: StateFlow<Int> = repository
        .observeFoodCount(activeUserEmail)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0
        )

    init {
        seedDemoFoodsOnce()
    }

    suspend fun findFood(id: Long): FoodItemEntity? = repository.getFoodById(id, activeUserEmail)

    suspend fun getFoodsExpiringBetween(fromDate: Long, toDate: Long): List<FoodItemEntity> {
        return repository.getFoodsByExpiryRange(activeUserEmail, fromDate, toDate)
    }

    fun saveFood(food: FoodItemEntity, isEditing: Boolean) {
        viewModelScope.launch {
            val scopedFood = food.copy(ownerEmail = activeUserEmail)
            if (isEditing) {
                repository.updateFood(scopedFood)
                ReminderScheduler.cancelReminder(getApplication(), scopedFood.id)
                ReminderScheduler.scheduleReminder(getApplication(), scopedFood)
            } else {
                val newId = repository.insertFood(scopedFood.copy(id = 0))
                val newFood = scopedFood.copy(id = newId)
                ReminderScheduler.scheduleReminder(getApplication(), newFood)
            }
        }
    }

    fun deleteFood(food: FoodItemEntity) {
        viewModelScope.launch {
            repository.deleteFood(food)
            ReminderScheduler.cancelReminder(getApplication(), food.id)
        }
    }

    private fun seedDemoFoodsOnce() {
        if (!isDemoAccount) return

        viewModelScope.launch {
            val currentCount = repository.getFoodCount(activeUserEmail)
            if (currentCount > 0) return@launch
            repository.seedFoods(
                DemoFoodSeedData.foods.map { it.copy(ownerEmail = activeUserEmail) }
            )
        }
    }

    companion object {
        private const val DEMO_EMAIL = "user@test.com"
    }
}
