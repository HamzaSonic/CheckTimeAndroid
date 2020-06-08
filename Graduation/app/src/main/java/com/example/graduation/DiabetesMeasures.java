package com.example.graduation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DiabetesMeasures extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private ArrayList<String> dates=new ArrayList<>();
    private ArrayList<String>times=new ArrayList<>();
    private ArrayList<String>results=new ArrayList<>();
    private Button saveBtn;
    private Button clear;
    String spinnerSelection;

    DatabaseHelper mydb;
    private AutoCompleteTextView measure;
    private Spinner spinner;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetes_measures);
        Spinner spinner =findViewById(R.id.spinnerDiabetes);
        ArrayAdapter<CharSequence> ad= ArrayAdapter.createFromResource(this,R.array.diabetesTimes,android.R.layout.simple_spinner_item);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
        spinner.setOnItemSelectedListener(this);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd");
        mydb=new DatabaseHelper(this);

        getHistory();
        clear=findViewById(R.id.clearDia);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mydb.delete();
                Intent intent =new Intent(DiabetesMeasures.this,MainActivity.class);
                startActivity(intent);
            }
        });
        saveBtn=findViewById(R.id.saveDiabetesButton);
        measure=findViewById(R.id.DiabetesMeasure);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getResult =measure.getText().toString();
                if (getResult.length()!= 0 ){
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM");
                    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                    Date today = Calendar.getInstance().getTime();
                    String date =formatter.format(today);
                makeEntry(date,spinnerSelection,getResult);
                measure.setText("");
                }else   Toast.makeText(DiabetesMeasures.this,"please fill the field ",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public  void makeEntry(String date, String time, String result){
        boolean insert = mydb.addData(date,time,result);
        if ( insert)   Toast.makeText(this,"success ",Toast.LENGTH_SHORT).show();
        else   Toast.makeText(this,"fail ",Toast.LENGTH_SHORT).show();
    }


    public void getHistory(){

        Cursor data = mydb.getData();
        while (data.moveToNext()){
            dates.add(data.getString(1));
            times.add(data.getString(2));
            results.add(data.getString(3));
        }

        initRecyclerView();
    }
    private void initRecyclerView(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecyclerView recyclerView =findViewById(R.id.DiabetesHisRecycler);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewDiabetesAdapter adapter =new RecyclerViewDiabetesAdapter(this,dates,times,results);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerSelection =parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),spinnerSelection,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
