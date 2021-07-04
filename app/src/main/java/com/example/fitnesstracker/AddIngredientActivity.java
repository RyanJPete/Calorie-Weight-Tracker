package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.LinkedList;

public class AddIngredientActivity extends AppCompatActivity
                                    implements checkDoubleEntry.checkDoubleEntryListener{
    AppDatabase db;
    IngredientDao IDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "dateDatabase").allowMainThreadQueries().build();
        IDao = db.IngredientStats();
        EditText inputTxt = findViewById(R.id.portionText);
        inputTxt.setText("gram");
    }

    public void ingredientBtnClick(View view){
        Button addBtn = findViewById(R.id.addIngredientBtn);

        if(addBtn.getText().toString().equals("Reset?")){
                EditText inputTxt;
            inputTxt = findViewById(R.id.nameTxt);
            inputTxt.setText("");
            inputTxt = findViewById(R.id.gramsTxt);
            inputTxt.setText("");
            inputTxt = findViewById(R.id.calorieTxt);
            inputTxt.setText("");
            inputTxt = findViewById(R.id.fatTxt);
            inputTxt.setText("");
            inputTxt = findViewById(R.id.carbTxt);
            inputTxt.setText("");
            inputTxt = findViewById(R.id.proteinTxt);
            inputTxt.setText("");
            inputTxt = findViewById(R.id.portionText);
            inputTxt.setText("");
            addBtn.setText("Add Ingredient");

        } else {
            ArrayList<IngredientStats> ingredientList = (ArrayList<IngredientStats>) IDao.getAll();
            TextView inputTxt;
            int grams;
            IngredientStats newEntry = new IngredientStats();



            inputTxt = findViewById(R.id.nameTxt);
            for(int x = 0; x < ingredientList.size(); ++x){
                if(ingredientList.get(x).iname.matches(inputTxt.getText().toString())){
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

            if (inputTxt.getText().toString().matches("")) {
                return;
            }
            inputTxt = findViewById(R.id.gramsTxt);
            grams = Integer.parseInt(inputTxt.getText().toString());
            inputTxt = findViewById(R.id.nameTxt);
            newEntry.iname = inputTxt.getText().toString();
            inputTxt = findViewById(R.id.calorieTxt);
            newEntry.icalories = Double.parseDouble(inputTxt.getText().toString()) / grams;
            inputTxt = findViewById(R.id.fatTxt);
            newEntry.ifat = Double.parseDouble(inputTxt.getText().toString()) / grams;
            inputTxt = findViewById(R.id.carbTxt);
            newEntry.icarbs = Double.parseDouble(inputTxt.getText().toString()) / grams;
            inputTxt = findViewById(R.id.proteinTxt);
            newEntry.iprotein = Double.parseDouble(inputTxt.getText().toString()) / grams;
            inputTxt = findViewById(R.id.portionText);
            newEntry.iportion = inputTxt.getText().toString();
            IDao.insertIngredient(newEntry);

            addBtn.setText("Reset?");
            //List<IngredientStats> tmp = IDao.getAll();
            //grams++;

        }
    }

    public void onDialogPositiveClick(DialogFragment dialog){
        TextView inputTxt = findViewById(R.id.gramsTxt);
        int grams = Integer.parseInt(inputTxt.getText().toString());
        IngredientStats newEntry = new IngredientStats();
        Button addBtn = findViewById(R.id.addIngredientBtn);


        inputTxt = findViewById(R.id.nameTxt);
        newEntry.iname = inputTxt.getText().toString();
        inputTxt = findViewById(R.id.calorieTxt);
        newEntry.icalories = Double.parseDouble(inputTxt.getText().toString()) / grams;
        inputTxt = findViewById(R.id.fatTxt);
        newEntry.ifat = Double.parseDouble(inputTxt.getText().toString()) / grams;
        inputTxt = findViewById(R.id.carbTxt);
        newEntry.icarbs = Double.parseDouble(inputTxt.getText().toString()) / grams;
        inputTxt = findViewById(R.id.proteinTxt);
        newEntry.iprotein = Double.parseDouble(inputTxt.getText().toString()) / grams;
        inputTxt = findViewById(R.id.portionText);
        newEntry.iportion = inputTxt.getText().toString();
        newEntry.key = IDao.getKeyByName(newEntry.iname);
        IDao.updateIngredient(newEntry);

        addBtn.setText("Reset?");
    }
    public void onDialogNegativeClick(DialogFragment dialog){

    }
    public void clearTextBox(View view){

    }
}
