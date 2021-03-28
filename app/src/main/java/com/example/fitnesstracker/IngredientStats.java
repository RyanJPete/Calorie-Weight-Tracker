package com.example.fitnesstracker;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class IngredientStats {
    @PrimaryKey(autoGenerate = true)
    public int key;

    @ColumnInfo(name = "name")
    public int iname;

    @ColumnInfo(name = "fat")
    public int ifat;

    @ColumnInfo(name = "carbs")
    public int icarbs;

    @ColumnInfo(name = "protein")
    public int iprotein;
}
