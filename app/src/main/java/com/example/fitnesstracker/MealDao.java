package com.example.fitnesstracker;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MealDao {
    @Query("DELETE FROM mealstats")
    void clearMeals();

    @Query("SELECT * FROM mealstats")
    List<MealStats> getAll();

    @Query("SELECT name FROM mealstats")
    List<String> getNames();

    @Query("SELECT * FROM mealstats WHERE mealstats.name = :name")
    MealStats getByName(String name);

    @Insert
    void insertAll(MealStats... mstat);

    @Insert
    void insertMeal(MealStats mstat);

    @Update
    public void updateMeal(MealStats mstat);
}