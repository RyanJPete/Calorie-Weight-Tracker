package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class ViewStatsActivity extends AppCompatActivity {
    DateDao DDao;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stats);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "dateDatabase").allowMainThreadQueries().build();    //TODO remove allowMainThreadQueries, for testing purposeds only, do asynk task
        DDao = db.DayStats();

        TextView txt = findViewById(R.id.statstxt);
        List<DayStats> stats = DDao.getAll();
        for(int x = 0; x < stats.size(); x++){
            txt.append(stats.get(x).printStats());
            txt.append("\n");
        }
    }
}
