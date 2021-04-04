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
    public int icalories;

    @ColumnInfo(name = "fat")
    public int ifat;

    @ColumnInfo(name = "carbs")
    public int icarbs;

    @ColumnInfo(name = "protein")
    public int iprotein;
}
