package com.trial.dietrophic;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class InputEat extends AppCompatActivity {

    EditText foodName;
    EditText fatPerGram;
    EditText proteinPerGram;
    EditText caloriesPerGram;

    private Intent intent;
    public SQLiteDatabase db=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_eat);
        foodName = findViewById(R.id.foodName);
        caloriesPerGram = findViewById(R.id.caloriesPerGram);
        fatPerGram = findViewById(R.id.fatPerGram);
        proteinPerGram = findViewById(R.id.proteinPerGram);

        db = (new DbBasic(this)).getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS food (_id INTEGER AUTO_INCREMENT ,Name TEXT, Fat INTEGER, Protein INTEGER, Calories INTEGER);");

            findViewById(R.id.getStarted1).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String fName = foodName.getText().toString();
                 String sFat = fatPerGram.getText().toString();
                 int fFat = Integer.parseInt(sFat);
                 String sProtein = proteinPerGram.getText().toString();
                 int fProtein = Integer.parseInt(sProtein);
                 String sCalories = caloriesPerGram.getText().toString();
                 int fCalories = Integer.parseInt(sCalories);
                 if (fName.isEmpty()) {
                     ((EditText) findViewById(R.id.name)).setError("Please Enter Username");
                     return;
                 }

                 if (sFat.isEmpty()) {
                     ((EditText) findViewById(R.id.weight)).setError("Please Enter Fat");
                     return;
                 }

                 if (sProtein.isEmpty()) {
                     ((EditText) findViewById(R.id.height)).setError("Please Enter Protein");
                     return;
                 }

                 if (sCalories.isEmpty()) {
                     ((EditText) findViewById(R.id.height)).setError("Please Enter Calories");
                     return;
                 }
                 Toast.makeText(InputEat.this, "Food Added", Toast.LENGTH_SHORT).show();


                 db.execSQL("INSERT INTO food ('Name','Fat','Protein','Calories') VALUES('" + fName + "','" + fFat + "','" + fProtein + "','" + fCalories + "');");

                 startActivity(new Intent(InputEat.this, EatActivity.class));
                 finish();
             }
            });


    }



}
