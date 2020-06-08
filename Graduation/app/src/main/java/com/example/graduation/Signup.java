package com.example.graduation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Signup extends AppCompatActivity {
    Button go;
    EditText name,phone,email,age;
    RadioGroup gender;
    RadioButton gender1;
    private DatabaseHelper4 mydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);



        name=findViewById(R.id.Nmaeidtext);
        phone=findViewById(R.id.phoneedittext);
        age=findViewById(R.id.ageedittext);
        email=findViewById(R.id.emailedittext);
        gender=findViewById(R.id.radioGroupgender);
        mydb=new DatabaseHelper4(this);

        SharedPreferences preferences=getSharedPreferences("Pref",MODE_PRIVATE);
        String first = preferences.getString("firstTime","");


        if (first.equals("yes")){
            Intent intent =new Intent(this,MainActivity.class);
            startActivity(intent);
        }else {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("firstTime", "yes");
            editor.apply();
        }

        go =findViewById(R.id.signup);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameU="",phoneU="",ageU="",emailU="";
                int aa=0;
                int rad=gender.getCheckedRadioButtonId();
                gender1=findViewById(rad);
                 nameU=name.getText().toString();
                 phoneU=phone.getText().toString();
                 ageU=age.getText().toString();
                 emailU=email.getText().toString();
                String genderr=gender1.getText().toString().trim();
                if (nameU.equals("")|| phoneU.equals("")||ageU.equals("")||emailU.equals("")){
                    Toast.makeText(Signup.this,"please fill all the fields to continue!",Toast.LENGTH_SHORT).show();
                }else {
                    mydb.deleteName();
                mydb.addData(nameU,emailU,phoneU,ageU,genderr);

                Intent intent=new Intent(Signup.this,MainActivity.class);
                startActivity(intent);
            }}
        });
    }
}
