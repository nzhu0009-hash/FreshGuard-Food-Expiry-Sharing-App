package com.example.foodwasteapplication.food.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FoodItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao

    companion object {
        @Volatile
        private var instance: FoodDatabase? = null

        fun getInstance(context: Context): FoodDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FoodDatabase::class.java,
                    "freshguard_food.db"
                ).build().also { created ->
                    instance = created
                }
            }
        }
    }
}

