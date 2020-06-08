package com.example.graduation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class PressureMeasures extends AppCompatActivity {
    ArrayList<String>dates=new ArrayList<>();
    ArrayList<Integer> fiResult=new ArrayList<Integer>();
    ArrayList<Integer> seResult=new ArrayList<Integer>();
    DatabaseHelper1 mydb;
    AutoCompleteTextView first,second;
    Button save;
    private Button clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure_measures);
        mydb=new DatabaseHelper1(this);
        first=findViewById(R.id.firstinputpressure);
        second=findViewById(R.id.secondinputpressure);
        save=findViewById(R.id.savePressureButton);
        clear=findViewById(R.id.clearpress);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mydb.deleteName();
                Intent intent =new Intent(PressureMeasures.this,MainActivity.class);
                startActivity(intent);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer result1=new Integer(0);
                Integer result2=new Integer(0);
                String result11= first.getText().toString();
                String result22=second.getText().toString();
                if (result11.length() != 0 && result22.length() !=0) {
                    result1 =Integer.parseInt(result11);
                    result2=Integer.parseInt(result22);
                    Toast.makeText(PressureMeasures.this,"success! ",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(PressureMeasures.this,"please fill the field ",Toast.LENGTH_SHORT).show();
                }
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date today = Calendar.getInstance().getTime();
                String date =formatter.format(today);
               mydb.addData(date,result1,result2);
               first.setText("");
               second.setText("");
            }
        });
        getHistory();
    }
    public void getHistory(){


        Cursor data = mydb.getData();
        while (data.moveToNext()){
            dates.add(data.getString(1));
            fiResult.add(data.getInt(2));
            seResult.add(data.getInt(3));
        }

        initRecyclerView();
    }

    private void initRecyclerView(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecyclerView recyclerView =findViewById(R.id.pressureHisRecycler);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewPressureAdapter adapter =new RecyclerViewPressureAdapter(this,dates,fiResult,seResult);
        recyclerView.setAdapter(adapter);

    }
}
