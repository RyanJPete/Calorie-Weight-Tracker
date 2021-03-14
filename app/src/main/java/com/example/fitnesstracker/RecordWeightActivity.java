package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;

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

public class RecordWeightActivity extends AppCompatActivity {
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
        int dInt = dT.getMonthOfYear()*1000000 + dT.getDayOfMonth()*10000 + dT.getYear();

        DayStats newEntry = new DayStats();
        newEntry.DayDate = dInt;
        newEntry.DayWeight = Integer.parseInt(txt.getText().toString());
        DDao.insertWeight(newEntry);
        int hold = DDao.getDateWeight(dInt);
        btn.setText(String.valueOf(hold));

    }
}

