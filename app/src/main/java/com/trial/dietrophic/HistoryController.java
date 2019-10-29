package com.trial.dietrophic;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HistoryController extends AppCompatActivity {
    ListView historyListView;
    ArrayAdapter<String> adapter;
    private ArrayList<String> historyArrayList;
    Intent intent;
    Date today = new Date();
    TextView tvDOB;
    private String TAG = "HistoryController";
    DatePickerDialog datePickerDialog;

    TextView date;
    public SQLiteDatabase db = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
//        date = findViewById(R.id.selectedDate);
//        final String date1 = "25-12-1998";
        db = (new DbBasic(this)).getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS History (_id INTEGER AUTO_INCREMENT ,foodName TEXT, Quantity INTEGER, Date TEXT, Fat FLOAT, Protein FLOAT, Calories FLOAT );");

        historyListView = findViewById(R.id.historyListView);
        historyArrayList = new ArrayList<>();

        final TextView sumFat = (TextView) findViewById(R.id.fat);
        final TextView sumProtein = (TextView) findViewById(R.id.protein);
        tvDOB=findViewById(R.id.selectedDate);
        final TextView sumCalorie = (TextView) findViewById(R.id.calorie);

        final Calendar mCalendar= Calendar.getInstance();
        tvDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog= new DatePickerDialog(HistoryController.this ,new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        SimpleDateFormat sdm=new SimpleDateFormat("dd-MM-yyyy");
                        mCalendar.set(year,monthOfYear,dayOfMonth);
                        tvDOB.setText(sdm.format(mCalendar.getTime()));

                    }

                },mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("Date Of Birth");
                datePickerDialog.show();

            }
        });
        findViewById(R.id.chooseDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db = (new DbBasic(HistoryController.this)).getReadableDatabase();
                Cursor curdateDetail = db.rawQuery("Select * from History where Date = '" + tvDOB.getText().toString() + "'", null);
                int sumOfCalories = 0;
                int sumOfFat = 0;
                int sumOfProtein = 0;
                startManagingCursor(curdateDetail);
                curdateDetail.moveToFirst();
//        txtname.setText( curdateDetail.getString(1));
                if(historyArrayList.isEmpty()){
                    Toast.makeText(HistoryController.this, "No data added", Toast.LENGTH_SHORT).show();
                }
                historyArrayList.clear();

                if (curdateDetail.moveToFirst()) {
                    do {
// Passing values
                        String foodName = curdateDetail.getString(1);
                        String quantity = curdateDetail.getString(2);
                        String fat = curdateDetail.getString(4);
                        String protein = curdateDetail.getString(5);
                        String calories = curdateDetail.getString(6);
                        Log.d("data", "onCreate: " + foodName);
                        int c = Integer.parseInt(calories);
                        int f = Integer.parseInt(fat);
                        int p = Integer.parseInt(protein);

                        sumOfFat = sumOfFat + f;
                        sumOfProtein = sumOfProtein + p;
                        sumOfCalories = sumOfCalories + c;

                        historyArrayList.add(foodName);
                    } while (curdateDetail.moveToNext());
                }


                // listView
                adapter = new ArrayAdapter<String>(HistoryController.this, android.R.layout.simple_list_item_1, historyArrayList);
                historyListView.setAdapter(adapter);

                sumFat.setText(Integer.toString(sumOfFat));
                sumProtein.setText(Integer.toString(sumOfProtein));
                sumCalorie.setText(Integer.toString(sumOfCalories));



            }
        });

        findViewById(R.id.getStarted).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistoryController.this, EatActivity.class));
                finish();
            }
        });
        findViewById(R.id.overview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistoryController.this, OverviewActivity.class));
                finish();
            }
        });
        findViewById(R.id.history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistoryController.this, HistoryController.class));
                finish();
            }
        });

    }
}
