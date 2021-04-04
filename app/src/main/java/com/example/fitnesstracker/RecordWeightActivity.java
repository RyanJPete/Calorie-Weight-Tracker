package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;



public class RecordWeightActivity extends AppCompatActivity
                                    implements checkDoubleEntry.checkDoubleEntryListener{
    AppDatabase db;
    DateDao DDao;
    List<DayStats> DStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_weight);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "dateDatabase").allowMainThreadQueries().build();    //TODO remove allowMainThreadQueries, for testing purposeds only, do asynk task
        DDao = db.DayStats();

    }

    public void recordWeight(View view){
        Button btn =findViewById(R.id.weightBtn);
        TextView txt = findViewById(R.id.enterWeight);

        DateTime dT = DateTime.now();
        int dayOfWeek = dT.getDayOfWeek();
        int today = dT.getMonthOfYear()*1000000 + dT.getDayOfMonth()*10000 + dT.getYear();
        int weightEntered = DDao.getDateWeight(today);
        DayStats newEntry = new DayStats();
        int caloriesEntered = DDao.getDateCalories(today);
        if(weightEntered == 0 && caloriesEntered == 0) {  //No entry has been made for today
            newEntry.DayOfWeek = dayOfWeek;
            newEntry.DayDate = today;
            newEntry.DayWeight = Integer.parseInt(txt.getText().toString());
            newEntry.DayCalories = 0;
            newEntry.DayFat = 0;
            newEntry.DayCarbs = 0;
            newEntry.DayProtein = 0;
            DDao.insertWeight(newEntry);
        } else if(weightEntered != 0){  //Weight has already been recorded today
            FragmentManager fm = getSupportFragmentManager();
            DialogFragment dub = new checkDoubleEntry();
            dub.show(fm, "VerifyDoubleup");
        } else if(caloriesEntered != 0){  //an entry has been made to add calories but not weight
            DDao.updateWeight(Integer.parseInt(txt.getText().toString()), today);
        } else {

        }
    }

    public void onDialogPositiveClick(DialogFragment dialog){
        TextView txt = findViewById(R.id.enterWeight);
        int weight = Integer.parseInt(txt.getText().toString());
        DateTime dT = DateTime.now();
        int today = dT.getMonthOfYear()*1000000 + dT.getDayOfMonth()*10000 + dT.getYear();
        DDao.updateWeight(weight, today);
    }

    public void addWeight(){

    }

    public void onDialogNegativeClick(DialogFragment dialog){

    }
}

