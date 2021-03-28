package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AddIngredientActivity extends AppCompatActivity {
    AppDatabase db;
    IngredientDao IDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "ingredientdatabase").allowMainThreadQueries().build();
        IDao = db.IngredientStats();
    }

    public void ingredientBtnClick(View view){
        TextView inputTxt;
        int grams;

        inputTxt = findViewById(R.id.gramsTxt);
        grams = Integer.parseInt(inputTxt.getText().toString());
        IngredientStats newEntry = new IngredientStats();
        inputTxt = findViewById(R.id.calorieTxt);
        newEntry.icalories = Integer.parseInt(inputTxt.getText().toString())/grams;
        inputTxt = findViewById(R.id.fatTxt);
        newEntry.ifat = Integer.parseInt(inputTxt.getText().toString())/grams;
        inputTxt = findViewById(R.id.carbTxt);
        newEntry.icarbs = Integer.parseInt(inputTxt.getText().toString())/grams;
        inputTxt = findViewById(R.id.proteinTxt);
        newEntry.iprotein= Integer.parseInt(inputTxt.getText().toString())/grams;

        IDao.insertIngrerdient(newEntry);
    }

    public void clearTextBox(View view){
        TextView txt = (TextView)view;
        txt.setText(null);
    }
}
