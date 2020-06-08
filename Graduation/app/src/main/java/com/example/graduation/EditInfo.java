package com.example.graduation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditInfo extends AppCompatActivity {
    private EditText name,phone,email,age;
    private Button update;
    private DatabaseHelper4 mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        mydb=new DatabaseHelper4(this);
        name=findViewById(R.id.Nmaeidtextup);
        email=findViewById(R.id.emailedittextup);
        phone=findViewById(R.id.phoneedittextup);
        age=findViewById(R.id.ageedittextup);
        update=findViewById(R.id.updateI);
        Cursor data=mydb.getData();
        String oldn="",olde="",oldp="",olda="";
        while(data.moveToNext()){
            oldn=data.getString(1);
            olde=data.getString(2);
            olda=data.getString(4);
            oldp=data.getString(3);
        }
        final String finalOldn = oldn;
        final String finalOlde = olde;
        final String finalOlda = olda;
        final String finalOldp = oldp;
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname="",uage="",uphone="",uemail="";
                uname=name.getText().toString();
                if(uname.length()!=0){
                    mydb.updateName(uname,"Name", finalOldn);
                }
                uemail=email.getText().toString();
                if(uemail.length()!=0){
                    mydb.updateName(uemail,"Email", finalOlde);
                }
                uage=age.getText().toString();
                if(uage.length()!=0){
                    mydb.updateName(uage,"Age", finalOlda);

                }
                uphone=phone.getText().toString();
                if(uphone.length()!=0){
                   mydb.updateName(uphone,"Phone", finalOldp);
                }
                Intent intent=new Intent(EditInfo.this,Profile.class);
                startActivity(intent);
            }


        });
    }
}
