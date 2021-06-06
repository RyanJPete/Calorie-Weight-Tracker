package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.view.KeyEvent.KEYCODE_BACK;
import static android.view.KeyEvent.KEYCODE_ENTER;

public class EatActivity extends AppCompatActivity {
    AppDatabase db;
    DateDao DDao;
    MealDao MDao;
    IngredientDao IDao;
    Map<EditText, Double> foodCaloriesMap;  //input box and calorie per qty
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "dateDatabase").allowMainThreadQueries().build();    //TODO remove allowMainThreadQueries, for testing purposes only, do asynk task
        DDao = db.DayStats();
        IDao = db.IngredientStats();
        MDao = db.MealStats();
        foodCaloriesMap = new HashMap<EditText, Double>();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        setupIngredientSpinner();
        setupMealSpinner();
    }

    private void addCalories(){
        double totalCalories = 0;
        TextView calorieSumText = findViewById(R.id.totalCalories);

        for (Map.Entry<EditText, Double> textEntry : foodCaloriesMap.entrySet()) {
            double qty = 0;
            String temp = textEntry.getKey().getText().toString();
            if (!textEntry.getKey().getText().toString().equals("")) {
                qty = Double.parseDouble(textEntry.getKey().getText().toString());
                totalCalories += qty * textEntry.getValue();
            }

        }

        calorieSumText.setText(Double.toString(totalCalories));
    }

    private boolean addCaloriesBtn(View v, int keyCode, KeyEvent event, String iName, EditText inputBox) {
        if ((event.getAction() == KeyEvent.ACTION_UP && keyCode != KEYCODE_ENTER) && keyCode != KEYCODE_BACK) {
           /* TextView totalCalories = findViewById(R.id.totalCalories);
            Double newCalories = Double.parseDouble(totalCalories.getText().toString());
            IngredientStats temping = IDao.getByName(iName).get(0);
            newCalories += Double.parseDouble(inputBox.getText().toString())*temping.icalories;
            totalCalories.setText(Double.toString(newCalories));*/
            addCalories();

            return true;
        }
        return false;
    }

    private void setupMealSpinner() {
        Spinner meals = findViewById(R.id.mealDropdown);

        List<String> mealList = MDao.getNames();
        mealList.add(0, "Choose Meal");
        List<MealStats> stats = MDao.getAll();
        final String[] mealArray = mealList.toArray(new String[0]);
        List<DayStats> dstats = DDao.getAll();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mealArray);

        meals.setAdapter(adapter);

        //ingredients.setSelection(0,false);
        meals.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selection = parent.getItemAtPosition(pos).toString();
                if (selection.equals("Choose Meal")) {      //stops creation of ingredient entry on default value
                    return;
                }

                LinearLayout eatLayout = findViewById(R.id.eatLayout);

                LinkedList<IngredientStats> ingredientList = (LinkedList<IngredientStats>) MDao.getByName(selection).ingredientList;
                LinkedList<Double> qtyList = (LinkedList<Double>) MDao.getByName(selection).defaultQty;

                for (int x = 0; x < ingredientList.size(); x++) {
                    LinearLayout ingredientLayout = new LinearLayout(getApplicationContext());     //set up horizontal layout to add to vertical layout
                    ingredientLayout.setOrientation(LinearLayout.HORIZONTAL);
                    ingredientLayout.setGravity(1);

                    final String iName = ingredientList.get(x).iname;
                    TextView ingredientName = new TextView(getApplicationContext());
                    ingredientName.setText(iName + ":");
                    ingredientLayout.addView(ingredientName);

                    final EditText inputBox = new EditText((getApplicationContext()));
                    inputBox.setInputType(TYPE_CLASS_NUMBER);
                    inputBox.setOnKeyListener(new View.OnKeyListener() {
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            return addCaloriesBtn(v, keyCode, event, iName, inputBox);
                        }
                    });
                    inputBox.setOnFocusChangeListener(new View.OnFocusChangeListener(){
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                inputBox.setText("");
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                            } else {
                                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                            }
                        }
                    });
                    inputBox.setText(qtyList.get(x).toString());



                    ingredientLayout.addView(inputBox);
                    double selectionCalories = IDao.getCalories(iName);
                    foodCaloriesMap.put(inputBox, selectionCalories);

                    final String iPortion = ingredientList.get(x).iportion + "s";
                    final TextView ingredientUnit = new TextView(getApplicationContext());
                    ingredientUnit.setText(iPortion);
                    ingredientLayout.addView(ingredientUnit);

                    eatLayout.addView(ingredientLayout);
                    addCalories();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupIngredientSpinner() {
        Spinner ingredients = findViewById(R.id.ingredientDropdown);

        List<String> ingredientList = IDao.getNames();
        ingredientList.add(0, "Choose Ingredient");
        List<IngredientStats> stats = IDao.getAll();
        final String[] ingredientArray = ingredientList.toArray(new String[0]);
        List<DayStats> dstats = DDao.getAll();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ingredientArray);

        ingredients.setAdapter(adapter);

        //ingredients.setSelection(0,false);
        ingredients.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                final String selection = parent.getItemAtPosition(pos).toString();
                if (selection.equals("Choose Ingredient")) {      //stops creation of ingredient entry on default value
                    return;
                }
                LinearLayout eatLayout = (LinearLayout) findViewById(R.id.eatLayout);

                LinearLayout ingredientLayout = new LinearLayout(getApplicationContext());     //set up horizontal layout to add to vertical layout
                ingredientLayout.setOrientation(LinearLayout.HORIZONTAL);
                ingredientLayout.setGravity(1);

                final TextView ingredientName = new TextView(getApplicationContext());
                ingredientName.setText(selection + ":");
                ingredientLayout.addView(ingredientName);

                final EditText inputBox = new EditText((getApplicationContext()));
                inputBox.setInputType(TYPE_CLASS_NUMBER);
                inputBox.setOnKeyListener(new View.OnKeyListener() {
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        return addCaloriesBtn(v, keyCode, event, selection, inputBox);
                    }
                });
                inputBox.setOnFocusChangeListener(new View.OnFocusChangeListener(){
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            inputBox.setText("");
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                        } else {
                            imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                        }
                    }
                });
                ingredientLayout.addView(inputBox);
                Double selectionCalories = IDao.getCalories(selection);
                foodCaloriesMap.put(inputBox, selectionCalories);

                final TextView ingredientUnit = new TextView(getApplicationContext());
                ingredientUnit.setText(IDao.getByName(selection).get(0).iportion + "s");
                ingredientLayout.addView(ingredientUnit);

                eatLayout.addView(ingredientLayout);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void eat(View view) {
        TextView txt = findViewById(R.id.totalCalories);

        DateTime dT = DateTime.now();
        int dayOfWeek = dT.getDayOfWeek();
        int today = dT.getMonthOfYear() * 1000000 + dT.getDayOfMonth() * 10000 + dT.getYear();
        double caloriesEntered = DDao.getDateCalories(today);
        int weightEntered = DDao.getDateWeight(today);
        DayStats newEntry = new DayStats();
        newEntry.DayOfWeek = dayOfWeek;
        newEntry.DayDate = today;
        newEntry.DayWeight = weightEntered;
        newEntry.DayCalories = Double.parseDouble(txt.getText().toString()) + caloriesEntered;
        newEntry.DayFat = 0.0;
        newEntry.DayCarbs = 0.0;
        newEntry.DayProtein = 0.0;

        if (caloriesEntered == 0 && weightEntered == 0) {  //No entry has been made for today
            DDao.insertWeight(newEntry);
        } else {  //an entry has been made for today
            newEntry.Fdate = DDao.getDateKey(today);

            DDao.updateNutrition(Double.parseDouble(txt.getText().toString()) + caloriesEntered, 0, 0, 0, today);
        }
    }
}
