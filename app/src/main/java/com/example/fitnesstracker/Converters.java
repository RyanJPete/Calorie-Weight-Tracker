package com.example.fitnesstracker;

import androidx.room.TypeConverter;

import java.util.LinkedList;
import java.util.List;

public class Converters {
    @TypeConverter
    public static String listToString(List<IngredientStats> list){
        String string = "";

        return string;
    }

    @TypeConverter
    public static List<IngredientStats> stringToList(String string){
        List<IngredientStats> list = new LinkedList<IngredientStats>();

        return list;
    }
}
