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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static android.text.InputType.TYPE_CLASS_NUMBER;

public class AddMealActivity extends AppCompatActivity
                                implements checkDoubleEntry.checkDoubleEntryListener{
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

    public MealStats makeMeal(){
        LinearLayout mealLayout = findViewById(R.id.mealLayout);
        int count = mealLayout.getChildCount();
        LinearLayout v = null;
        EditText nameTxt = findViewById(R.id.mealNameInput);
        EditText portionNumText = findViewById(R.id.portionNumInput);
        int portionCount = Integer.parseInt(portionNumText.getText().toString());

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
            newMeal.defaultQty.add(Double.parseDouble(ingredientQTY.getText().toString())/portionCount);
        }
        return newMeal;
    }

    public void resetLayout(){
        LinearLayout mealLayout = findViewById(R.id.mealLayout);
        mealLayout.removeAllViews();
        Spinner ingredientPicker = findViewById(R.id.ingredientDropdownMeal);
        setupIngredientSpinner();
    }

    public void addMeal(View view){
        Button addBtn = findViewById(R.id.addMealBtn);
        TextView inputTxt;
        inputTxt = findViewById(R.id.mealNameInput);
        TextView portionTxt = findViewById(R.id.portionNumInput);

        if(addBtn.getText().toString().equals("Reset?")){
            resetLayout();
            inputTxt.setText("");
            portionTxt.setText("1");
            addBtn.setText("ADD MEAL");

        } else {
            //check for existing name
            ArrayList<MealStats> mealList = (ArrayList<MealStats>) MDao.getAll();

            for(int x = 0; x < mealList.size(); ++x){
                if(mealList.get(x).mname.matches(inputTxt.getText().toString())){
                    try {
                        FragmentManager fm = getSupportFragmentManager();
                        DialogFragment dub = new checkDoubleEntry();
                        dub.show(fm, "VerifyDoubleup");
                    } catch (Exception e){
                        System.out.print(e);
                    }
                    return;
                }
            }

            MealStats newMeal = makeMeal();
            MDao.insertMeal(newMeal);
            addBtn.setText("Reset?");
        }
    }

    public void onDialogPositiveClick(DialogFragment dialog){
        TextView inputTxt = findViewById(R.id.gramsTxt);
        IngredientStats newEntry = new IngredientStats();
        Button addBtn = findViewById(R.id.addMealBtn);


        //Create Meal
        MealStats newMeal = makeMeal();
        newMeal.key = MDao.GetKeyByName(newMeal.mname);
        MDao.updateMeal(newMeal);


    }
    public void onDialogNegativeClick(DialogFragment dialog){

    }
}
