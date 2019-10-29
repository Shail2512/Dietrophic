package com.trial.dietrophic;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class OverviewActivity extends AppCompatActivity {

    public SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        TextView name = (TextView) findViewById(R.id.finalname);
        TextView height = (TextView) findViewById(R.id.height);
        TextView weight = (TextView) findViewById(R.id.weight);
        TextView BMI = (TextView) findViewById(R.id.BMI);
        TextView Calories = (TextView) findViewById(R.id.Calories);
//        final ArrayList<PieHelper> pieHelperArrayList = new ArrayList<>();
        db = (new DbBasic((this)).getReadableDatabase());
        Cursor curdateDetail = db.rawQuery("Select * from users", null);
        startManagingCursor(curdateDetail);
        curdateDetail.moveToFirst();
//        txtname.setText( curdateDetail.getString(1));

        if (curdateDetail.moveToFirst()) {
            do {
// Passing values
                String fname = curdateDetail.getString(1);
                String fweight = curdateDetail.getString(2);
                String fheight = curdateDetail.getString(3);
                Log.d("data", "onCreate: " + name);
                name.setText(fname);
                weight.setText(fweight);
                height.setText(fheight);
                int sweight = Integer.parseInt(fweight);
                int sheight = Integer.parseInt(fheight);
                double bmi = ((sweight * 10000) / (sheight * sheight));
                double calorie = (bmi * 1.55);
                DecimalFormat df = new DecimalFormat("0.0");

                BMI.setText(Double.toString(bmi));
                Calories.setText(df.format(calorie).toString());
            } while (curdateDetail.moveToNext());
        }

        findViewById(R.id.getStarted).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OverviewActivity.this, EatActivity.class));
                finish();
            }
        });
        findViewById(R.id.overview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OverviewActivity.this, OverviewActivity.class));
                finish();
            }
        });
        findViewById(R.id.history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OverviewActivity.this, HistoryController.class));
                finish();
            }
        });
    }
}
