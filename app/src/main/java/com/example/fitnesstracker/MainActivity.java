package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.RoomDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openWeightActivity(View view){
        Intent intent = new Intent(this, RecordWeightActivity.class);
        startActivity(intent);
    }

    public void openStatsActivity(View view){
        Intent intent = new Intent(this, ViewStatsActivity.class);
        startActivity(intent);
    }

    public void openIngredientActivity(View view){
        Intent intent = new Intent(this, AddIngredientActivity.class);
        startActivity(intent);
    }

    public void openMealActivity(View view){
        Intent intent = new Intent(this, AddMealActivity.class);
        startActivity(intent);
    }
}
