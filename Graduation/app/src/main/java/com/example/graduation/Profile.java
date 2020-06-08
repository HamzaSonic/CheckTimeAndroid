package com.example.graduation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {
    private BottomNavigationView navigation;
    Intent intent;
    TextView name,age,phone,gender,email;
    private DatabaseHelper4 mydb;
    private Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name=findViewById(R.id.nameTextView);
        age=findViewById(R.id.ageTextView);
        phone =findViewById(R.id.phoneTextView);
        gender=findViewById(R.id.genderTextView);
        email=findViewById(R.id.emailTextView);
        update=findViewById(R.id.updateinf);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentt=new Intent(Profile.this,EditInfo.class);
                startActivity(intentt);
            }
        });

        mydb=new DatabaseHelper4(this);
        Cursor data=mydb.getData();
        String nameP = "",emailP="",genderP="",phoneP="";
        String ageP = "";
        while (data.moveToNext()){
            nameP=data.getString(1);
            emailP=data.getString(2);
            genderP=data.getString(5);
            phoneP=data.getString(3);
            ageP=data.getString(4);
        }

        name.setText("Name: "+nameP);
        email.setText("email: "+emailP);
        gender.setText("Gender: "+genderP);
        phone.setText("Phone: "+phoneP);
        age.setText("Age: "+ ageP+" years");



       initComponent();

        CardView cd;
        cd=findViewById(R.id.infoapp);
        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(Profile.this,About.class);
                startActivity(intent);
            }
        });


    }
    private void initComponent() {

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_settings);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_reports:
                        intent=new Intent(Profile.this,Reports.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_settings:
                        return true;
                    case R.id.navigation_home:
                        intent=new Intent(Profile.this,MainActivity.class);
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
