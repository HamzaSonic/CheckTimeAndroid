package com.example.graduation;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class Reports extends AppCompatActivity {

    private LineGraphSeries<DataPoint> diabetesGraph;
    private LineGraphSeries<DataPoint> pressureGraph,pressureGraph1;
    private BottomNavigationView navigation;
    private Intent intent;
    DatabaseHelper mydb;
    DatabaseHelper1 mydb1;
    private ArrayList<Integer>dia=new ArrayList<>();
    private ArrayList<Integer>press=new ArrayList<>();
    private ArrayList<Integer>press1=new ArrayList<>();
    private TextView diafrom,pressfrom;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        diafrom=findViewById(R.id.diafrom);
        pressfrom=findViewById(R.id.pressfrom);
        mydb= new DatabaseHelper(this);
        mydb1= new DatabaseHelper1(this);
        Cursor data= mydb.getData();
        while (data.moveToNext()){
            dia.add(data.getInt(3));
        }
        Cursor data2 =mydb1.getData();
        while (data2.moveToNext()){
            press.add(data2.getInt(2));
            press1.add(data2.getInt(3));
        }
        initComponent();
        diabetesGraph=new LineGraphSeries<>();
        pressureGraph=new LineGraphSeries<>();
        pressureGraph1=new LineGraphSeries<>();
        double x=0,y=0;
        double mx=0,mn=1000;
        for(int i=0;i<dia.size();i++){
            x+=1;

            y=dia.get(i);
            mx=Math.max(mx,dia.get(i));
            mn=Math.min(mn,dia.get(i));
            diabetesGraph.appendData(new DataPoint(x,y),true,100);
        }
        x=0;int y1;
        double mx1=0,mn1=1000;
        int mxindex=-1,mnindex=-1;
        for(int i=0;i<press.size();i++){
            x+=1;
            y=press.get(i);
            y1=press1.get(i);
            mx1=Math.max(mx1,press.get(i));
            if (mx1==press.get(i)) mxindex=i;
            mn1=Math.min(mn1,press.get(i));
            if (mn1==press.get(i)) mnindex=i;
            pressureGraph.appendData(new DataPoint(x,y),true,100);
            pressureGraph1.appendData(new DataPoint(x,y1),true,100);

        }
        pressureGraph.setColor(Color.BLUE);
        pressureGraph1.setColor(Color.RED);
        diabetesGraph.setColor(Color.GREEN);

        int vv=Math.max(press.size(),dia.size());
        GraphView graphDia=findViewById(R.id.graph1);
        graphDia.getViewport().setMaxX(Math.max(vv,5));
        graphDia.getViewport().setMinX(1);
        graphDia.getViewport().setXAxisBoundsManual(true);
        graphDia.addSeries(diabetesGraph);
        graphDia.addSeries(pressureGraph);
        graphDia.addSeries(pressureGraph1);


        if(mxindex!=-1 && mnindex!=-1) {
            diafrom.setText("Diabetes:from " + mx + " to " + mn);
            pressfrom.setText("Pressure:from " + mx1 + "/" + press1.get(mxindex) + " to " + mn1 + "/" + press1.get(mnindex));
        }

    }
    private void initComponent() {


        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_reports);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_reports:
                        return true;
                    case R.id.navigation_settings:
                        intent=new Intent(Reports.this,Profile.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_home:
                        intent=new Intent(Reports.this,MainActivity.class);
                        startActivity(intent);
                        return true;

                }
                return false;
            }
        });
        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);
    }
}
