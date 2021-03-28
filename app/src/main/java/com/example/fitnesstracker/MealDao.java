package com.example.fitnesstracker;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MealDao {
    @Query("SELECT * FROM mealstats")
    List<MealStats> getAll();

    @Insert
    void insertAll(MealStats... mstat);

    @Insert
    void insertIngrerdient(MealStats mstat);

    @Update
    public void updateIngredient(MealStats mstat);
}