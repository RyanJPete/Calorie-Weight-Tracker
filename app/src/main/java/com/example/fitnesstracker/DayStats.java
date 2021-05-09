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

    @ColumnInfo(name= "DayOfWeek")
    public int DayOfWeek;

    @ColumnInfo(name= "calories")
    public Double DayCalories;

    @ColumnInfo(name= "fat")
    public Double DayFat;

    @ColumnInfo(name= "carbs")
    public Double DayCarbs;

    @ColumnInfo(name= "protein")
    public Double DayProtein;

    public String printStats(){
        String formattedDate = formatDate(String.valueOf(DayDate));
        String ret = "Date: " + formattedDate + ", Weight: " + String.valueOf(DayWeight) +
                ", Calories: " + String.valueOf(DayCalories) + ", Fat: " + String.valueOf(DayFat) +
                ", Carbs: " + String.valueOf(DayCarbs) + ", Protein: " + String.valueOf(DayProtein);
        return ret;
    }

    public String formatDate(String date){
        int dayLen = 1;
        if(date.length() == 8){           //check if day of month is one or two digits
            dayLen = 2;
        }

        String ret = date.substring(0,dayLen) + "-" + date.substring(dayLen, dayLen + 2) + "-" +
                date.substring(dayLen + 2 ) + " - " + toDayString(DayOfWeek);
        return ret;
    }

    public String toDayString(int dayInt){
        switch(dayInt){
            case 1: return "Monday";
            case 2: return "Tuesday";
            case 3: return "Wednesday";
            case 4: return "Thursday";
            case 5: return "Friday";
            case 6: return "Saturday";
            case 7: return "Sunday";
            default: return "//error: invalid day of week - " + String.valueOf(dayInt) + "//";
        }
    }
}
