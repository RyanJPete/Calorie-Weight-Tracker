package com.example.fitnesstracker;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IngredientDao {
    @Query("SELECT * FROM ingredientstats")
    List<IngredientStats> getAll();

    @Query("SELECT * FROM ingredientstats WHERE ingredientstats.`key` =:key")
    IngredientStats getByKey(int key);

    @Query("SELECT name FROM ingredientstats")
    List<String> getNames();

    @Query(("SELECT calories FROM ingredientstats WHERE ingredientstats.name =:name"))
    int getCalories(String name);

    @Query(("SELECT * FROM ingredientstats WHERE ingredientstats.name =:name"))
    List<IngredientStats> getByName(String name);

    @Insert
    void insertAll(IngredientStats... istat);

    @Insert
    void insertIngredient(IngredientStats istat);

    @Update
    public void updateIngredient(IngredientStats istat);
}
