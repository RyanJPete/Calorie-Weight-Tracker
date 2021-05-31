package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddIngredientActivity extends AppCompatActivity {
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
            TextView inputTxt;
            int grams;
            IngredientStats newEntry = new IngredientStats();

            inputTxt = findViewById(R.id.gramsTxt);
            if (inputTxt.getText().toString().matches("")) {
                return;
            }
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

    public void clearTextBox(View view){

    }
}
