package com.trial.dietrophic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

public class GetStart extends AppCompatActivity {
    EditText name;
    EditText height;
    EditText weight;
//    Button calculateDayCalories;
    Button getStarted;
    static double parameter;
    int fweight;
    String fname;
    int fheight;

    public SQLiteDatabase db=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_start);

        name = findViewById(R.id.name);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        db = (new DbBasic(this)).getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS users (_id INTEGER AUTO_INCREMENT ,Name TEXT, Weight INTEGER, Height INTEGER);");

        findViewById(R.id.getStarted).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname = name.getText().toString();
//                fweight = weight;
                String sheight = height.getText().toString();
                fheight=Integer.parseInt(sheight);
                String sweight = weight.getText().toString();
                fweight=Integer.parseInt(sweight);

                if (fname.isEmpty()) {
                    ((EditText) findViewById(R.id.name)).setError("Please Enter Username");
                    return;
                }

                if (sweight.isEmpty()) {
                    ((EditText) findViewById(R.id.weight)).setError("Please Enter Weight");
                    return;
                }

                if (sheight.isEmpty()) {
                    ((EditText) findViewById(R.id.height)).setError("Please Enter Height");
                    return;
                }
                Toast.makeText(GetStart.this, "Registeration Done", Toast.LENGTH_SHORT).show();


                db.execSQL("INSERT INTO users ('Name','Weight','Height') VALUES('" + fname + "','" + fweight + "','" + fheight + "');");

                startActivity(new Intent(GetStart.this,EatActivity.class));
                finish();
            }
        });





    }

    //man : kg * 1 * 24
    //woman : kg * 0.9 * 24
}
