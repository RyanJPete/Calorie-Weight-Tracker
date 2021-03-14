package com.example.fitnesstracker;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;

import java.util.List;

@Dao
public interface DateDao{
    @Query("SELECT * FROM daystats")
    List<DayStats> getAll();

    @Query("SELECT DayStats.weight FROM daystats WHERE DayStats.day = :day")
    public int getDateWeight(int day);

    @Insert
    void insertAll(DayStats... dstat);

    @Insert
    void insertWeight(DayStats dstat);
}
