package com.example.fitnesstracker;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DateDao{
    @Query("SELECT * FROM daystats")
    List<DayStats> getAll();

    @Query("SELECT DayStats.weight FROM daystats WHERE DayStats.day = :day")
    public int getDateWeight(int day);

    @Query("SELECT DayStats.calories FROM daystats WHERE DayStats.day = :day")
    public int getDateCalories(int day);

    @Query("SELECT DayStats.Fdate FROM DayStats WHERE DayStats.day = :day")
    public int getDateKey(int day);

    @Query("UPDATE DayStats SET weight = :newWeight WHERE DayStats.day = :day")
    public int updateWeight(int newWeight, int day);

    @Query("UPDATE DayStats SET calories= :newCalories, fat= :newFat, carbs= :newCarbs, " +
            "protein= :newProtein WHERE DayStats.day = :day")
    public int updateNutrition(int newCalories, int newFat, int newCarbs, int newProtein, int day);

    @Insert
    void insertAll(DayStats... dstat);

    @Insert
    void insertWeight(DayStats dstat);

    @Update
    public void updateDay(DayStats dstat);
}
