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
        String formattedDate = formatDate(String.valueOf(DayDate));
        String ret = "Date: " + formattedDate + " Weight: " + String.valueOf(DayWeight);
        return ret;
    }

    public String formatDate(String date){
        int dayLen = 1;
        if(date.length() == 8){           //check if day of month is one or two digits
            dayLen = 2;
        }

        String ret = date.substring(0,dayLen) + "-" + date.substring(dayLen, dayLen + 2) + "-" + date.substring(dayLen + 2);
        return ret;
    }
}
