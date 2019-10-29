package com.trial.dietrophic;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalculateEatCalories extends AppCompatActivity {
    TextView clickedItem;
    TextView gram;
    TextView totalcalories;
    Intent intent;
    float clickedItemCalories;
    float fat;
    float protein;
    String gramt;

    String clickedItemfood;
    //    private DatabaseReference ref_history;
//    List<History> historyArrayList;
//    ListView historyListView;
    Date today = new Date();
    public SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_eat_calories);

        gram = findViewById(R.id.gram);
        totalcalories = findViewById(R.id.totalcalories);
        clickedItem = findViewById(R.id.clickedItem);

        db = (new DbBasic(this)).getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS History (_id INTEGER AUTO_INCREMENT ,foodName TEXT, Quantity INTEGER, Date TEXT, Fat FLOAT, Protein FLOAT, Calories FLOAT );");



        gram.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        intent = getIntent();


        clickedItemfood = intent.getStringExtra("food");
        clickedItem.setText(clickedItemfood);


        (findViewById(R.id.calcbtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Eat eat= (Eat) intent.getSerializableExtra("class");
                int gr=Integer.parseInt(gram.getText().toString());

                fat=gr*(Integer.parseInt(eat.fat))/100;
                protein=gr*(Integer.parseInt(eat.protein))/100;
                clickedItemCalories=gr*(Integer.parseInt(eat.calories))/100;


                ((TextView)findViewById(R.id.textView4)).setText(Float.toString(fat));
                ((TextView)findViewById(R.id.textView8)).setText(Float.toString(protein));
                ((TextView)findViewById(R.id.textView11)).setText(Float.toString(clickedItemCalories));
            }
        }
        );

        (findViewById(R.id.historybtn)).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
//               Date d=new Date();

               SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
               String d1 = sdf.format(new Date());
//               SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//               Date date1 = format.parse(d);
//               String d1=d.toString();
               gramt=gram.getText().toString();
               db.execSQL("INSERT INTO History ('foodName','Quantity','Date','Fat','Protein','Calories') VALUES('" + clickedItemfood + "','" + gramt + "','" + d1 + "','" + fat+ "',' " + protein+ "','" + clickedItemCalories+ "');");
               Toast.makeText(CalculateEatCalories.this, "Added to history", Toast.LENGTH_SHORT).show();
               Cursor curuserDetail = db.rawQuery("Select * from History", null);
               startManagingCursor(curuserDetail);
               curuserDetail.moveToFirst();
               startActivity(new Intent(CalculateEatCalories.this,EatActivity.class));
               if (curuserDetail.moveToFirst()) {
                   do {


                       String name = curuserDetail.getString(1);
                       String quantity = curuserDetail.getString(2);
                       String date = curuserDetail.getString(3);
                       String fat= curuserDetail.getString(4);
                       String protein= curuserDetail.getString(5);
                       String cal = curuserDetail.getString(6);

                       Log.d("hist", "onClick: "+name+" "+quantity+"  "+date+" "+fat+" "+protein+"  "+cal+"");

                   } while (curuserDetail.moveToNext());
               }
//                          startActivity(new Intent(CalculateEatCalories.this,EatActivity.class));

           }
       }
        );





    }


}
