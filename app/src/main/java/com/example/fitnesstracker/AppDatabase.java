package com.example.fitnesstracker;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.LinkedList;
import java.util.List;

@Database(entities = {DayStats.class, IngredientStats.class, MealStats.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DateDao DayStats();
    public abstract IngredientDao IngredientStats();
    public abstract MealDao MealStats();
}
