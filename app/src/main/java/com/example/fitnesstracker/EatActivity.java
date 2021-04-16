package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.List;

import static android.text.InputType.TYPE_CLASS_NUMBER;

public class EatActivity extends AppCompatActivity {
    AppDatabase db;
    DateDao DDao;
    IngredientDao IDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "dateDatabase").allowMainThreadQueries().build();    //TODO remove allowMainThreadQueries, for testing purposeds only, do asynk task
        DDao = db.DayStats();
        IDao = db.IngredientStats();

        Spinner ingredients = findViewById(R.id.ingredientDropdown);

        List<String> ingredientList = IDao.getNames();
        List<IngredientStats> stats = IDao.getAll();
        final String[] ingredientArray = ingredientList.toArray(new String[0]);
        List<DayStats> dstats = DDao.getAll();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ingredientArray);

        ingredients.setAdapter(adapter);

        ingredients.setSelection(0,false);
        ingredients.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selection = parent.getItemAtPosition(pos).toString();
                LinearLayout eatLayout = (LinearLayout)findViewById(R.id.eatLayout);

                LinearLayout ingredientLayout = new LinearLayout(getApplicationContext());     //set up horizontal layout to add to vertical layout
                ingredientLayout.setOrientation(LinearLayout.HORIZONTAL);
                ingredientLayout.setGravity(1);

                TextView ingredientName = new TextView(getApplicationContext());
                ingredientName.setText(selection + ":");
                ingredientLayout.addView(ingredientName);

                EditText inputBox = new EditText((getApplicationContext()));
                inputBox.setInputType(TYPE_CLASS_NUMBER);
                inputBox.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        TextView txt = findViewById(R.id.totalCalories);
                        int calories = Integer.parseInt(txt.getText().toString());
                        TextView input = (TextView) v;
                        calories += Integer.parseInt(input.getText().toString());
                        txt.setText(String.valueOf(calories));
                    }
                });
                ingredientLayout.addView(inputBox);

                eatLayout.addView(ingredientLayout);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void eat (View view){
    /*    TextView txt = findViewById(R.id.eatTxt);

        DateTime dT = DateTime.now();
        int dayOfWeek = dT.getDayOfWeek();
        int today = dT.getMonthOfYear()*1000000 + dT.getDayOfMonth()*10000 + dT.getYear();
        try {
        int caloriesEntered = DDao.getDateCalories(today);
        int weightEntered = DDao.getDateWeight(today);
        DayStats newEntry = new DayStats();
            newEntry.DayOfWeek = dayOfWeek;
            newEntry.DayDate = today;
            newEntry.DayWeight = weightEntered;
            newEntry.DayCalories = Integer.parseInt(txt.getText().toString()) + caloriesEntered;
            newEntry.DayFat = 0;
            newEntry.DayCarbs = 0;
            newEntry.DayProtein = 0;

            if (caloriesEntered == 0 && weightEntered == 0) {  //No entry has been made for today
                DDao.insertWeight(newEntry);
            } else {  //an entry has been made to add weight but not calories
                newEntry.Fdate = DDao.getDateKey(today);

                DDao.updateNutrition(Integer.parseInt(txt.getText().toString()) + caloriesEntered, 0, 0, 0, today);
            }

        txt = findViewById(R.id.eatOutput);
        txt.setText(String.valueOf(DDao.getDateCalories(today)));
        } catch(Exception e){
            System.out.println(e);
        }*/
    }
}
