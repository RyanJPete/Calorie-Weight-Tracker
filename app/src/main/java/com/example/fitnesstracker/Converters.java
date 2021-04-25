package com.example.fitnesstracker;

import androidx.room.Room;
import androidx.room.TypeConverter;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converters {
    //Turns a list of ingredients into a String of the format ";name,fat,carbs,protein,calories,key;"
    @TypeConverter
    public static String listToString(List<IngredientStats> list){
        String string = "";

        for(int x = 0; x < list.size(); x++){
            IngredientStats ing = list.get(x);
            string += "," + ing.iname + "," + ing.ifat + "," + ing.icarbs + "," + ing.iprotein + "," +
                    ing.icalories + "," + Integer.toString(ing.key) + "," + ";";
        }
   return string;

    }

    @TypeConverter
    public static List<IngredientStats> stringToList(String string){
        List<IngredientStats> list = new LinkedList<IngredientStats>();

        Pattern pattern = Pattern.compile("([a-zA-z]|[0-9]|,)+;");   //TODO clean inputs
        Matcher matcher = pattern.matcher(string);

        while(matcher.find()){
            IngredientStats ing = new IngredientStats();
            String innerString = matcher.group();
            Pattern pattern2 = Pattern.compile("([a-zA-z]|[0-9])+,");
            Matcher matcher2 = pattern2.matcher(innerString);

            matcher2.find();
            String temp = matcher2.group();
            temp = temp.substring(1,temp.length() - 1);
            ing.iname = temp;

            matcher2.find();
            temp = matcher2.group().substring(0,matcher2.group().length() - 1);
            ing.ifat = Integer.parseInt(temp);

            matcher2.find();
            String test = matcher2.group();
            ing.icarbs = Integer.parseInt(matcher2.group().substring(0,matcher2.group().length() - 1));

            matcher2.find();
            test = matcher2.group();
            ing.iprotein = Integer.parseInt(matcher2.group().substring(0,matcher2.group().length() - 1));

            matcher2.find();
            ing.icalories = Integer.parseInt(matcher2.group().substring(0,matcher2.group().length() - 1));

            matcher2.find();
            ing.key = Integer.parseInt(matcher2.group().substring(0,matcher2.group().length() - 1));

            list.add(ing);
        }

        return list;
    }
}
