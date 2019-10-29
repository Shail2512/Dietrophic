package com.trial.dietrophic;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStream;
import java.util.ArrayList;

public class EatActivity extends AppCompatActivity {
    SearchView searchView;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    ArrayList<Eat> list2;
    Intent intent;
    //    private DatabaseReference ref_eat;
//    private DatabaseReference ref_basicInfo;
    public SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat);

//        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.listView);
        list = new ArrayList<>();

        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);
        Log.d("shail", "onCreate: " + isFirstRun);
        if (isFirstRun) {
            //show start activity

            startActivity(new Intent(EatActivity.this, GetStart.class));
            Toast.makeText(EatActivity.this, "First Run", Toast.LENGTH_LONG)
                    .show();
            db = (new DbBasic(this)).getWritableDatabase();

            db.execSQL("CREATE TABLE IF NOT EXISTS food (_id INTEGER AUTO_INCREMENT ,Name TEXT, Fat INTEGER, Protein INTEGER, Calories INTEGER);");

            db.execSQL("INSERT INTO food ('Name','Fat','Protein','Calories') VALUES('roti',6,4,20);");
            db.execSQL("INSERT INTO food ('Name','Fat','Protein','Calories') VALUES('rice',6,2,22);");
            db.execSQL("INSERT INTO food ('Name','Fat','Protein','Calories') VALUES('paneer',4,8,25);");
            db.execSQL("INSERT INTO food ('Name','Fat','Protein','Calories') VALUES('dal',3,6,15);");
        }


        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();

        //Show Value from Table
        db = (new DbBasic(this)).getReadableDatabase();
        Cursor curuserDetail = db.rawQuery("Select * from food", null);
        startManagingCursor(curuserDetail);
        curuserDetail.moveToFirst();
//        txtname.setText( curuserDetail.getString(1));
        intent = new Intent(this, CalculateEatCalories.class);

        if (curuserDetail.moveToFirst()) {
            do {
// Passing values

            String name = curuserDetail.getString(1);
            String fat = curuserDetail.getString(2);
            String protein = curuserDetail.getString(3);
            String calories = curuserDetail.getString(4);
            Log.d("data", "onCreate: " + name);
            Eat eat =new Eat(name,fat,protein,calories);
            list.add(name);
            intent.putExtra("class",eat);
// Do something Here with values
            } while (curuserDetail.moveToNext());
        }

        // listView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // list - everything
                // after filter - refresh list or create another list with searched items.
                System.out.println("position : " + position);
                System.out.println("id : " + id);
                String name = list.get(position);

                intent.putExtra("food", name);
                // intent.putExtra("calories", eat.getCalories());
                startActivity(intent);

            }
        });

        findViewById(R.id.input).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EatActivity.this, InputEat.class));
            }
        });
        findViewById(R.id.overview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EatActivity.this, OverviewActivity.class));
            }
        });

        findViewById(R.id.getStarted).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EatActivity.this, EatActivity.class));
                finish();
            }
        });
        findViewById(R.id.history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EatActivity.this, HistoryController.class));
            }
        });


    }
}