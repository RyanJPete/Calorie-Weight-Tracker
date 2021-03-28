package com.example.fitnesstracker;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MealStats {
    @PrimaryKey(autoGenerate = true)
    public int key;

    @ColumnInfo(name = "name")
    public int mname;

    @ColumnInfo(name = "calories")
    public int icalories;

    @ColumnInfo(name = "fat")
    public int mfat;

    @ColumnInfo(name = "carbs")
    public int mcarbs;

    @ColumnInfo(name = "protein")
    public int mprotein;
}
