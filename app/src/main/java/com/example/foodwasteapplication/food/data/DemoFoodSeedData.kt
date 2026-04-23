package com.example.foodwasteapplication.food.data

import java.util.Calendar

object DemoFoodSeedData {
    val foods: List<FoodItemEntity> = listOf(
        FoodItemEntity(
            name = "Long-life Milk 1L",
            quantity = 1,
            category = "Dairy",
            expiryDateMillis = dateMillis(2026, Calendar.JUNE, 3),
            note = "UHT unopened carton"
        ),
        FoodItemEntity(
            name = "Frozen Chicken Breast Fillets",
            quantity = 2,
            category = "Meat",
            expiryDateMillis = dateMillis(2026, Calendar.JUNE, 6),
            note = "Portioned and frozen"
        ),
        FoodItemEntity(
            name = "Baby Spinach Bag",
            quantity = 1,
            category = "Vegetable",
            expiryDateMillis = dateMillis(2026, Calendar.APRIL, 24),
            note = "Use today for salad"
        ),
        FoodItemEntity(
            name = "Frozen Banana Slices",
            quantity = 6,
            category = "Fruit",
            expiryDateMillis = dateMillis(2026, Calendar.MAY, 31),
            note = "For smoothies"
        ),
        FoodItemEntity(
            name = "Greek Yogurt Cups",
            quantity = 4,
            category = "Dairy",
            expiryDateMillis = dateMillis(2026, Calendar.JUNE, 4),
            note = "Unopened multi-pack"
        ),
        FoodItemEntity(
            name = "Orange Juice",
            quantity = 1,
            category = "Drink",
            expiryDateMillis = dateMillis(2026, Calendar.JUNE, 8),
            note = "Sealed bottle"
        ),
        FoodItemEntity(
            name = "Canned Diced Tomatoes",
            quantity = 5,
            category = "General",
            expiryDateMillis = dateMillis(2026, Calendar.JUNE, 2),
            note = "Pantry stock for pasta sauce"
        ),
        FoodItemEntity(
            name = "Wholemeal Bread Loaf",
            quantity = 1,
            category = "General",
            expiryDateMillis = dateMillis(2026, Calendar.JUNE, 1),
            note = "Sliced and frozen"
        ),
        FoodItemEntity(
            name = "Romaine Lettuce",
            quantity = 1,
            category = "Vegetable",
            expiryDateMillis = dateMillis(2026, Calendar.APRIL, 23),
            note = "Leftover from wraps"
        ),
        FoodItemEntity(
            name = "Firm Tofu Pack",
            quantity = 2,
            category = "Other",
            expiryDateMillis = dateMillis(2026, Calendar.APRIL, 25),
            note = "For stir-fry dinner"
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
