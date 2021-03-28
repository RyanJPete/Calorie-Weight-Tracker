package com.example.fitnesstracker;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DayStats.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DateDao DayStats();
    public abstract DateDao IngredientStats();
    public abstract DateDao MealStats();
}