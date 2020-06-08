package com.example.graduation;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String>Medicines =new ArrayList<>();
    private ArrayList<String>Next=new ArrayList<>();
    private ArrayList<Integer> dose=new ArrayList<>();
    private ArrayList<Integer>pills =new ArrayList<>();
    private ArrayList<String> Dnames=new ArrayList<>();
    private ArrayList<String >Dlocation=new ArrayList<>();
    private ArrayList<String >Ddepts=new ArrayList<>();
    private ArrayList<String>Ddates=new ArrayList<>();
    private ArrayList<String>Dtimes=new ArrayList<>();
    private ArrayList<String >Dnotes=new ArrayList<>();
    RecyclerViewAdapter adapter;


    Intent intent;
    private BottomNavigationView navigation;
    private View lyt_med;
    private View lyt_appionment;
    private View back_drop;
    private boolean rotate = false;
    private Button pressureButton,diabetesButton;
    private DatabaseHelper2 mydb;
    private DatabaseHelper3 mydb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb1=new DatabaseHelper3(this);
        mydb=new DatabaseHelper2(this);
        pressureButton = findViewById(R.id.PressureButton);
        final FloatingActionButton fab_mic =  findViewById(R.id.fab_add_med);
        final FloatingActionButton fab_call =  findViewById(R.id.fab_add_ap);
        final FloatingActionButton fab_add = findViewById(R.id.fab_add);
        lyt_med = findViewById(R.id.lyt_medicine);
        lyt_appionment = findViewById(R.id.lyt_appoinment);
        ViewAnimation.initShowOut(lyt_med);
        ViewAnimation.initShowOut(lyt_appionment);
        back_drop = findViewById(R.id.back_drop);
        back_drop.setVisibility(View.GONE);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFabMode(v);
            }
        });

        back_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFabMode(fab_add);
            }
        });

        fab_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 intent = new Intent(MainActivity.this,AddMedicine.class);
                startActivity(intent);
            }
        });

        fab_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 intent = new Intent(MainActivity.this,AddAppoinment.class);
                startActivity(intent);
            }
        });

        pressureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 intent= new Intent(MainActivity.this,PressureMeasures.class);
                startActivity(intent);
            }
        });

        diabetesButton=findViewById(R.id.DiabetesButton);
        diabetesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 intent=new Intent(MainActivity.this,DiabetesMeasures.class);
                startActivity(intent);
            }
        });

        getMedicines();
        initComponent();
    }
    @Override
    public void onResume(){
        super.onResume();
        Medicines.clear();
        Next.clear();
        dose.clear();
        pills.clear();
        Dnames.clear();
        Ddepts.clear();
        Dlocation.clear();
        Ddates.clear();
        Dtimes.clear();
        Dnotes.clear();
        getMedicines();

    }



    private void toggleFabMode(View v) {
        rotate = ViewAnimation.rotateFab(v, !rotate);
        if (rotate) {
            ViewAnimation.showIn(lyt_med);
            ViewAnimation.showIn(lyt_appionment);
            back_drop.setVisibility(View.VISIBLE);
        } else {
            ViewAnimation.showOut(lyt_med);
            ViewAnimation.showOut(lyt_appionment);
            back_drop.setVisibility(View.GONE);
        }
    }

    private void getMedicines(){
        Cursor data = mydb.getData();
        String temp=" ";
        while (data.moveToNext()) {
           if (!data.getString(1).equals(temp)) {
                Medicines.add(data.getString(1));
                dose.add(data.getInt(2));
                pills.add(data.getInt(3));
                Next.add(data.getString(5));
                temp=data.getString(1);
            }
        }
        Cursor data1 = mydb1.getData();

        while (data1.moveToNext()) {
            Dnames.add(data1.getString(1));
            Ddepts.add(data1.getString(2));
            Dlocation.add(data1.getString(3));
            Ddates.add(data1.getString(4));
            Dtimes.add(data1.getString(5));
            Dnotes.add(data1.getString(6));

        }

        initRecyclerView();
    }
    private void initRecyclerView(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecyclerView recyclerView =findViewById(R.id.recyclerViewMedicines);

        recyclerView.setLayoutManager(layoutManager);


         adapter =new RecyclerViewAdapter(this,Medicines,dose,pills,Next);
         new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager1=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecyclerView recyclerView1 =findViewById(R.id.recyclerViewAppoi);
        recyclerView1.setLayoutManager(layoutManager1);
        RecyclerViewAdapterApp adapter1 =new RecyclerViewAdapterApp(this,Dnames,Ddates,Dlocation,Dtimes,Dnotes);
        new ItemTouchHelper(itemTouchHelperCallBack1).attachToRecyclerView(recyclerView1);
        recyclerView1.setAdapter(adapter1);

    }


    private void initComponent() {


        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_reports:
                         intent=new Intent(MainActivity.this,Reports.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_settings:
                         intent=new Intent(MainActivity.this,Profile.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_home:
                        intent=new Intent(MainActivity.this,MainActivity.class);
                        return true;

                }
                return false;
            }
        });
        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);
    }
    ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int a= viewHolder.getAdapterPosition();
            String name=Medicines.get(a);
            int aa=pills.get(a);

            mydb.deleteName(aa,name);

            Medicines.remove(viewHolder.getAdapterPosition());
            Next.remove(viewHolder.getAdapterPosition());
            dose.remove(viewHolder.getAdapterPosition());
            pills.remove(viewHolder.getAdapterPosition());
            adapter.notifyDataSetChanged();

        }
    };
    ItemTouchHelper.SimpleCallback itemTouchHelperCallBack1 = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int a= viewHolder.getAdapterPosition();
            String name=Dnames.get(a);


            mydb1.deleteName(name);

            Dnames.remove(viewHolder.getAdapterPosition());
            Ddepts.remove(viewHolder.getAdapterPosition());
            Dlocation.remove(viewHolder.getAdapterPosition());
            Ddates.remove(viewHolder.getAdapterPosition());
            Dtimes.remove(viewHolder.getAdapterPosition());
            Dnotes.remove(viewHolder.getAdapterPosition());


        }
    };



}
