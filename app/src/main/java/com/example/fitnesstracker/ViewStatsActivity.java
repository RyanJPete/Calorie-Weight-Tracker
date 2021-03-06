package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.joda.time.DateTime;
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

        printWeeklyStats();
    }

    public void printWeeklyStats(){
        TextView weightTxt = findViewById(R.id.aveWeightTxt);
        TextView calText = findViewById(R.id.aveCaloriesTxt);
        DateTime dT = DateTime.now();
        int today = dT.getMonthOfYear()*1000000 + dT.getDayOfMonth()*10000 + dT.getYear();
        int dayOfWeek = (dT.getDayOfWeek() + 1) % 7;
        int weeklyTotalWeight = 0;
        double weeklyTotalCalories = 0;
        int weightDaysRecorded = 0;
        int calorieDaysRecorded = 0;

        for(int x = 0; x <= dayOfWeek; x++){
            int dayWeight = DDao.getDateWeight(todayMinusX(today, x));
            double dayCal = DDao.getDateCalories(todayMinusX(today, x));
            if(dayWeight > 0){
                weeklyTotalWeight += dayWeight;
                weightDaysRecorded++;
            }
            if(dayCal > 0){
                weeklyTotalCalories += dayCal;
                calorieDaysRecorded++;
            }
        }
        if(weeklyTotalWeight > 0) {
            weightTxt.setText("This Weeks Average Weight is: " + weeklyTotalWeight / weightDaysRecorded);
        } else {
            weightTxt.setText("No weight this week");
        }
        if(weeklyTotalCalories > 0) {
            calText.setText("This week's average calorie count is: " + weeklyTotalCalories / calorieDaysRecorded);
        } else {
            calText.setText("No calories this week");
        }
    }

    public  int todayMinusX(int today, int x){
        int month = today /1000000 ;
        int day = (today / 10000) % 100 ;
        int year = today %10000;


        //calculate subtraction
        for(int y = 0; y < x; y++) {
            if (day == 1) {
                month--;
                if (month <= 0) {
                    year--;
                    month = 12;
                }
                day = getMonthLength(month);
            } else {
                day--;
            }
        }
        int ret = month*1000000 + day*10000 + year;
        return ret;
    }

    public int getMonthLength(int month){
        switch (month){
            case 1: return 31;
            case 2: return 28;
            case 3: return 31;
            case 4: return 30;
            case 5: return 31;
            case 6: return 30;
            case 7: return 31;
            case 8: return 31;
            case 9: return 30;
            case 10: return 31;
            case 11: return 30;
            case 12: return 31;
            default: return 0;
        }
    }

    public void dumpStats(View view){
        TextView txt = findViewById(R.id.statstxt);
        List<DayStats> stats = DDao.getAll();
        for(int x = 0; x < stats.size(); x++){
            txt.append(stats.get(x).printStats());
            txt.append("\n");
        }
    }
}
