package com.example.fitnesstracker;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DayStats.class, IngredientStats.class, MealStats.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DateDao DayStats();
    public abstract IngredientDao IngredientStats();
    public abstract MealDao MealStats();
}