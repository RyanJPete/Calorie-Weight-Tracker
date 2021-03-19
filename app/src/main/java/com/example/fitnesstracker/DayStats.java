package com.example.fitnesstracker;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DayStats{
    @PrimaryKey(autoGenerate = true)
    public int Fdate;

    @ColumnInfo(name = "day")
    public int DayDate;

    @ColumnInfo(name = "weight")
    public int DayWeight;

    public String printStats(){
        String ret = "Date: " + String.valueOf(DayDate) + " Weight: " + String.valueOf(DayWeight);
        return ret;
    }
}
