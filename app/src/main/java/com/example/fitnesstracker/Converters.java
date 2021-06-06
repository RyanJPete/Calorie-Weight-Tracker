package com.example.fitnesstracker;

import androidx.room.Room;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converters {
    //Turns list of integers into a string of the format "int;...;int;"
    @TypeConverter
    public static String doubleListToString(List<Double> list){
        String string = "";

        for(int x = 0; x < list.size(); x++){
            Double curDouble = list.get(x);
            string += curDouble + ";";
        }
        return string;
    }

    @TypeConverter
    public static List<Double> stringToDoubleList(String string){
        List<Double> list = new LinkedList<Double>();
        Pattern pattern = Pattern.compile("([0-9]|\\.)+;");
        Matcher matcher = pattern.matcher(string);

        while(matcher.find()){
            String temp = matcher.group();
            temp = temp.substring(0,temp.length() - 1);
            Double newDouble = Double.parseDouble(temp);
            list.add(newDouble);
        }
        return list;
    }

    //Turns a list of ingredients into a String of the format "name,fat,carbs,protein,calories,unit,key;"
    @TypeConverter
    public static String listToString(List<IngredientStats> list){
        String string = "";

        for(int x = 0; x < list.size(); x++){
            IngredientStats ing = list.get(x);
            string += ing.iname + "," + ing.ifat + "," + ing.icarbs + "," + ing.iprotein + "," +
                    ing.icalories + "," + ing.iportion + ',' + Integer.toString(ing.key) + "," + ";";
        }
   return string;

    }

    @TypeConverter
    public static List<IngredientStats> stringToList(String string){
        List<IngredientStats> list = new LinkedList<IngredientStats>();
        Pattern pattern = Pattern.compile("([a-zA-z]|[0-9]|\\s|\\.|,)+;");   //TODO clean inputs
        Matcher matcher = pattern.matcher(string);

        while(matcher.find()){
            IngredientStats ing = new IngredientStats();
            String innerString = matcher.group();
            Pattern pattern2 = Pattern.compile("([a-zA-z]|[0-9]|\\s|\\.|)+,");
            Matcher matcher2 = pattern2.matcher(innerString);

            matcher2.find();
            String temp = matcher2.group();
            temp = temp.substring(0,temp.length() - 1);
            ing.iname = temp;
            matcher2.find();
            temp = matcher2.group().substring(0, matcher2.group().length() - 1);
            ing.ifat = Double.parseDouble(temp);
            matcher2.find();
            String test = matcher2.group();
            ing.icarbs = Double.parseDouble(matcher2.group().substring(0,matcher2.group().length() - 1));

            matcher2.find();
            test = matcher2.group();
            ing.iprotein = Double.parseDouble(matcher2.group().substring(0,matcher2.group().length() - 1));

            matcher2.find();
            ing.icalories = Double.parseDouble(matcher2.group().substring(0,matcher2.group().length() - 1));

            matcher2.find();
            temp = matcher2.group();
            temp = temp.substring(0,temp.length() - 1);
            ing.iportion = temp;

            matcher2.find();
            ing.key = Integer.parseInt(matcher2.group().substring(0,matcher2.group().length() - 1));

            list.add(ing);
        }

        return list;
    }
}
