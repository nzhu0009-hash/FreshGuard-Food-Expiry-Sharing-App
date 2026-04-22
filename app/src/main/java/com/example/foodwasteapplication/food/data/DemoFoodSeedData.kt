package com.example.foodwasteapplication.food.data

import java.util.Calendar

object DemoFoodSeedData {
    val foods: List<FoodItemEntity> = listOf(
        FoodItemEntity(
            name = "Milk",
            quantity = 1,
            category = "Dairy",
            expiryDateMillis = dateMillis(2026, Calendar.JUNE, 3),
            note = "Unopened carton"
        ),
        FoodItemEntity(
            name = "Chicken Breast",
            quantity = 2,
            category = "Meat",
            expiryDateMillis = dateMillis(2026, Calendar.JUNE, 6),
            note = "Kept frozen"
        ),
        FoodItemEntity(
            name = "Spinach",
            quantity = 1,
            category = "Vegetable",
            expiryDateMillis = dateMillis(2026, Calendar.MAY, 30),
            note = "Use soon"
        ),
        FoodItemEntity(
            name = "Banana",
            quantity = 6,
            category = "Fruit",
            expiryDateMillis = dateMillis(2026, Calendar.MAY, 31),
            note = "Room temperature"
        ),
        FoodItemEntity(
            name = "Yogurt",
            quantity = 4,
            category = "Dairy",
            expiryDateMillis = dateMillis(2026, Calendar.JUNE, 4),
            note = "Single cups"
        ),
        FoodItemEntity(
            name = "Orange Juice (sealed)",
            quantity = 1,
            category = "Drink",
            expiryDateMillis = dateMillis(2026, Calendar.JUNE, 8),
            note = "Unopened bottle"
        ),
        FoodItemEntity(
            name = "Tomato",
            quantity = 5,
            category = "Vegetable",
            expiryDateMillis = dateMillis(2026, Calendar.JUNE, 2),
            note = "For pasta"
        ),
        FoodItemEntity(
            name = "Bread",
            quantity = 1,
            category = "General",
            expiryDateMillis = dateMillis(2026, Calendar.JUNE, 1),
            note = "Store in fridge"
        ),
        FoodItemEntity(
            name = "Lettuce",
            quantity = 1,
            category = "Vegetable",
            expiryDateMillis = dateMillis(2026, Calendar.APRIL, 21),
            note = "For wraps"
        ),
        FoodItemEntity(
            name = "Tofu",
            quantity = 2,
            category = "General",
            expiryDateMillis = dateMillis(2026, Calendar.APRIL, 21),
            note = "For stir-fry"
        )
    )

    private fun dateMillis(year: Int, month: Int, dayOfMonth: Int): Long {
        return Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }
}
