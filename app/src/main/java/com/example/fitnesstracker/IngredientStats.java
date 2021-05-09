package com.example.fitnesstracker;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class IngredientStats {
    @PrimaryKey(autoGenerate = true)
    public int key;

    @ColumnInfo(name = "name")
    public String iname;

    @ColumnInfo(name = "calories")
    public double icalories;

    @ColumnInfo(name = "fat")
    public double ifat;

    @ColumnInfo(name = "carbs")
    public double icarbs;

    @ColumnInfo(name = "protein")
    public double iprotein;

    @ColumnInfo(name = "portionType")
    public String iportion;
}
