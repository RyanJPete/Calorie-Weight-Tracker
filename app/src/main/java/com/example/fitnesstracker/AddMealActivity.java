package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import static android.text.InputType.TYPE_CLASS_NUMBER;

public class AddMealActivity extends AppCompatActivity {
    AppDatabase db;
    DateDao DDao;
    IngredientDao IDao;
    MealDao MDao;
    int numIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "dateDatabase").allowMainThreadQueries().build();

        DDao = db.DayStats();
        IDao = db.IngredientStats();
        MDao = db.MealStats();
        numIngredients = 0;

        setupIngredientSpinner();
    }

    private void setupIngredientSpinner(){
        Spinner ingredientPicker = findViewById(R.id.ingredientDropdownMeal);

        List<String> ingredientList = IDao.getNames();
        ingredientList.add(0, "Choose Ingredient");
        List<IngredientStats> stats = IDao.getAll();
        final String[] ingredientArray = ingredientList.toArray(new String[0]);
        List<DayStats> dstats = DDao.getAll();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ingredientArray);

        ingredientPicker.setAdapter(adapter);

        ingredientPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selection = parent.getItemAtPosition(pos).toString();
                if (selection.equals("Choose Ingredient")) {      //stops creation of ingredient entry on default value
                    return;
                }
                LinearLayout mealLayout = (LinearLayout) findViewById(R.id.mealLayout);

                LinearLayout ingredientLayout = new LinearLayout(getApplicationContext());     //set up horizontal layout to add to vertical layout
                ingredientLayout.setOrientation(LinearLayout.HORIZONTAL);
                ingredientLayout.setGravity(1);

                TextView ingredientName = new TextView(getApplicationContext());
                ingredientName.setText(selection + ":");
                ingredientLayout.addView(ingredientName);

                EditText inputBox = new EditText((getApplicationContext()));
                inputBox.setInputType(TYPE_CLASS_NUMBER);

                ingredientLayout.addView(inputBox);

                mealLayout.addView(ingredientLayout);
                numIngredients++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void addMeal(View view){
        LinearLayout mealLayout = findViewById(R.id.mealLayout);
        int count = mealLayout.getChildCount();
        LinearLayout v = null;
        EditText nameTxt = findViewById(R.id.mealNameTxt);

        MealStats newMeal = new MealStats();
        newMeal.ingredientList = new LinkedList<IngredientStats>();
        newMeal.defaultQty = new LinkedList<Double>();

        String mealName = nameTxt.getText().toString();
        newMeal.mname = mealName;

        for(int i = 0; i < count; i++){
            v = (LinearLayout)mealLayout.getChildAt(i);
            TextView ingredientName = (TextView)v.getChildAt(0);
            EditText ingredientQTY = (EditText) v.getChildAt(1);
            String iname = ingredientName.getText().toString();
            iname = iname.substring(0,iname.length()-1);
            IngredientStats newIngredient = IDao.getByName(iname).get(0);
            newMeal.ingredientList.add(newIngredient);
            newMeal.defaultQty.add(Double.parseDouble(ingredientQTY.getText().toString()));
        }
            MDao.insertMeal(newMeal);
    }
}
