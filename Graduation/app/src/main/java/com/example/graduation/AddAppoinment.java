package com.example.graduation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class AddAppoinment extends AppCompatActivity {
    private AutoCompleteTextView name,location,dept,notes;
    private DatabaseHelper3 mydb;
    private TextView date,time;
    private Button save;
    Calendar cur_calender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appoinment);
        mydb=new DatabaseHelper3(this);
        name=findViewById(R.id.DoctornameTextv);
        location= findViewById(R.id.LocationText);
        dept=findViewById(R.id.DeptText);
        notes=findViewById(R.id.notesText);
        date=findViewById(R.id.dateId);
        time=findViewById(R.id.timeId2);
        save=findViewById(R.id.saveButton2);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameD=name.getText().toString();
                String locationD=location.getText().toString();
                String deptD=dept.getText().toString();
                String timeD=time.getText().toString();
                String dateD=date.getText().toString();
                String noteD=notes.getText().toString();
                if(nameD.length() !=0 && locationD.length() !=0 && timeD.length()!=0 && dateD.length() !=0 ){
                    if (deptD.length()==0) deptD=" ";if(noteD.length()==0) noteD=" ";
                    mydb.addData(nameD,deptD,locationD,dateD,timeD,noteD);
                    Toast.makeText(AddAppoinment.this,"success! ",Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Calendar c=Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY,9);
                        c.set(Calendar.MINUTE,0);
                        c.set(Calendar.YEAR,2020);
                        int ss=cur_calender.get(Calendar.MONTH);
                        c.set(Calendar.MONTH,ss);
                        int day=cur_calender.get(Calendar.DAY_OF_MONTH);
                        c.set(Calendar.DAY_OF_MONTH,day-1);
                        startAlarm(c);
                        AddMedicine.i++;
                    }



                    Intent intent =new Intent(AddAppoinment.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(AddAppoinment.this,"please Enter the Required fields!",Toast.LENGTH_SHORT).show();
                }

            }
        });
        initComponent();
    }

    private void initComponent() {
        ( findViewById(R.id.bt_pick_date)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDatePickerLight();
            }
        });
        ( findViewById(R.id.bt_pick2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTimePickerLight();
            }
        });
    }

    private void dialogDatePickerLight() {
         cur_calender = Calendar.getInstance();
        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        long date_ship_millis = calendar.getTimeInMillis();
                        ((TextView) findViewById(R.id.dateId)).setText(Tools.getFormattedDateSimple(date_ship_millis));
                    }
                },
                cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH)
        );
        //set dark light
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.setMinDate(cur_calender);
        datePicker.show(getFragmentManager(), "Datepickerdialog");
    }
    private void dialogTimePickerLight() {
        Calendar cur_calender = Calendar.getInstance();
        TimePickerDialog datePicker = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                ((TextView) findViewById(R.id.timeId2)).setText(hourOfDay + ":" + minute);
            }
        }, cur_calender.get(Calendar.HOUR_OF_DAY), cur_calender.get(Calendar.MINUTE), true);
        //set dark light
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.show(getFragmentManager(), "Timepickerdialog");
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startAlarm(Calendar c) {
        Toast.makeText(this,"Success!",Toast.LENGTH_SHORT).show();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver1.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, AddMedicine.i, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_HOUR*diff,pendingIntent);
    }
}
