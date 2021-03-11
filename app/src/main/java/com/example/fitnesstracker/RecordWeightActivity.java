package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RecordWeightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_weight);
    }

    public void recordWeight(View view){
        Button btn =findViewById(R.id.weightBtn);
        TextView txt = findViewById(R.id.enterWeight);
        btn.setText(txt.getText());
    }
}
