package com.example.fitnesstracker;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class MealStats {
    @PrimaryKey(autoGenerate = true)
    public int key;

    @ColumnInfo(name = "name")
    public String mname;

    @ColumnInfo(name = "ingredientList")
    public List<IngredientStats> ingredientList;
}
